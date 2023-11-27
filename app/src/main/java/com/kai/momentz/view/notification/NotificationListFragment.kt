package com.kai.momentz.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.CommentNotificationAdapter
import com.kai.momentz.adapter.FollowNotificationAdapter
import com.kai.momentz.adapter.LikeNotificationAdapter
import com.kai.momentz.databinding.FragmentNotificationItemListBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.CommentNotificationDataItem
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.model.response.FollowNotificationDataItem
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.LikeNotificationDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowerFollowingFragment


class NotificationListFragment : Fragment(), FollowNotificationAdapter.FollowNotificationAdapterListener {

    private lateinit var binding: FragmentNotificationItemListBinding
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var user: User


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationItemListBinding.inflate(inflater, container, false)
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

       notificationViewModel.getUser().observe(requireActivity()){
            user = it
        }
        notificationViewModel.getFollowing(user.token, user.id)
        notificationViewModel.likeNotification(user.token)
        notificationViewModel.commentNotification(user.token)
        notificationViewModel.followNotification(user.token)

        notificationViewModel.listFollowing.observe(requireActivity()){ listFollowing ->
            if(listFollowing!=null){
                notificationViewModel.followNotificationResponse.observe(requireActivity()){ followNotification ->
                    if(followNotification!=null){
                        if(index==3){
                            setFollowNotif(followNotification.data, listFollowing)
                        }
                    }
                }
            }
        }


        notificationViewModel.likeNotificationResponse.observe(requireActivity()){ likeNotification ->
            if(likeNotification!=null){
                if(index==1){
                    setLikeNotif(likeNotification.data)
                }
            }
        }

        notificationViewModel.commentNotificationResponse.observe(requireActivity()){ commentNotification ->
            if(commentNotification!=null){
                if(index==2){
                    setCommentNotif(commentNotification.data)
                }
            }
        }

        notificationViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
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

    private fun setFollowNotif(notif: List<FollowNotificationDataItem?>?, followingResponse:FollowingResponse) {
        val listNotifAdapter = FollowNotificationAdapter(notif as List<FollowNotificationDataItem>,
            fragmentManager,
            followingResponse.data as List<FollowItem>,
            this)
        binding.rvNotification.adapter = listNotifAdapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onFollowClicked(id: Int, itemView: View) {
        notificationViewModel.followUser(user.token, id.toString())
        setFollowFollowingButton(itemView)
    }

    override fun onFollowingClicked(id: Int, itemView: View) {
        notificationViewModel.unfollowUser(user.token, id.toString())
        setFollowFollowingButton(itemView)
    }

    private fun setFollowFollowingButton(itemView: View){
        notificationViewModel.followResponse.observe(requireActivity()) { data ->
            if(data != null){
                if(data.status =="200"){
                    if(itemView.findViewById<View>(R.id.follow).isVisible){
                        itemView.findViewById<View>(R.id.follow).visibility = View.GONE
                        itemView.findViewById<View>(R.id.following).visibility = View.VISIBLE
                    }else{
                        itemView.findViewById<View>(R.id.follow).visibility = View.VISIBLE
                        itemView.findViewById<View>(R.id.following).visibility = View.GONE
                    }
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}