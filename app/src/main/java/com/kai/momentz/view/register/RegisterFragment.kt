package com.kai.momentz.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.R
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.FragmentRegisterBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.utils.Validator.isValidInputEmail
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.login.LoginViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var usernameEditText : LoginInputEditText
    private lateinit var nameEditText : LoginInputEditText
    private lateinit var emailEditText : LoginInputEditText
    private lateinit var passwordEditText : LoginInputEditText
    private lateinit var confirmPasswordEditText : LoginInputEditText
    private lateinit var agreementCheckBox : CheckBox
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginViewModel: LoginViewModel

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

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireActivity())
        )[LoginViewModel::class.java]
    }

    private fun fieldCheck(username: String, name: String, email: String,password: String, confirmPassword: String) : Boolean{
        if (username.isEmpty()) {
            usernameEditText.error = getString(R.string.field_cant_empty)
            return false
        }
        if (name.isEmpty()) {
            nameEditText.error = getString(R.string.field_cant_empty)
            return false
        }
        if (email.isEmpty()) {
            emailEditText.error = getString(R.string.field_cant_empty)
            return false
        }
        if (password.isEmpty()) {
            passwordEditText.error = getString(R.string.field_cant_empty)
            return false
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.error = getString(R.string.field_cant_empty)
            return false
        }
        if (!isValidInputEmail(binding.email.text.toString())) {
            emailEditText.error = getString(R.string.email_not_valid)
            return false
        }
        return if(!agreementCheckBox.isChecked) {
            agreementCheckBox.error = getString(R.string.field_cant_empty)
            false
        }else {
            true
        }
    }

    override fun onClick(v: View) {
        val username = usernameEditText.text.toString()
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        val registerRequest = RegisterRequest(username, password, name, email, null)

        if(v == binding.back){
            activity?.onBackPressed()
        }else if(v == binding.signup){
            if (fieldCheck(username, name, email, password, confirmPassword)){
                registerViewModel.registerUser(username.toRequestBody("text/plain".toMediaType()),
                    password.toRequestBody("text/plain".toMediaType()),
                    name.toRequestBody("text/plain".toMediaType()),
                    email.toRequestBody("text/plain".toMediaType()))

                registerViewModel.registerResponse.observe(this){ response ->
                    if (response.status != null){
                        if(response.status == "200"){
                            val profileImageFragment = ProfileImageFragment()
                            val fragmentManager = parentFragmentManager

                            loginViewModel.loginUser(username, password)
                            Toast.makeText(requireActivity(), response.message!!, Toast.LENGTH_SHORT).show()
                            loginViewModel.loginResponse.observe(this) { loginResponse ->
                                val login = User(loginResponse.data!!.user!!.idUser!!.toString(), loginResponse.data.user!!.username!!, loginResponse.data.token!!)

                                loginViewModel.login(login)
                                val bundle = Bundle()
                                bundle.putParcelable("key_person", registerRequest)
                                profileImageFragment.arguments = bundle
                                fragmentManager.popBackStack()
                                fragmentManager.beginTransaction().apply {
                                    replace(R.id.frame_container, profileImageFragment, ProfileImageFragment::class.java.simpleName)
                                    addToBackStack(null)
                                    commit()
                                }
                            }
                        }else {
                            Toast.makeText(requireActivity(), response.message!!, Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(requireActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}