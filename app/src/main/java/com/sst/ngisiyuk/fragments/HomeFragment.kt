package com.sst.ngisiyuk.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.AllServiceAdapter
import com.sst.ngisiyuk.databinding.FragmentHomeBinding
import com.sst.ngisiyuk.models.ngisiyuk.GetProfil
import com.sst.ngisiyuk.models.ngisiyuk.Produk
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    private val layananViewModel : LayananViewModel by activityViewModels()
    private val userVM :UserDataViewModel by activityViewModels()

    @Inject lateinit var userPrefs:SharedPreferences
    @Inject lateinit var myId :String
    @Inject lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        println("userPrefs: ${userPrefs.getBoolean("isOpened", false)}")
        println("phoneNumber : ${auth.currentUser?.phoneNumber}")



        layananViewModel.allServices.observe(viewLifecycleOwner,{
            initServiceRV(it)
        })

        userVM.dataUser.observe(viewLifecycleOwner,{profil ->
            handleUserPrefs(profil)
        })



        initCarousel()
        handleTopUp()
        handleTransfer()

        return binding.root
    }

    private fun handleTransfer() {
        binding.includeInfo.transfer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransferFragment())
        }
    }

    private fun handleTopUp() {
        binding.includeInfo.topUpUser.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTopUpFragment())
        }
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

    private fun handleUserPrefs(profil: GetProfil?) {
        if(profil == null){
            userPrefs.edit().clear().apply()

        } else {
            userPrefs.edit().putString("id", profil.data.id).apply()
            userPrefs.edit().putString("pin", profil.data.id).apply()

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

    override fun onResume() {
        super.onResume()
        if(auth.currentUser != null && userVM.dataUser.value == null){
            println("getProfile")
            userVM.getUserProfile()
        }

        if (userVM.dataUser.value == null){
            userVM.getUserProfile()
        }

    }



}