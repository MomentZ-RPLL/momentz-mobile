package com.kai.momentz.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.FragmentRegisterBinding
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.utils.Validator.isValidInputEmail
import com.kai.momentz.view.ViewModelFactory

class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var usernameEditText : LoginInputEditText
    private lateinit var nameEditText : LoginInputEditText
    private lateinit var emailEditText : LoginInputEditText
    private lateinit var passwordEditText : LoginInputEditText
    private lateinit var confirmPasswordEditText : LoginInputEditText
    private lateinit var agreementCheckBox : CheckBox
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signup.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        usernameEditText = binding.username
        nameEditText = binding.name
        emailEditText = binding.email
        passwordEditText = binding.password
        confirmPasswordEditText = binding.confirmPassword
        agreementCheckBox = binding.agreementCheckbox
    }

    private fun setupViewModel(){
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[RegisterViewModel::class.java]
    }

    override fun onClick(v: View) {
        if(v == binding.back){
            activity?.onBackPressed()
        }else if(v == binding.signup){
            val username = usernameEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (username.isEmpty()) {
                usernameEditText.error = getString(R.string.field_cant_empty)
            }
            if (name.isEmpty()) {
                nameEditText.error = getString(R.string.field_cant_empty)
            }
            if (email.isEmpty()) {
                emailEditText.error = getString(R.string.field_cant_empty)
            }
            if (password.isEmpty()) {
                passwordEditText.error = getString(R.string.field_cant_empty)
            }
            if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.error = getString(R.string.field_cant_empty)
            }
            if (!isValidInputEmail(binding.email.text.toString())) {
                emailEditText.error = getString(R.string.email_not_valid)
            }
            if(!agreementCheckBox.isChecked){
                agreementCheckBox.error = getString(R.string.field_cant_empty)
            }else{

//                val registerRequest = RegisterRequest(username = username,
//                    password = password,
//                    name = name,
//                    email = email)

                registerViewModel.registerUser(username, password, name, email)

                registerViewModel.registerResponse.observe(this){ response ->
                    if (response.status == "200"){
                        val profileImageFragment = ProfileImageFragment()
                        val fragmentManager = parentFragmentManager
                        fragmentManager.beginTransaction().apply {
                            replace(R.id.frame_container, profileImageFragment, ProfileImageFragment::class.java.simpleName)
                            addToBackStack(null)
                            commit()
                        }
                    }else {
                        Toast.makeText(requireActivity(), response.message, Toast.LENGTH_SHORT).show()
                    }
                }



            }

        }
    }
}