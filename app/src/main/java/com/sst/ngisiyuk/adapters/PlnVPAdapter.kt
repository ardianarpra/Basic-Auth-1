package com.sst.ngisiyuk.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.PLNPascaFragment
import com.sst.ngisiyuk.fragments.UniversalFragment

class PlnVPAdapter(
    val fragment: Fragment,
    val context: Context,
    val tipe: String
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {



        val plnPascaFragment = PLNPascaFragment()
        val bundle = Bundle()
        bundle.putString("tipe", tipe)
        plnPascaFragment.arguments = bundle
        return when(position){
            0 -> plnPascaFragment
            1 -> UniversalFragment()
            else -> Fragment()
        }
    }
}