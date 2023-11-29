package com.kai.momentz.view.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kai.momentz.adapter.CommentAdapter
import com.kai.momentz.databinding.FragmentCommentBinding
import com.kai.momentz.model.response.CommentsItem
import com.kai.momentz.model.response.Datas
import com.kai.momentz.view.ViewModelFactory
import android.content.Intent


class CommentFragment : Fragment() {

    private lateinit var commentViewModel: CommentViewModel
    private lateinit var binding : FragmentCommentBinding
    private lateinit var token:String
    private lateinit var fragmentManager: FragmentManager
    private lateinit var captionTextView: TextView
    private lateinit var usernameTextView: TextView

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

        setupViewModel()
    }

    private fun setupViewModel(){
        commentViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[CommentViewModel::class.java]

        commentViewModel.getUser().observe(requireActivity()){user ->
            if (user!=null){
                token = user.token
                commentViewModel.getPost(user.token, user.id)
            }
        }

        commentViewModel.commentResponse.observe(requireActivity()){comment ->
            if(comment != null){
                setComment(comment.data)
                setupUser()
            }else{
                Toast.makeText(requireActivity(),"Unknown Error", Toast.LENGTH_SHORT).show()
            }
        }
        commentViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }
    }
    private fun setupUser(){

    }
    private fun setComment(comment: Datas?){
        val listComment = CommentAdapter(comment as List<CommentsItem> ,
        fragmentManager)
        binding.rvComment.adapter = listComment
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}