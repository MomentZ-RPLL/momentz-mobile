package com.kai.momentz.view.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kai.momentz.R
import com.kai.momentz.adapter.ProfilePostAdapter
import com.kai.momentz.databinding.FragmentProfileBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.DataProfile
import com.kai.momentz.model.response.PostsItem
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowFragment


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

    private lateinit var dataProfile: DataProfile
    private lateinit var currentUserData: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.rvPost.layoutManager = gridLayoutManager

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel(){
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(requireActivity()){ user ->
            if(user != null){
                val data = arguments?.getString("username")
                if(data != null){
                    profileViewModel.getProfile(user.token, data.toString())
                }else {
                    profileViewModel.getProfile(user.token, user.username)
                    currentUserData = user
                }
                profileViewModel.profileResponse.observe(requireActivity()) { user ->
                    if(user != null){
                        dataProfile = DataProfile(name = user.data!!.name,
                            bio = user.data.bio,
                            email = user.data.email,
                            profilePicture = user.data.profilePicture)
                        setupView(user)
                        setProfilePostData(user.data.posts!!)
                    }else {
                        Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupView(user : ProfileResponse){
        Log.d("dsfsf", user.data!!.profilePicture!!)

        Glide.with(this)
            .load( user.data.profilePicture)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.profilePicture)

        nameTextView.text = user.data.name
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
        binding.editProfile.setOnClickListener(this)
        binding.profileMenu.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val fragmentManager = parentFragmentManager
        if(v == binding.followingBox){
            val bundle = Bundle()
            bundle.putString("tab", FollowFragment.FOLLOWING_TAB.toString())

            val followFragment = FollowFragment()
            followFragment.arguments = bundle

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

            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, followFragment, FollowFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        if(v == binding.editProfile){
            val editProfileFragment = EditProfileFragment()

            val bundle = Bundle().apply {
                putParcelable("user_data", dataProfile)
                putParcelable("current_user", currentUserData)
            }

            editProfileFragment.arguments = bundle
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_bottom, 0).apply {
                replace(R.id.frame_container, editProfileFragment, EditProfileFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        if(v == binding.profileMenu){
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.profile_bottom_dialog, null)

            val logout = view.findViewById<TextView>(R.id.logout)

            logout.setOnClickListener{
                profileViewModel.logout()
            }

            dialog.setContentView(view)
            dialog.show()
        }
    }

}