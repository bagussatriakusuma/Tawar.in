package com.example.tawarin.UI.main.account.myBargain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tawarin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeclinedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_declined, container, false)
    }

}