package com.example.productapp.presentation.screens.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.productapp.R
import com.example.productapp.databinding.MainScreenBinding
import com.example.productapp.presentation.adapters.MainAdapter
import com.example.productapp.presentation.screens.detail.ProductDetailFragment
import com.example.productapp.presentation.screens.main.MainViewModel
import dev.androidbroadcast.vbpd.viewBinding

class MainScreen : Fragment(R.layout.main_screen) {
    private val viewModel : MainViewModel by viewModels()
    private val binding by viewBinding(MainScreenBinding::bind)
    private val adapter by lazy { MainAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRv.adapter = adapter
        binding.mainRv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter.setItemClickListener {
            val fr = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("PRODUCT", it)
            fr.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.myContainer,fr)
                .addToBackStack(null)
                .commit()
        }
        viewModel.loadProducts()
        viewModel.productsLiveData.observe(viewLifecycleOwner){ list ->
            adapter.submitList(list)
        }
        viewModel.messageLiveData.observe(viewLifecycleOwner){ message ->
            if (message.isNotEmpty()){
                if(message == "INTERNET") binding.noInternetScreen.visibility = View.VISIBLE
                else {
                   binding.errorState.visibility = View.VISIBLE
                   binding.errorMessage.text = message
                }
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }
}