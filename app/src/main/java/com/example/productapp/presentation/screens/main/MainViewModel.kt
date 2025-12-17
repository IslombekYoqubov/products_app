package com.example.productapp.presentation.screens.main

import android.net.http.HttpException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.remote.models.response.ProductResponse
import com.example.productapp.data.remote.repository.ProductRepositoryImpl
import com.example.productapp.domain.ProductRepository
import com.example.productapp.presentation.screens.main.MainViewModelContract
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel(), MainViewModelContract {

    private val repository: ProductRepository = ProductRepositoryImpl.Companion.getInstance()
    override val productsLiveData = MutableLiveData<List<ProductResponse>>()
    override val messageLiveData = MutableLiveData<String>()
    override val noInternetConnection = MutableLiveData<Boolean>()
    override val loadingLiveData = MutableLiveData<Boolean>()

    override fun loadProducts() {
        viewModelScope.launch {
            loadingLiveData.value = true
            repository.getAllProduct()
                .onSuccess { list ->
                    productsLiveData.value = list
                }
                .onFailure { throwable ->
                    val message = when (throwable) {
                        is IOException -> "INTERNET"
                        else -> throwable.message ?: "Unknown error"
                    }
                    messageLiveData.value = message
                }
            loadingLiveData.value = false
        }

    }
}