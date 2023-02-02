package com.example.tawarin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tawarin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.itemIconTintList = null

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.flfragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.uploadProduct -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.loginActivity -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.registerActivity -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.detailFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
//                R.id.previewFragment -> {
//                    binding.bottomNavigationView.visibility = View.GONE
//                }
                R.id.editProfileFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.updateProductFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.infoBargainFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.daftarJualFragment2 -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

}