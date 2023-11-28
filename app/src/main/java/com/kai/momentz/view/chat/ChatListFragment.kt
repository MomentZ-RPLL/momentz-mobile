package com.kai.momentz.view.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.ChatListAdapter
import com.kai.momentz.adapter.SearchAdapter
import com.kai.momentz.databinding.FragmentChatListBinding
import com.kai.momentz.databinding.FragmentFollowBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ChatListDataItem
import com.kai.momentz.model.response.SearchDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowerFollowingFragment
import com.kai.momentz.view.notification.NotificationViewModel
import com.kai.momentz.view.profile.ProfileFragment

class ChatListFragment : Fragment(), ChatListAdapter.ChatListAdapterListener {
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var binding: FragmentChatListBinding
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvChat.layoutManager = linearLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
    }

    private fun setupViewModel(){
        chatViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[ChatViewModel::class.java]

        chatViewModel.getUser().observe(requireActivity()){
            if(it != null){
                this.user = it
            }
        }

        chatViewModel.getChatList(user.token)

        chatViewModel.chatListResponse.observe(requireActivity()){
            if(it != null){
                setChatList(it.data)
            }
        }

        chatViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun setChatList(listChat: List<ChatListDataItem?>?) {
        val chatListAdapter = ChatListAdapter(listChat as List<ChatListDataItem>,
            this, user.id)
        binding.rvChat.adapter = chatListAdapter
    }

    override fun onViewClicked(id: String, username:String, itemView: View) {
        val fragmentManager = parentFragmentManager
        val newFragment = ChatDetailFragment()
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("username", username)
        newFragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}