package com.kai.momentz.view.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.ProfilePostAdapter
import com.kai.momentz.databinding.FragmentProfileBinding
import com.kai.momentz.model.response.PostsItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowFragment
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.home.HomeFragment


class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var editProfileButton: Button
    private lateinit var followerTextView: TextView
    private lateinit var postTextView: TextView
    private lateinit var followingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val gridLayoutManager = GridLayoutManager(context, 3) // Mengatur spanCount menjadi 3
        binding.rvPost.layoutManager = gridLayoutManager

        setupViewModel()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupViewModel(){
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(requireActivity()){user ->
            if(user != null){
                profileViewModel.getProfile(user.token, user.username)
                profileViewModel.profileResponse.observe(requireActivity()) { user ->
                    if(user != null){
                        nameTextView.text = user.data!!.name
                        usernameTextView.text = user.data.username
                        bioTextView.text = user.data.bio
                        followingTextView.text = user.data.followingCount.toString()
                        followerTextView.text = user.data.followersCount.toString()
                        val z = 0
                        if (user.data.posts!!.isNotEmpty()){
                            postTextView.text = user.data.posts.count().toString()
                        }else {
                            postTextView.text = z.toString()
                        }
                        setProfilePostData(user.data.posts)
                    }else {
                        Toast.makeText(requireActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setProfilePostData(post : List<PostsItem?>){
        val listPostAdapter = ProfilePostAdapter(post)
        binding.rvPost.adapter = listPostAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = binding.nameTextView
        usernameTextView = binding.usernameTextView
        bioTextView = binding.bioTextView
        editProfileButton = binding.editProfile
        followerTextView = binding.followerTextView
        postTextView = binding.postTextView
        followingTextView = binding.followingTextView

        binding.followingBox.setOnClickListener(this)
        binding.followerBox.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == binding.followingBox){
            val bundle = Bundle()
            bundle.putString("tab", FollowFragment.FOLLOWING_TAB.toString())

            val followFragment = FollowFragment()
            followFragment.arguments = bundle
            val fragmentManager = parentFragmentManager

            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, followFragment, FollowFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        if(v == binding.followerBox){
            val bundle = Bundle()
            bundle.putString("tab", FollowFragment.FOLLOWER_TAB.toString())

            val followFragment = FollowFragment()
            followFragment.arguments = bundle
            val fragmentManager = parentFragmentManager

            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, followFragment, FollowFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

}