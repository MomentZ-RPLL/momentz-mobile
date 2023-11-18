package com.kai.momentz.view.follow.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kai.momentz.view.follow.FollowerFollowingFragment
import com.kai.momentz.view.home.HomeFragment
import com.kai.momentz.view.profile.ProfileFragment

class FollowPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFollowingFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

}