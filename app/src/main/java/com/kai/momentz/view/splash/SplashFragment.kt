package com.kai.momentz.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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


class SplashFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)



        setupViewModel()

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(requireActivity()) { user ->
            if (user.token.isNotEmpty()) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Handler().postDelayed({
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_container, LoginFragment())
                    transaction.addToBackStack(null)
                    parentFragmentManager.popBackStack()
                    transaction.commit()
                }, SPLASH_TIME_OUT)
            }
        }
    }
}