package com.example.tawarin.UI.main.sell

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tawarin.R
import com.example.tawarin.databinding.FragmentUploadProductBinding
import com.example.tawarin.Utils.listCategory
import com.example.tawarin.Utils.listCategoryId
import com.example.tawarin.Utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class UploadProduct : Fragment() {
    private var _binding: FragmentUploadProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualViewModel by viewModels()

    private var uri: String = ""
    companion object {
        const val NAMA_PRODUCT_KEY = "namaProduk"
        const val HARGA_PRODUCT_KEY = "hargaProduk"
        const val DESKRIPSI_PRODUCT_KEY = "deskripsiProduk"
        const val KATEGORI_PRODUCT_KEY = "kategoriProduk"
        const val ALAMAT_PRODUCT_KEY = "alamatProduk"
        const val IMAGE_PRODUCT_KEY = "imageProduk"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUploadProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        bindViewModel()
        bindView()

    }

    private fun bindViewModel(){
        viewModel.categoryList.observe(viewLifecycleOwner){ kat ->
            if (kat.isNotEmpty()){
                var kategori = ""
                for(element in kat){
                    kategori += ", $element"
                }
                binding.etKategoriProduct.setText(kategori.drop(2))
            }
        }

        viewModel.showSuccess.observe(viewLifecycleOwner){
            showToastSuccess()
            findNavController().navigate(R.id.action_uploadProduct_to_homeFragment)
            listCategoryId.clear()
        }

        val dialogCustom = Dialog(requireContext())
        dialogCustom.setContentView(R.layout.alert_loading)
        dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCustom.setCancelable(false)
        viewModel.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                dialogCustom.show()
            } else {
                dialogCustom.dismiss()
            }
        }
    }

    private fun bindView(){
        binding.cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etKategoriProduct.setOnClickListener {
            val bottomFragment = FragmentPilihCategory(
                update = {
                    viewModel.addCategory(listCategory)
                })
            bottomFragment.show(parentFragmentManager, "Tag")
        }

        binding.ivPhotoProduct.setOnClickListener {
            openImagePicker()
        }

        binding.etKategoriProduct.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilKategori.error = "Product category cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilKategori.error = null
            }
        }

        binding.etNamaProduct.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilNamaProduct.error = "Product name cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilNamaProduct.error = null
            }
        }

        binding.etHargaProduct.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilHargaProduct.error = "Product price cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilHargaProduct.error = null
            }
        }

        binding.etDeskripsiProduct.doOnTextChanged { text, start, before, count ->
            if(text!!.isEmpty()) {
                binding.tilDeskripsiProduct.error = "Product description cannot be empty"
            } else if(text.isNotEmpty()) {
                binding.tilDeskripsiProduct.error = null
            }
        }

//        val bundle = Bundle()
//        binding.btnPreviewProduct.setOnClickListener {
//            resetError()
//            val namaProduk = binding.etNamaProduct.text.toString()
//            val hargaProduk = binding.etHargaProduct.text.toString()
//            val deskripsiProduk = binding.etDeskripsiProduct.text.toString()
//            val kategoriProduk = binding.etKategoriProduct.text.toString()
//            val alamatPenjual = binding.etLokasiProduct.text.toString()
//            val validation = validation(
//                listCategoryId,
//                namaProduk,
//                hargaProduk,
//                deskripsiProduk,
//                alamatPenjual,
//                uri
//            )
//            if (validation == "passed") {
//                bundle.putString(NAMA_PRODUCT_KEY, namaProduk)
//                bundle.putString(HARGA_PRODUCT_KEY, hargaProduk)
//                bundle.putString(DESKRIPSI_PRODUCT_KEY, deskripsiProduk)
//                bundle.putString(KATEGORI_PRODUCT_KEY, kategoriProduk)
//                bundle.putString(ALAMAT_PRODUCT_KEY, alamatPenjual)
//                bundle.putString(IMAGE_PRODUCT_KEY, uri)
////                findNavController().navigate(R.id.action_jualFragment_to_previewFragment, bundle)
//            }
//        }

        binding.btnPostProduct.setOnClickListener {
            resetError()
            val namaProduk = binding.etNamaProduct.text.toString()
            val hargaProduk = binding.etHargaProduct.text.toString()
            val deskripsiProduk = binding.etDeskripsiProduct.text.toString()
//            val alamatPenjual = binding.etLokasiProduct.text.toString()
            val validation = validation(
                listCategoryId,
                namaProduk,
                hargaProduk,
                deskripsiProduk,
//                alamatPenjual,
                uri
            )
            if (validation == "passed") {
                viewModel.uploadProduct(
                    namaProduk,
                    deskripsiProduk,
                    hargaProduk,
                    listCategoryId,
//                    alamatPenjual,
                    uriToFile(Uri.parse(uri), requireContext())
                )
            }
        }
    }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivPhotoProduct)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        loadImage(fileUri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {

                }
            }
        }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    fun validation(listCategory: List<Int>, namaProduk: String, hargaProduk: String, deskripsiProduk: String, uriFoto: String): String {
        when {
            listCategory.isEmpty() -> {
                binding.tilKategori.error = "Product category cannot be empty"
                return "Product category empty!"
            }
            namaProduk.isEmpty() -> {
                binding.tilNamaProduct.error = "Product name cannot be empty"
                return "Product name empty!"
            }
            hargaProduk.isEmpty() -> {
                binding.tilHargaProduct.error = "Product price cannot be empty"
                return "Product price empty!"
            }
            hargaProduk.toInt() > 2000000000 -> {
                binding.tilHargaProduct.error = "Product price cannot up to 2 billion"
                return "Product price exceeds the limit!"
            }
            deskripsiProduk.isEmpty() -> {
                binding.tilDeskripsiProduct.error = "Product description cannot be empty"
                return "Product description empty!"
            }
//            alamatPenjual.isEmpty() -> {
//                binding.tilLokasiProduct.error = "alamat Produk tidak boleh kosong"
//                return "Deskripsi Produk Kosong!"
//            }
            uriFoto.isEmpty() -> {
                Toast.makeText(requireContext(), "Image product cannot be empty", Toast.LENGTH_SHORT).show()
                return "Image product empty!"
            }
            else -> {
                return "passed"
            }
        }
    }

    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(binding.root, "Product berhasil di terbitkan.", Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_close_24, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(30, 150, 30, 0)
        snackBarView.view.setPadding(20, 10, 0, 10)
        snackBarView.view.setBackgroundResource(R.drawable.bg_snackbar)
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    fun resetError() {
        binding.tilKategori.error = null
        binding.tilNamaProduct.error = null
        binding.tilHargaProduct.error = null
        binding.tilDeskripsiProduct.error = null
//        binding.tilLokasiProduct.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}