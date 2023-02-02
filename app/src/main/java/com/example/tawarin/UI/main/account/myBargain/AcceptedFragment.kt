package com.example.tawarin.UI.main.account.myBargain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tawarin.R
import com.example.tawarin.databinding.FragmentAcceptedBinding
import com.example.tawarin.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptedFragment : Fragment() {
    private var _binding: FragmentAcceptedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAcceptedBinding.inflate(inflater, container, false)
        return binding.root
    }

}