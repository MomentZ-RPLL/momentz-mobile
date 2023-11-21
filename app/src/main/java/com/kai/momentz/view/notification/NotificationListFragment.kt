package com.kai.momentz.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.adapter.CommentNotificationAdapter
import com.kai.momentz.adapter.LikeNotificationAdapter
import com.kai.momentz.databinding.FragmentNotificationListBinding
import com.kai.momentz.model.response.CommentNotificationDataItem
import com.kai.momentz.model.response.LikeNotificationDataItem
import com.kai.momentz.view.ViewModelFactory
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
        notificationViewModel.commentNotification(token)
        notificationViewModel.likeNotificationResponse.observe(requireActivity()){ likeNotif ->
            if(likeNotif!=null){
                if(index==1){
                    setLikeNotif(likeNotif.data)
                }
            }
        }

        notificationViewModel.commentNotificationResponse.observe(requireActivity()){ commentNotif ->
            if(commentNotif!=null){
                if(index==2){
                    setCommentNotif(commentNotif.data)
                }
            }
        }
    }

    private fun setLikeNotif(notif: List<LikeNotificationDataItem?>?) {
        val listNotifAdapter = LikeNotificationAdapter(notif as List<LikeNotificationDataItem>, fragmentManager)
        binding.rvNotification.adapter = listNotifAdapter
    }

    private fun setCommentNotif(notif: List<CommentNotificationDataItem?>?) {
        val listNotifAdapter = CommentNotificationAdapter(notif as List<CommentNotificationDataItem>, fragmentManager)
        binding.rvNotification.adapter = listNotifAdapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}