package com.fetchrewards.codingexercise.repository

import com.fetchrewards.codingexercise.data.remote.ListItem
import com.fetchrewards.codingexercise.service.ApiService
import javax.inject.Inject

class GetListRepository
    @Inject
    constructor(
        private val apiService: ApiService,
    ) {
        suspend fun fetchList(): Result<List<ListItem>> {
            return try {
                val response = apiService.getList()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
