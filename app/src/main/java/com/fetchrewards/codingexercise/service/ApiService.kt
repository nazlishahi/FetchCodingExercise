package com.fetchrewards.codingexercise.service

import com.fetchrewards.codingexercise.data.remote.ListItem
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getList(): List<ListItem>
}
