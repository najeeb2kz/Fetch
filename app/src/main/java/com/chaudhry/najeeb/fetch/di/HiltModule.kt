package com.chaudhry.najeeb.fetch.di

import android.content.Context
import androidx.room.Room
import com.chaudhry.najeeb.fetch.data.ItemDatabase
import com.chaudhry.najeeb.fetch.data.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ItemDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ItemDatabase::class.java,
            "item_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: ItemDatabase): ItemDao {
        return database.itemDao()
    }
}