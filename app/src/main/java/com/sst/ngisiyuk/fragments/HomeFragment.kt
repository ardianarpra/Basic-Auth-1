package com.sst.ngisiyuk.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.AllServiceAdapter
import com.sst.ngisiyuk.databinding.FragmentHomeBinding
import com.sst.ngisiyuk.databinding.PopupLoginBinding
import com.sst.ngisiyuk.models.ngisiyuk.GetProfil
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.util.ThousandSeparator
import com.sst.ngisiyuk.viewmodels.HistoryViewModel
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
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

    private val layananViewModel : LayananViewModel by activityViewModels()
    private val userVM :UserDataViewModel by activityViewModels()
    private val historyVM :HistoryViewModel by activityViewModels()

    @Inject lateinit var userPrefs:SharedPreferences
    @Inject lateinit var myId :String
    @Inject lateinit var auth:FirebaseAuth
    @Inject @Named("pin") lateinit var pin : String
    @Inject lateinit var id : String



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        println("userPrefs: ${userPrefs.getBoolean("isOpened", false)}")
        println("phoneNumber : ${auth.currentUser?.phoneNumber}")
        dialog = AlertDialog.Builder(requireContext())

        layananViewModel.allServices.observe(viewLifecycleOwner,{
            initServiceRV(it)
        })

        userVM.dataUser.observe(viewLifecycleOwner,{profil ->
            handleButtonClick(profil)
            binding.includeInfo.nominalSaldoUser.text = "Rp. ${thousandSeparator(profil?.data?.saldo ?: 0, ".")}"

        })



        handleLoginButton()
        initNewsCarousel()
        initFeed()

        return binding.root
    }

    private fun initFeed() {
        binding.includeFeed.feedCarousel.apply {
            autoPlay = true
            showIndicator = true
            carouselType = CarouselType.SHOWCASE
            scalingFactor = .15f
            for(i in 0..5){
                addData(CarouselItem(R.drawable.im_buy, "Test"))
            }
        }
    }

    private fun initNewsCarousel() {

        binding.includeNews.newsCarousel.apply {
            autoPlay = true
            infiniteCarousel = true
            for (i in 0..4){
                addData(CarouselItem(R.drawable.im_buy))
            }
        }
    }

    private fun handleLoginButton() {
        if (auth.currentUser == null) {
            binding.includeInfo.homeLoginButton.apply {
                setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthFragment())
                }
            }

        }
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
            if (profil == null) loginDialog.show() else findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransferFragment())
        }
        binding.includeInfo.topUpUser.setOnClickListener {
            if (profil == null) loginDialog.show() else findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTopUpFragment())

        }
    }

    private fun closeLoginDialog() {
        loginDialog.dismiss()
    }

    override fun onResume() {
        super.onResume()
        println("Pin : $pin")
        println("Id : $id")

        if (auth.currentUser == null){
            historyVM.nullifyHistory()
        } else if (id != "") historyVM.getHistory(id)

    }



    private fun initServiceRV(services: ArrayList<Produk>) {
        val adapter = AllServiceAdapter(services)
        binding.serviceRv.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.HORIZONTAL, false)
            setAdapter(adapter)
            itemAnimator = SlideInDownAnimator()
            bringToFront()
        }
    }


}