package com.sst.ngisiyuk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.slider.FirstFragment
import com.sst.ngisiyuk.fragments.slider.FourthFragment
import com.sst.ngisiyuk.fragments.slider.SecondFragment
import com.sst.ngisiyuk.fragments.slider.ThirdFragment


class SliderPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment{
        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            else -> Fragment()
        }
    }

}