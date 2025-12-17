package com.example.productapp.data.remote.services

import com.example.productapp.data.remote.models.response.ProductResponse
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductResponse>
}
