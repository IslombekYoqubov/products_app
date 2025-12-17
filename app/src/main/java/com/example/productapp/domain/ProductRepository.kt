package com.example.productapp.domain

import com.example.productapp.data.remote.models.response.ProductResponse

interface ProductRepository {
    suspend fun getAllProduct() : Result<List<ProductResponse>>
}