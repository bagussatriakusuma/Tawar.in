package com.example.tawarin.UI.main.sell.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.R
import com.example.tawarin.databinding.ListProductCategoryBinding
import com.example.tawarin.Utils.listCategory

class CategoryAdapterSell(
    private val selected: (CategoryResponse.Data)->Unit,
    private val unselected: (CategoryResponse.Data)->Unit
) :
    RecyclerView.Adapter<CategoryAdapterSell.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<CategoryResponse.Data>(){

        override fun areItemsTheSame(
            oldItem: CategoryResponse.Data,
            newItem: CategoryResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponse.Data,
            newItem: CategoryResponse.Data
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<CategoryResponse.Data>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListProductCategoryBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ListProductCategoryBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: CategoryResponse.Data){
            binding.apply {
                cardCategoryJual.setBackgroundResource(R.drawable.bg_roundstroke)
                cbCategory.text = data.name
                cbCategory.isChecked = listCategory.contains(data.name)
                cbCategory.setOnClickListener{
                    if (!listCategory.contains(data.name)){
                        selected.invoke(data)
                    } else {
                        unselected.invoke(data)
                    }
                }
            }
        }
    }

}