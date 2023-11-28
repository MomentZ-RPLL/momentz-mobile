package com.kai.momentz.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.login.LoginFragment
import com.kai.momentz.view.login.LoginViewModel
import com.kai.momentz.view.map.MapsFragment
import com.kai.momentz.view.onboarding.LandingFragment


class SplashFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val SPLASH_TIME_OUT: Long = 3000
    private  var isFirst: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupViewModel()

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[LoginViewModel::class.java]

        isFirst = loginViewModel.getIsFirst()

        val fragmentManager = parentFragmentManager


        loginViewModel.getUser().observe(requireActivity()) { user ->
            if (user.token.isNotEmpty()) {
                fragmentManager.popBackStackImmediate()
                Handler().postDelayed({
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }, SPLASH_TIME_OUT)
            }else {
                if(!isFirst){
                    Handler().postDelayed({
                        fragmentManager.beginTransaction().apply {
                            replace(R.id.frame_container, LoginFragment())
                            commit()
                        }
                    }, SPLASH_TIME_OUT)
                }else {
                    Handler().postDelayed({
                        fragmentManager.beginTransaction().apply {
                            replace(R.id.frame_container, LandingFragment())
                            commit()
                        }
                    }, SPLASH_TIME_OUT)
                }
            }
        }


    }
}