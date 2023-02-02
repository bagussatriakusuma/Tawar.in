package com.example.tawarin.UI.main.account.myBargain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.Data.api.main.seller.sellerOrder.SellerOrderResponse
import com.example.tawarin.R
import com.example.tawarin.UI.main.account.myBargain.adapter.MyBargainAdapter
import com.example.tawarin.UI.main.home.adapter.CategoryAdapter
import com.example.tawarin.UI.main.home.detailProduct.DetailViewModel
import com.example.tawarin.databinding.FragmentAcceptedBinding
import com.example.tawarin.databinding.FragmentPendingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingFragment : Fragment() {
    private var _binding: FragmentPendingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyBargainViewModel by viewModels()
    private lateinit var myBargainAdapter: MyBargainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()

        viewModel.getBuyerOrder()
    }

    private fun bindViewModel() {
        val status = "pending"

        viewModel.showGetBuyerOrder.observe(viewLifecycleOwner){
            myBargainAdapter.submitData(it)
        }
    }

    private fun bindView() {
        myBargainAdapter = MyBargainAdapter(object: MyBargainAdapter.OnClickListener{
            override fun onClickItem(data: GetBuyerOrderResponse) {

            }
        })
        binding.rvDiminatiDaftarJual.adapter = myBargainAdapter
    }
}