package com.kai.momentz.view.notification.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kai.momentz.view.notification.NotificationListFragment

class NotificationPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = NotificationListFragment()
        fragment.arguments = Bundle().apply {
            putInt(NotificationListFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

}