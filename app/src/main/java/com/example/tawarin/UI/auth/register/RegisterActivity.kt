package com.example.tawarin.UI.auth.register

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.tawarin.UI.auth.register.RegisterViewModel
import com.example.tawarin.R
import com.example.tawarin.UI.auth.login.LoginActivity
import com.example.tawarin.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            binding.tilEmail.error = it
            binding.tilPassword.error = it
        }

        viewModel.shouldShowErrorEmail.observe(this) {
            binding.tilEmail.error = it
        }

        viewModel.shouldShowErrorPassword.observe(this) {
            binding.tilPassword.error = it
        }

        viewModel.shouldShowLoading.observe(this) {
            val dialogCustom = Dialog(this)
            dialogCustom.setContentView(R.layout.alert_loading)
            dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogCustom.setCancelable(false)
            if (it) {
                dialogCustom.show()
            } else {
                dialogCustom.dismiss()
            }
        }


        viewModel.shouldOpenLoginPage.observe(this){
            if (it) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        viewModel.showConfirmation.observe(this){
            val dialogCustom = Dialog(this)
            dialogCustom.setContentView(R.layout.alert_confirmation_register)
            dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val thinkingFaceUnicode = 0x1F914
            val stringBuilder1 = StringBuilder()
            val emoteThinking = stringBuilder1.append(Character.toChars(thinkingFaceUnicode))
            dialogCustom.findViewById<TextView>(R.id.tvCustom).text = "Are you sure want to register with credentials that you filled in the register column? $emoteThinking"
            dialogCustom.show()

            dialogCustom.findViewById<Button>(R.id.btnYa).setOnClickListener{
                viewModel.processToSignUp()
                dialogCustom.dismiss()
            }
            dialogCustom.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                dialogCustom.dismiss()
            }
        }
    }

    private fun bindView() {
        binding.etNamaRegister.doAfterTextChanged {
            viewModel.onChangeFullName(it.toString())
        }
        binding.etEmailRegister.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etPasswordRegister.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.etPhoneRegister.doAfterTextChanged {
            viewModel.onChangePhoneNumber(it.toString())
        }

        binding.etNamaRegister.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilNama.error = "Full name cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilNama.error = null
            }
        }

        binding.etPhoneRegister.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilPhone.error = "Phone number cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilPhone.error = null
            }
        }

//        binding.etCityRegister.doOnTextChanged { text, start, before, count ->
//            if(text!!.isEmpty()) {
//                binding.tilCity.error = "City cannot be empty"
//            } else if(text.isNotEmpty()) {
//                binding.tilCity.error = null
//            }
//        }
//
//        binding.etAddressRegister.doOnTextChanged { text, start, before, count ->
//            if(text!!.isEmpty()) {
//                binding.tilAddress.error = "Address cannot be empty"
//            } else if(text.isNotEmpty()) {
//                binding.tilAddress.error = null
//            }
//        }

        binding.etEmailRegister.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilEmail.error = "Email cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilEmail.error = null
            }
        }

        binding.etPasswordRegister.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilPassword.error = "Password cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilPassword.error = null
            }
        }

//        binding.etCityRegister.doAfterTextChanged {
//            viewModel.onChangeCity(it.toString())
//        }
//        binding.etAddressRegister.doAfterTextChanged {
//            viewModel.onChangeAddress(it.toString())
//        }

        binding.btnDaftarRegister.setOnClickListener {
            viewModel.onValidate()
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val hugFaceUnicode = 0x1F917
        val stringBuilder1 = StringBuilder()
        val emoteHug = stringBuilder1.append(Character.toChars(hugFaceUnicode))

        binding.tvSubHeader.text = "Let’s make a new account for you to buy any products you like. $emoteHug"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}