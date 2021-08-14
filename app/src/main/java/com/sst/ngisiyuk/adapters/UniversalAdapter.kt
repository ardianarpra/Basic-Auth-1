package com.sst.ngisiyuk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sst.ngisiyuk.fragments.inquiry.DetailInquiryFragment
import com.sst.ngisiyuk.fragments.inquiry.IdleInquiryFragment

class UniversalAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> IdleInquiryFragment()
           1 -> DetailInquiryFragment()
           else -> Fragment()
       }
    }

}
