package com.example.tawarin.UI.main.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductResponse
import com.example.tawarin.R
import com.example.tawarin.UI.main.categories.CategoriesFragment.Companion.CATEGORY_ID
import com.example.tawarin.UI.main.categories.CategoriesFragment.Companion.CATEGORY_NAME
import com.example.tawarin.UI.main.categories.adapter.CategoriesAdapter
import com.example.tawarin.UI.main.home.HomeFragment
import com.example.tawarin.UI.main.home.adapter.ProductAdapter
import com.example.tawarin.databinding.FragmentCategoriesBinding
import com.example.tawarin.databinding.FragmentDetailsCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentDetailsCategoriesBinding
    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()

        val bundleCategories = arguments
        val categoriesId = bundleCategories?.getInt(CATEGORY_ID)

        val status = "available"
        val search = ""
        viewModel.getProductBuyer(status = status, categoryId = categoriesId.toString(), search = search)
    }

    private fun bindViewModel(){
        viewModel.showProductBuyer.observe(viewLifecycleOwner){
            productAdapter.submitData(it.produk)
        }
    }

    private fun bindView(){
        val bundleCategories = arguments
        val categoryName = bundleCategories?.getString(CATEGORY_NAME)
        binding.tvCategoryName.text = categoryName

        productAdapter = ProductAdapter(object: ProductAdapter.OnClickListener{
            override fun onClickItem(data: ProductResponse.Produk) {
//                val bundle = Bundle()
//                bundle.putInt(HomeFragment.PRODUCT_ID, data.id.hashCode())
//                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        })
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            binding.rvProductHome.layoutManager = this
        }
        binding.rvProductHome.isNestedScrollingEnabled = false
        binding.rvProductHome.adapter = productAdapter
    }

}