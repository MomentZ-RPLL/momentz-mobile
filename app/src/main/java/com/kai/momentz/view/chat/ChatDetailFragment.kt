package com.kai.momentz.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.ChatDetailListAdapter
import com.kai.momentz.databinding.FragmentChatDetailBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ChatDetailListDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.profile.ProfileFragment


class ChatDetailFragment : Fragment(), ChatDetailListAdapter.ChatDetailListAdapterListener {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var binding: FragmentChatDetailBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerGchat.layoutManager = linearLayoutManager

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

        val data = arguments?.getString("id")

        chatViewModel.getChatList(user.token)
        chatViewModel.getChatDetail(user.token, data!!)

        chatViewModel.chatDetailResponse.observe(requireActivity()){
            if(it != null){
                setDetailChatList(it.data)
            }
        }
    }

    private fun setDetailChatList(listChat: List<ChatDetailListDataItem?>?) {
        val reversedList = listChat?.filterNotNull()?.reversed()

        val chatListAdapter = ChatDetailListAdapter(reversedList as List<ChatDetailListDataItem>,
            this, user.id.toInt())

        binding.recyclerGchat.adapter = chatListAdapter

        reversedList.size.let {
            binding.recyclerGchat.scrollToPosition(it - 1)
        }
    }

    override fun onProfileImageClicked(username: String, itemView: View) {
        val fragmentManager = parentFragmentManager
        val newFragment = ProfileFragment()
        val bundle = Bundle()
        bundle.putString("username", user.username)
        newFragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, newFragment)
            .addToBackStack(null)
            .commit()
    }
}