package com.example.tawarin.UI.main.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.R
import com.example.tawarin.databinding.ListCategoryHomeBinding

class CategoryAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var rowIndex = -1
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

    inner class ViewHolder(private val binding: ListCategoryHomeBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "ResourceAsColor")
        fun bind (data: CategoryResponse.Data, position: Int){
            binding.tvCategoryHome.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
                rowIndex = position
                notifyDataSetChanged()
            }
            if (rowIndex == position){
                binding.cardCategory.setBackgroundColor(R.color.color_main)
                binding.cardCategory.setBackgroundResource(R.drawable.bg_category_seller_selected)
                binding.tvCategoryHome.setTextColor(Color.parseColor("#FFFFFF"))
            }else{
                binding.cardCategory.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.tvCategoryHome.setTextColor(Color.parseColor("#000000"))
                binding.cardCategory.setBackgroundResource(R.drawable.bg_category_home)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListCategoryHomeBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data, position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}