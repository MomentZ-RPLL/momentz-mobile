package com.kai.momentz.view.notification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.FollowAdapter
import com.kai.momentz.adapter.NotificationAdapter
import com.kai.momentz.databinding.FragmentNotificationBinding
import com.kai.momentz.databinding.FragmentNotificationListBinding
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.model.response.NotificationDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowViewModel
import com.kai.momentz.view.follow.FollowerFollowingFragment


class NotificationListFragment : Fragment() {

    private lateinit var binding: FragmentNotificationListBinding
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationListBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvNotification.layoutManager = linearLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(FollowerFollowingFragment.ARG_SECTION_NUMBER, 0)

        setupViewModel(index!!)
    }

    private fun setupViewModel(index: Int){
        notificationViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[NotificationViewModel::class.java]

        token = notificationViewModel.getToken()

        notificationViewModel.likeNotification(token)

        notificationViewModel.likeNotificationResponse.observe(requireActivity()){ likeNotif ->
            if(likeNotif!=null){
                setNotif(likeNotif.data, index)
            }
        }
    }

    private fun setNotif(notif: List<NotificationDataItem?>?, tabIndex:Int) {
        val listNotifAdapter = NotificationAdapter(notif as List<NotificationDataItem>, fragmentManager, tabIndex)
        binding.rvNotification.adapter = listNotifAdapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}