package com.kai.momentz.view.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.FollowAdapter
import com.kai.momentz.databinding.FragmentFollowerFollowingBinding
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.view.ViewModelFactory


@Suppress("UNCHECKED_CAST")
class FollowerFollowingFragment() : Fragment() {

    private lateinit var followViewModel: FollowViewModel
    private lateinit var binding: FragmentFollowerFollowingBinding
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
                followViewModel.getFollowers(user.token, user.id)
                followViewModel.getFollowing(user.token, user.id)

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
            }
        }
    }

    private fun setFollow(follows: List<FollowItem?>?, following:List<FollowItem?>?) {
        val listFollowAdapter = FollowAdapter(follows as List<FollowItem>, fragmentManager, following as List<FollowItem>)
        binding.rvFollow.adapter = listFollowAdapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}