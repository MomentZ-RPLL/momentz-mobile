package com.kai.momentz.view.home

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.databinding.ActivityHomeFragmentBinding
import com.kai.momentz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager

//        setupViewModel()
    }

//    private fun setupViewModel() {
//        homeViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
//        )[HomeViewModel::class.java]
//        homeViewModel.getUser().observe(this) { user ->
//            if (user.accessToken.isNotEmpty()) {
//                homeViewModel.getProfileUser(user.accessToken)
//            } else {
//                Log.e(ContentValues.TAG, "onFailure")
//            }
//        }
//        homeViewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
//
//        homeViewModel.profileUser.observe(this) { age ->
//            setUpViewModelId(age.data.age)
//        }
//
//    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}