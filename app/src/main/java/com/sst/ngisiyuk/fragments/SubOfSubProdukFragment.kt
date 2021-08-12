package com.sst.ngisiyuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sst.ngisiyuk.adapters.SubOfSubProdukFragmentAdapter
import com.sst.ngisiyuk.databinding.FragmentSubOfSubProdukBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXX
import com.sst.ngisiyuk.viewmodels.LayananViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubOfSubProdukFragment : Fragment() {
    lateinit var binding: FragmentSubOfSubProdukBinding
    private val layananViewModel: LayananViewModel by activityViewModels()
    @Inject
    lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubOfSubProdukBinding.inflate(inflater, container, false)

        layananViewModel.subProduct.observe(viewLifecycleOwner,{
            it?.data?.let {
                initRV(it)
            }
        })

        return binding.root
    }

    private fun initRV(it: List<DataXXX>) {
        val adapter = SubOfSubProdukFragmentAdapter(it, layananViewModel, id)
        binding.recyclerView.apply {
            setAdapter(adapter)
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }


}