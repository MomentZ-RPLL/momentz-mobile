package com.kai.momentz.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.databinding.ActivityHomeBinding
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.login.LoginActivity
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.view.notification.NotificationFragment
import com.kai.momentz.view.post.PostFragment
import com.kai.momentz.view.profile.ProfileFragment
import com.kai.momentz.view.register.RegisterFragment
import com.kai.momentz.view.register.RegisterViewModel
import com.kai.momentz.view.search.SearchFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navView
        val fragmentManager = supportFragmentManager
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, homeFragment, ProfileFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_search -> {
                    val searchFragment = SearchFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, searchFragment, SearchFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_post -> {
                    val postFragment = PostFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, postFragment, PostFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_notifications -> {
                    val notificationFragment = NotificationFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, notificationFragment, NotificationFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_profile -> {
                    fragmentManager.popBackStackImmediate()
                    val profileFragment = ProfileFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, profileFragment, ProfileFragment::class.java.simpleName).commit()
                    true
                }

                else -> false
            }
        }

        val layoutManager = LinearLayoutManager(this)

        setupViewModel()
    }

    private fun setupViewModel(){
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(this)
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
//                binding.greeting.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }


}