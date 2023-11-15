package com.kai.momentz.view.profile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.FragmentEditProfileBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.UpdateProfileRequest
import com.kai.momentz.model.response.DataProfile
import com.kai.momentz.utils.Validator
import com.kai.momentz.view.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class EditProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileData: DataProfile
    private lateinit var currentUser: User
    private lateinit var updateProfileRequest: UpdateProfileRequest

    private lateinit var nameEditText: LoginInputEditText
    private lateinit var emailEditText: LoginInputEditText
    private lateinit var bioEditText: LoginInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        profileData = arguments?.getParcelable<DataProfile>("user_data")!!
        currentUser = arguments?.getParcelable<User>("current_user")!!

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel(){
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[ProfileViewModel::class.java]


        profileViewModel.updateProfileResponse.observe(requireActivity()) { response ->
            if(response != null){
                if(response.status == "200"){
                    Toast.makeText(requireContext(), "Update Profile Success", Toast.LENGTH_SHORT).show()
                    val fragmentManager = parentFragmentManager
                    fragmentManager.popBackStack()
                }else {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fieldCheck(name: String, email: String, bio: String) : Boolean{
        if(email == profileData.email && name == profileData.name && bio == profileData.bio){
            return false
        }
        return if (!Validator.isValidInputEmail(binding.email.text.toString())) {
            emailEditText.error = getString(R.string.email_not_valid)
            false
        }else {
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(requireActivity())
            .load( profileData.profilePicture)
            .into(binding.profileImage)


        nameEditText = binding.name
        emailEditText = binding.email
        bioEditText = binding.bio

        nameEditText.text = profileData.name!!.toEditable()
        emailEditText.text = profileData.email!!.toEditable()
        bioEditText.text = profileData.bio!!.toEditable()

        binding.edit.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v == binding.edit){
            if(fieldCheck(nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    bioEditText.text.toString())){

                profileViewModel.editProfile(
                    currentUser.token,
                    currentUser.username,
                    null,
                    binding.name.text.toString().toRequestBody("text/plain".toMediaType()),
                    binding.email.text.toString().toRequestBody("text/plain".toMediaType()),
                    binding.bio.text.toString().toRequestBody("text/plain".toMediaType()))
            }

        }
    }
    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}