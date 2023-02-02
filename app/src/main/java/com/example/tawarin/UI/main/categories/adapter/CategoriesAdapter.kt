package com.example.tawarin.UI.main.categories.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryByIdResponse
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.R
import com.example.tawarin.databinding.ListCategoryHomeBinding
import com.example.tawarin.databinding.ListItemCategoriesByidBinding

class CategoriesAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<CategoryResponse.Data>(){
        override fun areItemsTheSame(oldItem: CategoryResponse.Data, newItem: CategoryResponse.Data): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CategoryResponse.Data, newItem: CategoryResponse.Data): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value: List<CategoryResponse.Data>?) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem (data: CategoryResponse.Data)
    }

    inner class ViewHolder(private val binding: ListItemCategoriesByidBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "ResourceAsColor")
        fun bind (data: CategoryResponse.Data, position: Int){
            binding.tvCategory.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemCategoriesByidBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data, position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}