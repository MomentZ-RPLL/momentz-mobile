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
import com.kai.momentz.model.request.SendMessageRequest
import com.kai.momentz.model.response.ChatDetailListDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.profile.ProfileFragment


class ChatDetailFragment : Fragment(), ChatDetailListAdapter.ChatDetailListAdapterListener, View.OnClickListener {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var binding: FragmentChatDetailBinding
    private lateinit var user: User
    private lateinit var dataId: String
    private lateinit var dataUsername: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        dataId = arguments?.getString("id")!!
        dataUsername = arguments?.getString("username")!!

        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerGchat.layoutManager = linearLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGchatSend.setOnClickListener(this)

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
        chatViewModel.getChatDetail(user.token, dataId)

        chatViewModel.chatDetailResponse.observe(requireActivity()){
            if(it != null){
                setDetailChatList(it.data)
            }
        }

        chatViewModel.sendChatResponse.observe(requireActivity()){
            if(it != null){
                chatViewModel.getChatDetail(user.token, dataId)
            }
        }

    }

    private fun setDetailChatList(listChat: List<ChatDetailListDataItem?>?) {
        val reversedList = listChat?.filterNotNull()?.reversed()

        val chatListAdapter = ChatDetailListAdapter(reversedList as List<ChatDetailListDataItem>,
            this, user.id.toInt())

        binding.recyclerGchat.adapter = chatListAdapter

        binding.chatUsername.text = dataUsername

        reversedList.size.let {
            binding.recyclerGchat.scrollToPosition(it - 1)
        }
    }

    override fun onProfileImageClicked(username: String, itemView: View) {
        val fragmentManager = parentFragmentManager
        val newFragment = ProfileFragment()

        val bundle = Bundle()
        bundle.putString("username", dataUsername)
        newFragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(v: View?) {
        if(v == binding.buttonGchatSend){
            if(binding.editGchatMessage.text.toString().isNotEmpty()){
                val sendMessageRequest = SendMessageRequest(binding.editGchatMessage.text.toString())
                chatViewModel.sendChat(user.token, dataId, sendMessageRequest)
                binding.editGchatMessage.text = null
            }
        }
    }
}