package com.example.tawarin.UI.main.home.detailProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderRequest
import com.example.tawarin.R
import com.example.tawarin.databinding.FragmentTawarBinding
import com.example.tawarin.Utils.currency
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class TawarFragment (
    private val productId: Int,
    private val namaProduct: String,
    private val hargaProduct: Int,
    private val gambarProduct: String,
//    private val refreshButton: () -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: FragmentTawarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTawarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()

        val smilingFaceUnicode = 0x1F60A
        val stringBuilder1 = StringBuilder()
        val emoteSmile = stringBuilder1.append(Character.toChars(smilingFaceUnicode))

//        binding.textView10.text = "Masukan harga tawar anda$emoteSmile"

        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    private fun bindViewModel(){
        viewModel.showBuyerOrder.observe(viewLifecycleOwner){
            Toast.makeText(context, "Harga Tawarmu berhasil dikirim ke Penjual", Toast.LENGTH_SHORT).show()
//            refreshButton.invoke()
        }
        viewModel.showError.observe(viewLifecycleOwner){
            binding.tilHargaTawar.error = it
        }
    }

    private fun bindView(){
        binding.etHargaTawarProduct.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilHargaTawar.error = "Bargain price cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilHargaTawar.error = null
            }
        }

        binding.tvProductName.text = namaProduct
        binding.tvProductPrice.text = currency(hargaProduct)
        Glide.with(binding.root)
            .load(gambarProduct)
            .into(binding.ivProductImage)

//        binding.btnTawarProduct.setOnClickListener {
//            validation()
//        }

        binding.btnTawarProduct.setOnClickListener {
            if (binding.etHargaTawarProduct.text.isNullOrEmpty()) {
                binding.tilHargaTawar.error = "Bargain price cannot be empty"
            } else if(binding.etHargaTawarProduct.text.toString().toInt() >= hargaProduct){
                binding.tilHargaTawar.error = "Your bargain is higher than the product price"
            } else if(binding.etHargaTawarProduct.text.toString().toInt() <= (hargaProduct - (hargaProduct * 0.3))){
                binding.tilHargaTawar.error = "Bargain cannot less than 30% of product price"
            }else {
                val inputHargaTawar = binding.etHargaTawarProduct.text
                val requestHargaTawar = BuyerOrderRequest(productId.toString(), inputHargaTawar.toString())
                viewModel.postBuyerOrder(requestHargaTawar)
        }
    }

//    private fun validation() {
//        if (binding.etHargaTawarProduct.text.isNullOrEmpty()) {
//            binding.containerHargaTawar.error = "Input tawar harga tidak boleh kosong"
//        }else if(binding.etHargaTawarProduct.text.toString().toInt() >= hargaProduct){
//            binding.containerHargaTawar.error = "Tawaranmu lebih tinggi dari harga produk"
//        }else {
//            viewModel.getToken()
//            viewModel.showToken.observe(viewLifecycleOwner) {
//                val inputHargaTawar = binding.etHargaTawarProduct.text
//                val requestHargaTawar =
//                    BuyerOrderRequest(productId.toString(), inputHargaTawar.toString())
//                viewModel.postBuyerOrder(requestHargaTawar)
//            }
//        }
    }
}