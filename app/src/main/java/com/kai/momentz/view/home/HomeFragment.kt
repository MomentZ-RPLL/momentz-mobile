package com.kai.momentz.view.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure want to quit?")
            builder.setPositiveButton("Yes") { _, _ ->
                requireActivity().finishAffinity()
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val dialog = builder.create()
            dialog.show()
        }


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