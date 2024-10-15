package com.chaudhry.najeeb.fetch.network

import com.chaudhry.najeeb.fetch.data.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}