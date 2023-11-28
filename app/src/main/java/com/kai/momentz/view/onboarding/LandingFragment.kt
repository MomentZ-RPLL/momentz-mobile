package com.kai.momentz.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.databinding.FragmentLandingBinding
import com.kai.momentz.databinding.FragmentLoginBinding
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.login.LoginFragment
import com.kai.momentz.view.login.LoginViewModel


class LandingFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[LoginViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finishAffinity()
        }

        binding.button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentManager = parentFragmentManager
        if(v == binding.button){
            loginViewModel.setIsFirst(false)
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, LoginFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
}