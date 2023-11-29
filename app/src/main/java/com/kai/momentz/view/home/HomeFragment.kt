package com.kai.momentz.view.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.FollowAdapter
import com.kai.momentz.adapter.TimelineAdapter
import com.kai.momentz.databinding.FragmentFollowBinding
import com.kai.momentz.databinding.FragmentHomeBinding
import com.kai.momentz.model.response.DataItem
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.chat.ChatListFragment
import com.kai.momentz.view.follow.FollowViewModel
import com.kai.momentz.view.follow.FollowerFollowingFragment.Companion.ARG_SECTION_NUMBER
import com.kai.momentz.view.map.MapsFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var token: String
    private lateinit var fragmentManager:FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = linearLayoutManager

        fragmentManager = parentFragmentManager

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

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

        homeViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

    }
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val fragmentManager = parentFragmentManager
        when (item.itemId) {
            R.id.chatMenu -> {
                val chatFragment = ChatListFragment()
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, chatFragment, ChatListFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
                return true
            }

            R.id.mapMenu -> {
                val mapFragment = MapsFragment()
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mapFragment, MapsFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun setTimeline(timeline: List<DataItem?>?) {
        val listTimeline = TimelineAdapter(timeline as List<DataItem>,
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