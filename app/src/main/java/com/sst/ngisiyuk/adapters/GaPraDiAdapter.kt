package com.sst.ngisiyuk.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.SubOfSubProdukFragment
import com.sst.ngisiyuk.fragments.SubProdukOfGaPraDigiFragment

class GaPraDiAdapter(requireParentFragment: Fragment, val subProdukOfGaPraDigiFragment: SubProdukOfGaPraDigiFragment) : FragmentStateAdapter(requireParentFragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> subProdukOfGaPraDigiFragment
            1 -> SubOfSubProdukFragment()
            else -> Fragment()
        }
    }

}
