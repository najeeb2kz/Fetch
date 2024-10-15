package com.chaudhry.najeeb.fetch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Query("SELECT * FROM items")
    fun getAllItems(): List<Item>

    @Query("DELETE FROM items")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndInsertAll(items: List<Item>) {
        clearAll()
        insertAll(items)
    }
}