package com.example.tawarin.UI.main.home.detailProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tawarin.R
import com.example.tawarin.UI.main.home.HomeFragment.Companion.PRODUCT_ID
import com.example.tawarin.databinding.FragmentDetailBinding
import com.example.tawarin.Utils.currency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    var productName = ""
    var imageURL = ""
    private var isBid = false
    private lateinit var convertBasePrice: String
    private var basePrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()

        val bundle = arguments
        val productID = bundle?.getInt(PRODUCT_ID)
        if(productID != null){
            viewModel.getProductBuyerById(productID)
        }
    }

    private fun bindViewModel(){
        viewModel.showShimerProduct.observe(viewLifecycleOwner){
            if(it){
                binding.shimerDetailsProduct.visibility = View.VISIBLE
            }else{
                binding.shimerDetailsProduct.visibility = View.GONE
            }
        }

        viewModel.showProductDetails.observe(viewLifecycleOwner){
            if (it != null){
                productName = it.produk!!.name!!
                imageURL = it.produk!!.imageUrl.first()

                //product name
                binding.tvProductName.text = it.produk!!.name!!
                //product image
                Glide.with(this@DetailFragment)
                    .load(it.produk!!.imageUrl.first())
                    .into(binding.ivProductDetails)
                //product category
//                var listCategory = ""
//                for (data in it.produk.Category) {
//                    listCategory += ", ${data.name}"
//                }
                binding.tvProductCategory.text = it.produk!!.Category!!.name
                //product baseprice
                convertBasePrice = currency(it.produk!!.basePrice!!)
                binding.tvProductharga.text = convertBasePrice
                basePrice = it.produk!!.basePrice!!
                //image penjual
                Glide.with(this@DetailFragment)
                    .load(it.produk?.User?.foto)
                    .placeholder(
                        AvatarGenerator
                            .AvatarBuilder(requireContext())
                            .setTextSize(50)
                            .setAvatarSize(200)
                            .toSquare()
                            .setLabel(it.produk?.User?.fullName.toString())
                            .build())
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                    .into(binding.rivDetailPhotoPenjual)
                //nama penjual & kota penjual
                binding.tvDetailNamaPenjual.text = it.produk?.User?.fullName
                if(it.produk?.User?.address == null) {
                    binding.tvDetailKotaPenjual.text = "Seller hasn't added a location"
                } else {
                    binding.tvDetailKotaPenjual.text = it.produk?.User?.city
                }
                //product description
                binding.tvDeskripsiProduct.text = it.produk?.description
            }
            val bundle = arguments
            val productID = bundle!!.getInt(PRODUCT_ID)

            binding.btnSayaTertarik.setOnClickListener {
                val bottomFragment = TawarFragment(
                    productID,
                    productName,
                    basePrice,
                    imageURL,
//                    refreshButton = {
//                        viewModel.getBuyerOrder()
//                    }
                )
                bottomFragment.show(parentFragmentManager, "Tag")
            }

            binding.cardBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.showGetBuyerOrder.observe(viewLifecycleOwner){
            val bundle = arguments
            val productID = bundle?.getInt(PRODUCT_ID)

//            for (data in 0 until (it.size ?: 0)) {
//                if (it.productId == productID) {
//                    isBid = true
//                }
//            }
//            if (isBid) {
//                binding.btnSayaTertarik.isEnabled = false
//                binding.btnSayaTertarik.backgroundTintList =
//                    requireContext().getColorStateList(R.color.dark_grey)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}