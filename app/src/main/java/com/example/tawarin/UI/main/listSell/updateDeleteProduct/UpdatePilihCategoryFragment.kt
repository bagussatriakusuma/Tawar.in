package com.example.tawarin.UI.main.listSell.updateDeleteProduct

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tawarin.R
import com.example.tawarin.UI.main.home.adapter.CategoryAdapter
import com.example.tawarin.UI.main.sell.adapter.CategoryAdapterSell
import com.example.tawarin.databinding.FragmentPilihCategoryBinding
import com.example.tawarin.Utils.listCategory
import com.example.tawarin.Utils.listCategoryId
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePilihCategoryFragment(private val update: ()->Unit) : BottomSheetDialogFragment() {
    private var _binding: FragmentPilihCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateProductViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapterSell

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPilihCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnKirimCategory.setOnClickListener {
            viewModel.addCategory(listCategory)
            Handler().postDelayed({
                update.invoke()
                dismiss()
            }, 1000)
        }
        viewModel.getCategory()

        viewModel.showCategory.observe(viewLifecycleOwner) {
            categoryAdapter.submitData(it.data)
        }

        categoryAdapter = CategoryAdapterSell(
            selected = { selected ->
                listCategory.add(selected.name)
                listCategoryId.add(selected.id)
            },
            unselected = { unselected ->
                listCategory.remove(unselected.name)
                listCategoryId.remove(unselected.id)
            }
        )
        binding.rvPilihCategory.adapter = categoryAdapter
    }
}
