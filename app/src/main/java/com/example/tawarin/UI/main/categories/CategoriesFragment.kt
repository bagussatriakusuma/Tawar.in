package com.example.tawarin.UI.main.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.R
import com.example.tawarin.UI.main.categories.adapter.CategoriesAdapter
import com.example.tawarin.UI.main.home.HomeViewModel
import com.example.tawarin.UI.main.home.adapter.CategoryAdapter
import com.example.tawarin.databinding.FragmentCategoriesBinding
import com.example.tawarin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val bundleCategories = Bundle()

    companion object{
        const val CATEGORY_ID = "categoriesId"
        const val CATEGORY_NAME = "categoriesName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategory()
        bindView()
        bindViewModel()
    }

    private fun bindViewModel(){
        viewModel.showCategory.observe(viewLifecycleOwner){
            categoriesAdapter.submitData(it.data)
        }
    }

    private fun bindView(){
        categoriesAdapter = CategoriesAdapter(object: CategoriesAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponse.Data) {
                bundleCategories.apply {
                    putInt(CATEGORY_ID, data.id)
                    putString(CATEGORY_NAME, data.name)
                }
                findNavController().navigate(R.id.action_categoriesFragment_to_detailsCategoriesFragment, bundleCategories)
            }
        })
        binding.rvCategories.adapter = categoriesAdapter
    }
}