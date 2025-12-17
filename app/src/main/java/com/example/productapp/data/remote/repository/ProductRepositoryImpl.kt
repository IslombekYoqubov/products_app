package com.example.productapp.data.remote.repository

import com.example.productapp.data.remote.api.ApiClient
import com.example.productapp.data.remote.models.response.ProductResponse
import com.example.productapp.data.remote.services.ApiService
import com.example.productapp.domain.ProductRepository

class ProductRepositoryImpl private constructor(private val apiService: ApiService) : ProductRepository {
    companion object {
        private var INSTANCE : ProductRepository? = null
        private val apiService = ApiClient.apiService
        fun getInstance() : ProductRepository{
            if (INSTANCE == null) INSTANCE = ProductRepositoryImpl(apiService = apiService)
            return INSTANCE!!
        }
    }
    override suspend fun getAllProduct(): Result<List<ProductResponse>> {
        return try {
            val response = apiService.getProducts()
            Result.success(response)
        } catch (e : Exception){
            Result.failure(e)
        }
    }
}