package com.kai.momentz.view.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.customview.DefaultButton
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.ActivityLoginBinding
import com.kai.momentz.databinding.FragmentLoginBinding
import com.kai.momentz.databinding.FragmentRegisterBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.register.RegisterActivity


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var passwordEditText: LoginInputEditText
    private lateinit var usernameEditText: LoginInputEditText
    private lateinit var loginButton: DefaultButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        passwordEditText = binding.password
        usernameEditText = binding.username
        loginButton = binding.login

        setupViewModel()
        setupAction()

        return binding.root
    }

    private fun setupAction(){
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password =  passwordEditText.text.toString()
            when {
                username.isEmpty() -> {
                    usernameEditText.error = getString(R.string.field_cant_empty)
                }
                password.isEmpty() -> {
                    passwordEditText.error = getString(R.string.field_cant_empty)
                }
                else -> {
                    loginViewModel.loginUser(username, password)

                    loginViewModel.loginResponse.observe(requireActivity()) { loginResponse ->
                        Log.d(ContentValues.TAG, "success response")
                        Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_SHORT).show()
                        val login = User(loginResponse.data!!.user!!.idUser!!.toString(), loginResponse.data.user!!.username!!, loginResponse.data.token!!)

                        loginViewModel.login(login)

                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        activity?.finish()
                    }

                    loginViewModel.errorResponse.observe(requireActivity()) { errorResponse ->
                        if(errorResponse.message != null){
                            Toast.makeText(requireContext(), errorResponse.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
        binding.letsSignup.setOnClickListener{
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}