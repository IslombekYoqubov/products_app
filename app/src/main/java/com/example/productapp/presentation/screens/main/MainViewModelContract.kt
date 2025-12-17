package com.example.productapp.presentation.screens.main

import androidx.lifecycle.LiveData
import com.example.productapp.data.remote.models.response.ProductResponse

interface MainViewModelContract {
    val productsLiveData : LiveData<List<ProductResponse>>
    val messageLiveData : LiveData<String>
    val noInternetConnection : LiveData<Boolean>
    val loadingLiveData : LiveData<Boolean>
    fun loadProducts()
}