package com.sst.ngisiyuk.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.UniversalFragment
import com.sst.ngisiyuk.fragments.PLNTokenFragment

class PlnVPAdapter(fragment:Fragment, val context: Context): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {



        return when(position){
            0 -> UniversalFragment()
            1 -> PLNTokenFragment()
            else -> Fragment()
        }
    }
}