package com.example.tawarin.UI.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductResponse
import com.example.tawarin.databinding.ListProductHomeBinding
import com.example.tawarin.Utils.currency

class ProductAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<ProductResponse.Produk>(){
        override fun areItemsTheSame(oldItem: ProductResponse.Produk, newItem: ProductResponse.Produk): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ProductResponse.Produk, newItem: ProductResponse.Produk): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value: List<ProductResponse.Produk>?) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem (data: ProductResponse.Produk)
    }

    inner class ViewHolder(private val binding: ListProductHomeBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        var listCategory = ""
        fun bind (data: ProductResponse.Produk){
            Glide.with(binding.root)
                .load(data.imageUrl.first())
                .into(binding.ivProductImg)
            binding.tvProductName.text = data.name
            binding.tvProductCategory.text = data.category!!.name.toString()
            binding.tvProductPrice.text = currency(data.basePrice!!)
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListProductHomeBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}