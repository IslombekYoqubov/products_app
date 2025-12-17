package com.example.productapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productapp.R
import com.example.productapp.data.remote.models.response.ProductResponse
import com.example.productapp.databinding.ProductItemBinding

class MainAdapter: ListAdapter<ProductResponse, MainAdapter.ProductViewHolder>(DiffCallBack()) {
    private lateinit var onItemClick: (ProductResponse) -> Unit
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding = binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding : ProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if(::onItemClick.isInitialized){
                    onItemClick.invoke(getItem(adapterPosition))
                }
            }
        }
        fun bind(product: ProductResponse){
            binding.productName.text = product.title
            Glide.with(binding.productImage.context)
                .load(product.image)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error_image)
                .into(binding.productImage)
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<ProductResponse>(){
        override fun areItemsTheSame(
            oldItem: ProductResponse,
            newItem: ProductResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductResponse,
            newItem: ProductResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
    fun setItemClickListener(block : (ProductResponse) -> Unit){
        onItemClick = block
    }
}