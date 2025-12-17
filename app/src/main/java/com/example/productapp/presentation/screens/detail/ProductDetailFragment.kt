package com.example.productapp.presentation.screens.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.productapp.R
import com.example.productapp.data.remote.models.response.ProductResponse
import com.example.productapp.databinding.DetailScreenBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProductDetailFragment : Fragment(R.layout.detail_screen) {
    private val binding by viewBinding(DetailScreenBinding::bind)
    private var product: ProductResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable("PRODUCT")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        product?.let { p ->
            binding.productTitle.text = p.title
            binding.productPrice.text = "$${p.price}"
            binding.productCategory.text = p.category
            binding.productRating.text = "Rating: ${p.rating.rate} (${p.rating.count} reviews)"
            binding.productDescription.text = p.description

            Glide.with(this)
                .load(p.image)
                .into(binding.productImage)
        }
        binding.backImg.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
