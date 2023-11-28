package com.kai.momentz.view.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.FollowAdapter
import com.kai.momentz.adapter.TimeLineAdapter
import com.kai.momentz.databinding.FragmentFollowBinding
import com.kai.momentz.databinding.FragmentHomeBinding
import com.kai.momentz.model.response.DataItem
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.follow.FollowViewModel
import com.kai.momentz.view.follow.FollowerFollowingFragment.Companion.ARG_SECTION_NUMBER

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = linearLayoutManager
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER,0)
        setupViewModel()

    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(requireActivity()) { user ->
            if (user != null) {
                token = user.token
                homeViewModel.getTimeline(user.token)
            }
        }

        homeViewModel.timelineResponse.observe(requireActivity()){ timeline ->
            if(timeline != null){
                setTimeline(timeline.data)
            }else{
                Toast.makeText(requireActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setTimeline(timeline: List<DataItem?>?) {
        val listTimeline = TimeLineAdapter(timeline as List<DataItem>,
            fragmentManager)
        binding.rvUser.adapter = listTimeline
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}