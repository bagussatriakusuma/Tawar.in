package com.example.tawarin.UI.auth.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.tawarin.R
import com.example.tawarin.UI.auth.register.RegisterActivity
import com.example.tawarin.UI.main.MainActivity
import com.example.tawarin.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViewModel()
        bindView()
    }

    private fun bindViewModel() {
        viewModel.shouldShowErrorEmail.observe(this) {
            binding.tilEmail.error = it
        }

        viewModel.shouldShowErrorPassword.observe(this) {
            binding.tilPassword.error = it
        }

        viewModel.shouldShowError.observe(this) {
            binding.tilEmail.error = it
            binding.tilPassword.error = it
        }

        viewModel.shouldOpenHomePage.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        val dialogCustom = Dialog(this)
        dialogCustom.setContentView(R.layout.alert_loading)
        dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCustom.setCancelable(false)
        viewModel.showLoading.observe(this) {
            if (it) {
                dialogCustom.show()
            } else {
                dialogCustom.dismiss()
            }
        }
    }

    private fun bindView() {
        binding.etEmailLogin.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        binding.etPasswordLogin.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        binding.etEmailLogin.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilEmail.error = "Email cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilEmail.error = null
            }
        }

        binding.etPasswordLogin.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilPassword.error = "Password cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilPassword.error = null
            }
        }

        binding.btnMasukLogin.setOnClickListener {
            viewModel.onClickSignIn()

        }

        binding.tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        binding.ivBack.setOnClickListener {
//            startActivity(Intent(this, OnBoardingActivity::class.java))
//        }

        val smilingFaceUnicode = 0x1F604
        val stringBuilder1 = StringBuilder()
        val emoteSmile = stringBuilder1.append(Character.toChars(smilingFaceUnicode))

        binding.tvSubHeader.text = "You’ve been missed. You can sign in first to see any products you searching for. $emoteSmile"
    }
}