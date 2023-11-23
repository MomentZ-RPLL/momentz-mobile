package com.kai.momentz.view.follow

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
import com.kai.momentz.adapter.FollowAdapter
import com.kai.momentz.databinding.FragmentFollowerFollowingBinding
import com.kai.momentz.databinding.NotificationItemBinding
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.view.ViewModelFactory


@Suppress("UNCHECKED_CAST")
class FollowerFollowingFragment() : Fragment(), FollowAdapter.FollowAdapterListener {

    private lateinit var followViewModel: FollowViewModel
    private lateinit var binding: FragmentFollowerFollowingBinding
    private lateinit var token: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerFollowingBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = linearLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        setupViewModel(index!!)
    }

    private fun setupViewModel(index: Int){
        followViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[FollowViewModel::class.java]

        followViewModel.getUser().observe(requireActivity()){user ->
            if(user != null){
                token = user.token
                followViewModel.getFollowers(user.token, user.id)
                followViewModel.getFollowing(user.token, user.id)
            }
        }

        followViewModel.listFollowing.observe(requireActivity()) { following ->
            if(following != null){
                followViewModel.listFollowers.observe(requireActivity()) { followers ->
                    if(followers != null){
                        if(index == 1){
                            setFollow(followers.data, following.data)
                        }
                    }else {
                        Toast.makeText(requireActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
                if(index == 2){
                    setFollow(following.data, following.data)
                }
            }else {
                Toast.makeText(requireActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }

        followViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    override fun onFollowClicked(id: Int, itemView: View) {
        followViewModel.followUser(token, id.toString())
        setFollowFollowingButton(itemView)
    }

    override fun onFollowingClicked(id: Int, itemView: View) {
        followViewModel.unfollowUser(token, id.toString())
        setFollowFollowingButton(itemView)
    }

    private fun setFollowFollowingButton(itemView: View){
        followViewModel.followResponse.observe(requireActivity()) { data ->
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

    private fun setFollow(follows: List<FollowItem?>?, following:List<FollowItem?>?) {
        val listFollowAdapter = FollowAdapter(follows as List<FollowItem>,
            fragmentManager,
            following as List<FollowItem>,
            this)
        binding.rvFollow.adapter = listFollowAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}