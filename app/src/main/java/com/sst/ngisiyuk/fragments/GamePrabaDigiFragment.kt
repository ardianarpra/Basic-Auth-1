package com.sst.ngisiyuk.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.R
import com.sst.ngisiyuk.adapters.GaPraDiAdapter
import com.sst.ngisiyuk.databinding.FailAnimationLayoutBinding
import com.sst.ngisiyuk.databinding.FragmentGamePrabaDigiBinding
import com.sst.ngisiyuk.viewmodels.LayananViewModel

class GamePrabaDigiFragment : Fragment() {
    lateinit var binding :FragmentGamePrabaDigiBinding
    private val args : GamePrabaDigiFragmentArgs by navArgs()
    private val layananVM :LayananViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamePrabaDigiBinding.inflate(inflater, container, false)
        val bundle = Bundle()
        val subProdukFragment = SubProdukOfGaPraDigiFragment()
        bundle.putString("tipe", args.tipe)
        subProdukFragment.arguments = bundle
        layananVM.nullifySubProduk()

        layananVM.subProduct.observe(viewLifecycleOwner,{
            it?.data?.let {
                binding.gpdViewPager.currentItem = 1
            }
        })
        val adapter = GaPraDiAdapter(requireParentFragment(), subProdukFragment)
        initViewPager(adapter)
        layananVM.getIsiLayanan(args.tipe)

        layananVM.transPembelianResponse.observe(viewLifecycleOwner,{
            it?.let {
                if (it.status){
                    val successBinding = FailAnimationLayoutBinding.inflate(LayoutInflater.from(requireContext()))
                    successBinding.animation.setAnimation(R.raw.anim_success)
                    handleResponsePembelian(successBinding.root)
                } else {
                    val errorBinding = FailAnimationLayoutBinding.inflate(LayoutInflater.from(requireContext()))
                    handleResponsePembelian(errorBinding.root)

                }
            }

        })

        handleBackPressed()


        return binding.root
    }


    private fun handleResponsePembelian(errorBinding: CardView) {
        val baloon = createBalloon(requireContext()){
            setArrowSize(0)
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)
            setArrowPosition(0.7f)
            setCornerRadius(10f)
            setPaddingHorizontal(20)
            backgroundColor = Color.WHITE
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
            setLayout(errorBinding)

        }

        baloon.show(binding.root)
        baloon.setOnBalloonDismissListener {
            layananVM.nullifyStatusPembelian()
        }
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            if (binding.gpdViewPager.currentItem == 1){
                binding.gpdViewPager.currentItem = 0
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }


    private fun initViewPager(adapter: GaPraDiAdapter) {
        binding.gpdViewPager.apply {
            this.adapter = adapter
            isUserInputEnabled = false
        }


    }



}