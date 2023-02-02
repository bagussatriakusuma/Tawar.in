package com.example.tawarin.UI.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tawarin.UI.onboarding.adapter.OnBoardingAdapter
import com.example.tawarin.databinding.ActivityOnBoardingBinding
import com.example.tawarin.Utils.Transform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private var _binding: ActivityOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listOnBoarding = listOf(
            FirstOnBoardingFragment(),
            SecondOnBoardingFragment(),
            ThirdOnBoardingFragment()
        )

        val adapter = OnBoardingAdapter(supportFragmentManager, listOnBoarding)
        binding.vpOnBoarding.adapter = adapter
        binding.tlIndicator.setViewPager(binding.vpOnBoarding)
//        binding.vpOnBoarding.setPageTransformer(true, Transform())


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}