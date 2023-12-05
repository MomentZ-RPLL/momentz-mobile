package com.kai.momentz.view.comment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.adapter.CommentAdapter
import com.kai.momentz.databinding.FragmentCommentBinding
import com.kai.momentz.model.response.CommentsDetailItem
import com.kai.momentz.model.response.PostDetailResponse
import com.kai.momentz.utils.getDate
import com.kai.momentz.utils.getTime
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.profile.ProfileFragment

class CommentFragment : Fragment() , View.OnClickListener, CommentAdapter.CommentListener{

    private lateinit var commentViewModel: CommentViewModel
    private lateinit var binding : FragmentCommentBinding
    private lateinit var token:String
    private lateinit var fragmentManager: FragmentManager
    private var postId:String? = null
    private var username:String? = null
    private var userId:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvComment.layoutManager = linearLayoutManager

        fragmentManager = parentFragmentManager
        return binding.root
    }
    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener(this)
        binding.username.setOnClickListener(this)
        binding.postComment.setOnClickListener(this)
        setupViewModel()
    }

    private fun setupViewModel(){
        postId = arguments?.getString("idPost")

        commentViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[CommentViewModel::class.java]

        commentViewModel.getUser().observe(requireActivity()){user ->
            if (user!=null){
                token = user.token
                if (postId != null) {
                    commentViewModel.getDetailPost(user.token, postId!!)
                }
            }
        }

        commentViewModel.postDetailResponse.observe(requireActivity()){postData ->
            if(postData != null){
                setComment(postData.data!!.comments!!)
                setupView(postData)
            }else{
                Toast.makeText(requireActivity(),"Unknown Error", Toast.LENGTH_SHORT).show()
            }
        }

        commentViewModel.postCommentResponse.observe(requireActivity()){postData ->
            if(postData != null){
                commentViewModel.getDetailPost(token, postId!!)
            }else{
                Toast.makeText(requireActivity(),"Unknown Error", Toast.LENGTH_SHORT).show()
            }
        }

        commentViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupView(postDetail: PostDetailResponse){

        username = arguments?.getString("username")
        userId = postDetail.data!!.idUser.toString()
        val profileImage = arguments?.getString("profileImage")

        Glide.with(this)
            .load(postDetail.data!!.postMedia)
            .into(binding.ivPostmage)

        Glide.with(this)
            .load(profileImage)
            .into(binding.profilePicturePhoto)

        binding.tvLike.text = postDetail.data.likes!!.count().toString()
        binding.tvComment.text = postDetail.data.comments!!.count().toString()
        binding.username.text = username
        binding.time.text = "${getDate(postDetail.data.createdAt!!)}, ${getTime(postDetail.data.createdAt)} "
    }

    private fun setComment(comments: List<CommentsDetailItem?>){
        val reversedCommentList = comments.reversed()
        val listComment = CommentAdapter(reversedCommentList as List<CommentsDetailItem>, this)
        binding.rvComment.adapter = listComment

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        val fragmentManager = parentFragmentManager
        if(v == binding.back){
            fragmentManager.popBackStack()
        }
        if(v == binding.postComment){
            if(binding.commentTextView.text != null){
                commentViewModel.postComment(token, postId!!, binding.commentTextView.text.toString())
                binding.commentTextView.text = null
            }
        }

        if(v == binding.username){
            val newFragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("id", userId)
            newFragment.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.frame_container, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onUsernameClicked(id: Int, username: String, itemView: View) {
        val fragmentManager = parentFragmentManager
        val newFragment = ProfileFragment()
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("id", id.toString())
        newFragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, newFragment)
            .addToBackStack(null)
            .commit()
    }


}