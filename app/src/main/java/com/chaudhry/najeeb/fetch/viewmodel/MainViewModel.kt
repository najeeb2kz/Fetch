package com.chaudhry.najeeb.fetch.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.chaudhry.najeeb.fetch.data.Item
import com.chaudhry.najeeb.fetch.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> get() = _text

    fun fetchItemsFromApi() {
        Log.d(TAG, "fetchItemsFromApi() called")
        _loading.value = true
        _text.value = "Items fetched from API"
        viewModelScope.launch {
            repository.clearItems()
        }
        viewModelScope.launch {
            try {
                val items = repository.fetchItemsFromApi()
                Log.d(TAG, "Below is the list of Items fetched from the API")
                Log.d(TAG, items.joinToString(separator = "\n"))
                val sortedItems = repository.sortAndFilterItems(items)
                Log.d(TAG, "Below is the list of sorted Items fetched from the API")
                Log.d(TAG, sortedItems.joinToString(separator = "\n"))
                repository.insertItems(sortedItems)
                Log.d(TAG, "Adding 1 sec delay for Fetch hiring team to observe spinner")
                delay(1000)
                _items.value = sortedItems
                _error.value = null
            } catch (e: Exception) {
                Log.d(TAG, "Error occurred while fetching data from API call")
                _text.value = "Error occurred while fetching data"
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchItemsFromDb() {
        Log.d(TAG, "fetchItemsFromDb() called")
        _loading.value = true
        viewModelScope.launch {
            val items = repository.fetchItemsFromDb()
            _text.value = if (items.isEmpty()) "DB is empty" else "Items fetched from DB"
            Log.d(TAG, "Adding 1 sec delay for Fetch hiring team to observe spinner")
            delay(1000)
            _items.value = items
            _loading.value = false
        }
    }

    fun clearItems() {
        Log.d(TAG, "clearItems() called")
        _loading.value = true
        _text.value = if (_items.value.isNullOrEmpty()) "DB is empty" else "Deleted items in DB"
        viewModelScope.launch {
            repository.clearItems()
            _items.value = emptyList()
            Log.d(TAG, "Adding 1 sec delay for Fetch hiring team to observe spinner")
            delay(1000)
            _loading.value = false
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}