package com.kai.momentz.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.databinding.ActivityHomeBinding
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.login.LoginActivity
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.view.follow.FollowerFollowingFragment
import com.kai.momentz.view.notification.NotificationFragment
import com.kai.momentz.view.post.PostFragment
import com.kai.momentz.view.profile.ProfileFragment
import com.kai.momentz.view.search.SearchFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupNavigation()
    }

    private fun setupNavigation(){
        val bottomNavigationView = binding.navView
        val fragmentManager = supportFragmentManager

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName).commit()
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
                    val profileFragment = ProfileFragment()
                    fragmentManager.beginTransaction().replace(R.id.frame_container, profileFragment, ProfileFragment::class.java.simpleName).commit()
                    true
                }

                else -> false
            }
        }
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

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


}