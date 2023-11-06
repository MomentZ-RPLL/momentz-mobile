package com.kai.momentz.view.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.customview.DefaultButton
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.ActivityLoginBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var passwordEditText: LoginInputEditText
    private lateinit var usernameEditText: LoginInputEditText
    private lateinit var loginButton: DefaultButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordEditText = binding.password
        usernameEditText = binding.username
        loginButton = binding.login

        setupViewModel()
        setupAction()
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

                    loginViewModel.loginResponse.observe(this) { loginResponse ->
                        Log.d(ContentValues.TAG, "success response")
                        Toast.makeText(this, loginResponse.message, Toast.LENGTH_SHORT).show()
                        val login = User(loginResponse.data!!.user!!.idUser!!.toString(), loginResponse.data.user!!.name!!, loginResponse.data.token!!)

                        loginViewModel.login(login)

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }

                    loginViewModel.errorResponse.observe(this) { errorResponse ->
                        if(errorResponse.message != null){
                            Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
        binding.letsSignup.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(this)
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(this) {
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