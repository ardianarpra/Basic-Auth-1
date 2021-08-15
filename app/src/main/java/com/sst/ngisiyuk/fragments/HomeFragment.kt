package com.sst.ngisiyuk.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.balloon
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.AllServiceAdapter
import com.sst.ngisiyuk.databinding.FragmentHomeBinding
import com.sst.ngisiyuk.databinding.PopupLoginBinding
import com.sst.ngisiyuk.models.ngisiyuk.GetProfil
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment(), ThousandSeparator {

    lateinit var binding : FragmentHomeBinding
    private lateinit var dialog:AlertDialog.Builder
    private lateinit var loginDialog : AlertDialog
    lateinit var pinDialog : AestheticDialog.Builder
    val metrics = DisplayMetrics()

    private val layananViewModel : LayananViewModel by activityViewModels()
    private val userVM :UserDataViewModel by activityViewModels()

    @Inject lateinit var userPrefs:SharedPreferences
    @Inject lateinit var myId :String
    @Inject lateinit var auth:FirebaseAuth



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        println("userPrefs: ${userPrefs.getBoolean("isOpened", false)}")
        println("phoneNumber : ${auth.currentUser?.phoneNumber}")
        dialog = AlertDialog.Builder(requireContext())
        pinDialog = AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.SUCCESS)
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        println("Widht: ${metrics.widthPixels}, height:${metrics.heightPixels}")

        layananViewModel.allServices.observe(viewLifecycleOwner,{
            initServiceRV(it)
        })

        userVM.dataUser.observe(viewLifecycleOwner,{profil ->
            handleButtonClick(profil)
            binding.includeInfo.nominalSaldoUser.text = "Rp. ${thousandSeparator(profil?.data?.saldo ?: 0, ".")}"

        })



        initCarousel()
        handleLoginButton()


        return binding.root
    }

    private fun handleLoginButton() {
        if (auth.currentUser == null) {
            binding.homeLoginButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthFragment())
                }
            }

        } else binding.homeLoginButton.visibility = View.GONE
    }

    private fun handleButtonClick(profil: GetProfil?) {


        val popupLoginBinding = PopupLoginBinding.inflate(LayoutInflater.from(requireContext())).apply {
            popupButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthFragment())
                closeLoginDialog()
            }
        }
        loginDialog = dialog.apply {
            setView(popupLoginBinding.root)
        }.create()



        binding.includeInfo.transfer.setOnClickListener {
//            if (profil == null) loginDialog.show() else findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransferFragment())
                pinDialog.show()
        }
        binding.includeInfo.topUpUser.setOnClickListener {
            if (profil == null) loginDialog.show() else findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTopUpFragment())

        }
    }

    private fun closeLoginDialog() {
        loginDialog.dismiss()
    }


    private fun initCarousel() {
        binding.carousel.apply {
            for (i in 0..5){
                addData(CarouselItem(R.drawable.im_buy))
            }
            registerLifecycle(viewLifecycleOwner)
            carouselType = CarouselType.SHOWCASE
        }
    }


    private fun initServiceRV(services: ArrayList<Produk>) {
        val adapter = AllServiceAdapter(services)
        binding.serviceRv.apply {
            layoutManager = GridLayoutManager(requireContext(),4)
            setAdapter(adapter)
            itemAnimator = SlideInDownAnimator()
        }
    }


}