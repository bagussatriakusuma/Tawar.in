package com.example.tawarin.UI.main.home

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductResponse
import com.example.tawarin.R
import com.example.tawarin.UI.auth.login.LoginActivity
import com.example.tawarin.UI.main.home.adapter.CategoryAdapter
import com.example.tawarin.UI.main.home.adapter.ProductAdapter
import com.example.tawarin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        var result = 0
        const val PRODUCT_ID = "id"
    }
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()
        changeToolbar()
        imageSlider()

//        activity?.window?.statusBarColor = Color.TRANSPARENT

//        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val status = "available"
        val categoryId = ""
        val search = ""
        viewModel.getProductBuyer(status = status, categoryId = categoryId, search = search)
        viewModel.getUserData()
        viewModel.getCategory()
    }

    private fun bindViewModel() {
        viewModel.showLogin.observe(viewLifecycleOwner) {
            Intent(activity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        viewModel.showCategory.observe(viewLifecycleOwner){
            categoryAdapter.submitData(it.data)
        }

        viewModel.showProductBuyer.observe(viewLifecycleOwner){
            productAdapter.submitData(it.produk)
        }

        viewModel.showUser.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.imageUrl.toString())
                .placeholder(
                    AvatarGenerator
                        .AvatarBuilder(requireContext())
                        .setTextSize(50)
                        .setAvatarSize(200)
                        .toSquare()
                        .setLabel(it.fullName.toString())
                        .build()
                )
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.rivUserImage)
        }
    }

    private fun bindView(){
//        binding.tvLogout.setOnClickListener {
//            viewModel.clearCredential()
//        }
//
//        binding.tvEditProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_editProfileFragment)
//        }
//
//        binding.tvSellerProduct.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_daftarJualFragment2)
//        }

        categoryAdapter = CategoryAdapter(object: CategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponse.Data) {
                val status = "available"
                val search = ""
                viewModel.getProductBuyer(categoryId = data.id.toString(), status = status, search = search)
            }
        })
        binding.rvCategoryHome.adapter = categoryAdapter

        productAdapter = ProductAdapter(object: ProductAdapter.OnClickListener{
            override fun onClickItem(data: ProductResponse.Produk) {
                val bundle = Bundle()
                bundle.putInt(PRODUCT_ID, data.id.hashCode())
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        })
//        binding.rvProductHome.layoutManager =
//            GridLayoutManager(requireContext(), 2)
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            binding.rvProductHome.layoutManager = this
        }
        binding.rvProductHome.isNestedScrollingEnabled = false
        binding.rvProductHome.adapter = productAdapter

//        binding.tvPostProduct.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_uploadProduct)
//        }

        binding.etSearchProduct.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                val onSearch = binding.etSearchProduct.text.toString()
                viewModel.getProductBuyer(search = onSearch, status = "available", categoryId = "")
                true
            }else{
                false
            }
        }
    }

    private fun imageSlider() {
        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.banner1))
        imageList.add(SlideModel(R.drawable.banner2))
        imageList.add(SlideModel(R.drawable.banner3))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun changeToolbar() {
        var toolbarColored = false
        var toolbarTransparent = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val bannerHeight =
                    (binding.imageSlider.height / 2) - result - binding.statusBar.height
                val colored = ContextCompat.getColor(requireContext(), R.color.white)
                val transparent =
                    ContextCompat.getColor(requireContext(), android.R.color.transparent)

                when {
                    scrollY > bannerHeight -> {
                        if (toolbarTransparent) {
                            val colorAnimate =
                                ValueAnimator.ofObject(ArgbEvaluator(), transparent, colored)
                            colorAnimate.addUpdateListener {
                                binding.statusBar.setBackgroundColor(it.animatedValue as Int)
                                binding.toolbar.setBackgroundColor(it.animatedValue as Int)
                            }
                            colorAnimate.duration = 250
                            colorAnimate.start()
                            toolbarColored = true
                            toolbarTransparent = false
                        }
                    }
                    else -> {
                        if (toolbarColored) {
                            val colorAnimate =
                                ValueAnimator.ofObject(ArgbEvaluator(), colored, transparent)
                            colorAnimate.addUpdateListener {
                                binding.statusBar.setBackgroundColor(it.animatedValue as Int)
                                binding.toolbar.setBackgroundColor(it.animatedValue as Int)
                            }
                            colorAnimate.duration = 250
                            colorAnimate.start()
                            toolbarColored = false
                            toolbarTransparent = true
                        }
                    }
                }
            }
        }
    }
}
