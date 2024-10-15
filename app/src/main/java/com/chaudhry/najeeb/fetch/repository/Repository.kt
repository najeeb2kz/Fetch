package com.chaudhry.najeeb.fetch.repository

import android.util.Log
import com.chaudhry.najeeb.fetch.data.Item
import com.chaudhry.najeeb.fetch.data.ItemDao
import com.chaudhry.najeeb.fetch.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) {
    suspend fun fetchItemsFromApi(): List<Item> {
        Log.d(TAG, "fetchItemsFromApi(): called")
        return withContext(Dispatchers.IO) {
            apiService.getItems()
        }
    }

    suspend fun fetchItemsFromDb(): List<Item> {
        Log.d(TAG, "fetchItemsFromDb(): called")
        return withContext(Dispatchers.IO) {
            val items = itemDao.getAllItems()
            sortAndFilterItems(items)
        }
    }

    suspend fun insertItems(items: List<Item>) {
        Log.d(TAG, "insertItems(): called")
        withContext(Dispatchers.IO) {
            itemDao.clearAll()
            itemDao.clearAndInsertAll(items)
        }
    }

    suspend fun clearItems() {
        Log.d(TAG, "clearItems(): called")
        withContext(Dispatchers.IO) {
            itemDao.clearAll()
        }
    }

    //Display all the items grouped by "listId"
    //Sort the results first by "listId" then by "name" (extract numeric part of name) when displaying
    //Filter out any items where "name" is blank or null
    fun sortAndFilterItems(items: List<Item>): List<Item> {
        return items.filter { !it.name.isNullOrBlank() }
            .sortedWith(compareBy({ it.listId }, { it.name?.substringAfter("Item ")?.toIntOrNull() ?: Int.MAX_VALUE }))
    }

    companion object {
        private const val TAG = "Repository"
    }
}