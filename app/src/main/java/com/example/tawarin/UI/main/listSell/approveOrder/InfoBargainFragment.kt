package com.example.tawarin.UI.main.listSell.approveOrder

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tawarin.Data.api.main.seller.sellerApproveOrder.ApproveOrderRequest
import com.example.tawarin.R
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.ORDER_ID
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.ORDER_STATUS
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.PRODUCT_BID
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.PRODUCT_BID_DATE
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.PRODUCT_IMAGE
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.PRODUCT_NAME
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.PRODUCT_PRICE
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.USER_CITY
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.USER_IMAGE
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.USER_NAME
import com.example.tawarin.UI.main.listSell.DaftarJualFragment.Companion.USER_PHONE_NUMBER
import com.example.tawarin.databinding.FragmentInfoBargainBinding
import com.example.tawarin.Utils.convertDate
import com.example.tawarin.Utils.currency
import com.example.tawarin.Utils.showToastSuccess
import com.example.tawarin.Utils.striketroughtText
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class InfoBargainFragment : Fragment() {
    private var _binding : FragmentInfoBargainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoBargainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBargainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()
    }

    private fun bindViewModel() {
//        val bundlePenawar = arguments
//        val idOrder = bundlePenawar?.getInt(ORDER_ID)
//        val statusOrder = bundlePenawar?.getString(ORDER_STATUS)
//        val namaPenawar = bundlePenawar?.getString(USER_NAME)
//        val kotaPenawar = bundlePenawar?.getString(USER_CITY)
//        val nomorPenawar = bundlePenawar?.getString(USER_PHONE_NUMBER)
//        val gambarPenawar = bundlePenawar?.getString(USER_IMAGE)
//        val namaProduk = bundlePenawar?.getString(PRODUCT_NAME)
//        val hargaAwalProduk = bundlePenawar?.getString(PRODUCT_PRICE)
//        val hargaDitawarProduk = bundlePenawar?.getString(PRODUCT_BID)
//        val gambarProduk = bundlePenawar?.getString(PRODUCT_IMAGE)
//        var status: String

        viewModel.showApproveOrder.observe(viewLifecycleOwner){
//            if(it.status == "accepted"){
//                binding.groupBtnTolakTerima.visibility = View.GONE
//                binding.groupBtnStatusHubungi.visibility = View.VISIBLE
//            }else {
//                showToastSuccess(binding.root, "Tawaran $namaPenawar di Tolak!", resources.getColor(R.color.success))
//                findNavController().navigate(R.id.action_infoPenawarFragment_to_daftarJualFragment)
//            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        val bundlePenawar = arguments
        val idOrder = bundlePenawar?.getInt(ORDER_ID)
        val statusOrder = bundlePenawar?.getString(ORDER_STATUS)
        val namaPenawar = bundlePenawar?.getString(USER_NAME)
        val kotaPenawar = bundlePenawar?.getString(USER_CITY)
        val nomorPenawar = bundlePenawar?.getString(USER_PHONE_NUMBER)
        val gambarPenawar = bundlePenawar?.getString(USER_IMAGE)
        val namaProduk = bundlePenawar?.getString(PRODUCT_NAME)
        val hargaAwalProduk = bundlePenawar?.getString(PRODUCT_PRICE)?.toInt()
        val hargaDitawarProduk = bundlePenawar?.getString(PRODUCT_BID)?.toInt()
        val gambarProduk = bundlePenawar?.getString(PRODUCT_IMAGE)
        var status: String

        binding.cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvNamaPenawar.text = namaPenawar
        binding.tvKotaPenawar.text = kotaPenawar
        Glide.with(requireContext())
            .load(gambarPenawar)
            .placeholder(
                AvatarGenerator
                    .AvatarBuilder(requireContext())
                    .setTextSize(50)
                    .setAvatarSize(200)
                    .toSquare()
                    .setLabel(namaPenawar.toString())
                    .build())
            .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
            .into(binding.ivGambarPenawar)

        binding.tvProductNameTawar.text = namaProduk
        binding.tvProductPriceTawar.text = striketroughtText(binding.tvProductPriceTawar, currency(hargaAwalProduk!!))
        binding.tvProductPriceDitawar.text = currency(hargaDitawarProduk!!)
        binding.tvProductDateTawar.text = convertDate(bundlePenawar?.getString(PRODUCT_BID_DATE).toString())
        Glide.with(requireContext())
            .load(gambarProduk)
            .into(binding.ivProductImageTawar)

//        if(statusOrder == "accepted"){
//            binding.btnTerimaTawar.visibility = View.GONE
//            binding.btnTolakTawar.visibility = View.GONE
//            binding.btnHubungiTawar.visibility = View.VISIBLE
//        }

        if(statusOrder == "pending"){
            binding.btnTerimaTawar.visibility = View.VISIBLE
            binding.btnTolakTawar.visibility = View.VISIBLE
            binding.btnHubungiTawarDisable.visibility = View.VISIBLE
            binding.btnHubungiTawar.visibility = View.GONE
            binding.btnHubungiTawarDisable.isEnabled = false
            binding.btnHubungiTawarDisable.isClickable = false
            binding.tvProductPriceDitawar.setTextColor(Color.parseColor("#0096FF"))
        } else if(statusOrder == "accepted"){
            binding.btnTerimaTawar.visibility = View.GONE
            binding.btnTolakTawar.visibility = View.GONE
            binding.tvDecline.visibility = View.GONE
            binding.btnHubungiTawar.visibility = View.VISIBLE
            binding.btnHubungiTawarDisable.visibility = View.GONE
            binding.tvProductPriceDitawar.setTextColor(Color.parseColor("#00A42E"))
        }

        binding.btnTolakTawar.setOnClickListener {
            val dialogCustom = Dialog(requireContext())
            dialogCustom.setContentView(R.layout.alert_confirmation_decline)
            dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogCustom.setCancelable(false)
            dialogCustom.show()

            dialogCustom.findViewById<Button>(R.id.btnYa).setOnClickListener{
                status = "declined"
                val body = ApproveOrderRequest(
                    status
                )
                if (idOrder != null) {
                    viewModel.acceptDeclineOrder(idOrder, body)
                    findNavController().navigate(R.id.action_infoBargainFragment_to_daftarJualFragment)
                    dialogCustom.dismiss()
                }
            }
            dialogCustom.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                dialogCustom.dismiss()
            }
        }

        binding.btnTerimaTawar.setOnClickListener {
            val dialogCustom = Dialog(requireContext())
            dialogCustom.setContentView(R.layout.alert_confirmation_accept)
            dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogCustom.setCancelable(false)
            dialogCustom.show()

            dialogCustom.findViewById<Button>(R.id.btnYa).setOnClickListener{
                status = "accepted"
                val body = ApproveOrderRequest(
                    status
                )
                if (idOrder != null) {
                    viewModel.acceptDeclineOrder(idOrder, body)
                    findNavController().navigate(R.id.action_infoBargainFragment_to_daftarJualFragment)
                    dialogCustom.dismiss()
                }
            }
            dialogCustom.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                dialogCustom.dismiss()
            }
        }

        binding.btnHubungiTawar.setOnClickListener {
            val dialogCustom = Dialog(requireContext())
            dialogCustom.setContentView(R.layout.alert_confirmation_hubungi)
            dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogCustom.setCancelable(false)
            dialogCustom.show()

            dialogCustom.findViewById<Button>(R.id.btnYa).setOnClickListener{
                val smilingFaceUnicode = 0x1F60A
                val waveUnicode = 0x1F44B
                val stringBuilder1 = StringBuilder()
                val stringBuilder2 = StringBuilder()
                val emoteSmile = stringBuilder1.append(Character.toChars(smilingFaceUnicode))
                val emoteWave = stringBuilder2.append(Character.toChars(waveUnicode))
                val phonenumberPenawar = "+62$nomorPenawar"
                val message = "Halo ${namaPenawar}${emoteWave} Tawaranmu pada product *$namaProduk* telah disetujui oleh penjual dengan harga *${currency(hargaDitawarProduk)}*. Penjual akan menghubungimu secepatnya$emoteSmile"

                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                phonenumberPenawar,
                                message
                            ))
                    )
                )
                dialogCustom.dismiss()
            }
            dialogCustom.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
                dialogCustom.dismiss()
            }
        }

//        binding.btnStatusTawar.setOnClickListener {
//            Toast.makeText(requireContext(), "on-progress", Toast.LENGTH_SHORT).show()
//        }

    }
}