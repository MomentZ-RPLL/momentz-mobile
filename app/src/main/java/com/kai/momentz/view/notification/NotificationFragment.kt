package com.kai.momentz.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kai.momentz.R
import com.kai.momentz.databinding.FragmentNotificationBinding
import com.kai.momentz.view.notification.adapter.NotificationPagerAdapter

class NotificationFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var binding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewPager = view.findViewById(R.id.notif_view_pager)
        tabs = view.findViewById(R.id.notif_tabs)

        val notificationPagerAdapter = NotificationPagerAdapter(requireActivity() as AppCompatActivity)
        viewPager.adapter = notificationPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.findViewById<View>(R.id.tabs)?.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.findViewById<View>(R.id.tabs)?.visibility = View.INVISIBLE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.white))
    }


    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.notification_tab_text_1,
            R.string.notification_tab_text_2,
            R.string.notification_tab_text_3
        )

        const val LIKE_NOTIFICATION_TAB = 0
        const val COMMENT_NOTIFICATION_TAB = 1
        const val FOLLOW_NOTIFICATION_TAB = 2
    }
}