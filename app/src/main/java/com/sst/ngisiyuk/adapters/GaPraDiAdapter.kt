package com.sst.ngisiyuk.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.SubOfSubProdukFragment
import com.sst.ngisiyuk.fragments.SubProdukFragment

class GaPraDiAdapter(requireParentFragment: Fragment, requireContext: Context) : FragmentStateAdapter(requireParentFragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> SubProdukFragment()
            1 -> SubOfSubProdukFragment()
            else -> Fragment()
        }
    }

}
