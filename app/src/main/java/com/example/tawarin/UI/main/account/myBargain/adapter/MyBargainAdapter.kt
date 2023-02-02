package com.example.tawarin.UI.main.account.myBargain.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tawarin.Data.api.main.buyer.buyerOrder.Buyer
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Data.api.main.seller.sellerOrder.SellerOrderResponse
import com.example.tawarin.databinding.ListItemSellerOrderBinding
import com.example.tawarin.Utils.convertDate
import com.example.tawarin.Utils.currency
import com.example.tawarin.Utils.striketroughtText

class MyBargainAdapter(private val OnItemClick: OnClickListener) :
    RecyclerView.Adapter<MyBargainAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<GetBuyerOrderResponse>() {
        override fun areItemsTheSame(
            oldItem: GetBuyerOrderResponse,
            newItem: GetBuyerOrderResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetBuyerOrderResponse,
            newItem: GetBuyerOrderResponse
        ): Boolean {
            return oldItem.id == oldItem.id
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<GetBuyerOrderResponse>?) = differ.submitList(value)

    inner class ViewHolder(private val binding: ListItemSellerOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: GetBuyerOrderResponse) {
            val basePrice = currency(data.basePrice!!)
            val priceNego = currency(data.price!!)
            val date = convertDate(data.createdAt!!)
            binding.apply {
                Glide.with(binding.root)
                    .load(data.imageUrl?.first())
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(binding.ivTerjual)
                tvNamaProduct.text = data.productName
                tvHargaProduct.text = basePrice
                tvDitawarProduct.text = priceNego
                tvTanggalTawar.text = date
                if (data.status != "declined") {
                    root.setOnClickListener {
                        OnItemClick.onClickItem(data)
                    }
                }
                if (data.status == "declined") {
                    root.alpha = 0.5f
//                    tvPenawaranProduct.text = "Ditolak"
                    tvHargaProduct.apply {
                        text = striketroughtText(this, basePrice)
                    }
                    tvDitawarProduct.apply {
                        text = priceNego
                    }
                    tvDitawarProduct.setTextColor(Color.parseColor("#C80000"))
                }
                if (data.status == "accepted") {
//                    tvPenawaranProduct.text = "Diterima"
                    tvHargaProduct.apply {
                        text = striketroughtText(this, basePrice)
                    }
                    tvDitawarProduct.setTextColor(Color.parseColor("#00A42E"))
                }
                if (data.status == "pending") {
//                    tvPenawaranProduct.text = "Ditawar"
                    tvHargaProduct.apply {
                        text = striketroughtText(this, basePrice)
                    }
                    tvDitawarProduct.setTextColor(Color.parseColor("#0096FF"))
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(data: GetBuyerOrderResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemSellerOrderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}