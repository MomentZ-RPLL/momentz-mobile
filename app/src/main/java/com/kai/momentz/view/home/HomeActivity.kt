package com.kai.momentz.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
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

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

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
                    Log.d("tess", "ini")
                    fragmentManager.beginTransaction().replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_search -> {
                    val searchFragment = SearchFragment()
                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fragmentManager.beginTransaction().replace(R.id.frame_container, searchFragment, SearchFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_post -> {
                    val postFragment = PostFragment()
                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fragmentManager.beginTransaction().replace(R.id.frame_container, postFragment, PostFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_notifications -> {
                    val notificationFragment = NotificationFragment()
                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fragmentManager.beginTransaction().replace(R.id.frame_container, notificationFragment, NotificationFragment::class.java.simpleName).commit()
                    true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    fragmentManager.popBackStackImmediate("ProfileFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
            if (user.token.isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("fromHome", "tes")
                startActivity(intent)
                finish()
            }
        }
    }
}