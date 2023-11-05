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
import com.kai.momentz.view.register.RegisterViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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