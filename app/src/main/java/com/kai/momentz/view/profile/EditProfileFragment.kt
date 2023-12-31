package com.kai.momentz.view.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.kai.momentz.R
import com.kai.momentz.customview.DefaultButton
import com.kai.momentz.customview.LoginInputEditText
import com.kai.momentz.databinding.FragmentEditProfileBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.DataProfile
import com.kai.momentz.utils.Validator
import com.kai.momentz.utils.createTempFile
import com.kai.momentz.utils.uriToFile
import com.kai.momentz.utils.reduceFileImage
import com.kai.momentz.view.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class EditProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileData: DataProfile
    private lateinit var currentUser: User

    private lateinit var nameEditText: LoginInputEditText
    private lateinit var emailEditText: LoginInputEditText
    private lateinit var bioEditText: LoginInputEditText
    private lateinit var bottomView: View
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        profileData = arguments?.getParcelable<DataProfile>("user_data")!!
        currentUser = arguments?.getParcelable<User>("current_user")!!

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

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

        profileViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                bottomView.findViewById<ShapeableImageView>(R.id.profilePicturePreview).setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireContext().packageManager)

        createTempFile(requireContext().applicationContext).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.kai.momentz",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                bottomView.findViewById<ShapeableImageView>(R.id.profilePicturePreview).setImageURI(uri)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(requireActivity())
            .load( profileData.profilePicture)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.profileImage)


        nameEditText = binding.name
        emailEditText = binding.email
        bioEditText = binding.bio

        nameEditText.text = profileData.name?.toEditable() ?: "".toEditable()
        emailEditText.text = profileData.email?.toEditable() ?: "".toEditable()
        bioEditText.text = profileData.bio?.toEditable() ?: "".toEditable()


        binding.edit.setOnClickListener(this)
        binding.editIcon.setOnClickListener(this)
        binding.back.setOnClickListener(this)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
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
                    binding.bio.text.toString().toRequestBody("text/plain".toMediaType()),
                    false,)
            }
        }
        if(v == binding.editIcon){
            val dialog = BottomSheetDialog(requireContext())
            bottomView = layoutInflater.inflate(R.layout.edit_photo_bottom_dialog, null)
            var delPict = false


            val camSrc = bottomView.findViewById<TextView>(R.id.cameraSource)
            val fileMngrSrc = bottomView.findViewById<TextView>(R.id.folderSource)
            val delete = bottomView.findViewById<TextView>(R.id.deleteImage)
            val edit = bottomView.findViewById<DefaultButton>(R.id.editPhoto)

            Glide.with(requireActivity())
                .load( profileData.profilePicture)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(bottomView.findViewById<ShapeableImageView>(R.id.profilePicturePreview))

            camSrc.setOnClickListener{
                delPict = false
                startTakePhoto()
            }

            fileMngrSrc.setOnClickListener{
                delPict = false
                startGallery()
            }
            delete.setOnClickListener{
                delPict = true
                bottomView.findViewById<ShapeableImageView>(R.id.profilePicturePreview).setImageDrawable(requireContext().getDrawable(R.drawable.profile_picture))
            }
            edit.setOnClickListener{
                if(getFile != null){
                    val file = reduceFileImage(getFile as File)

                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "profile_picture",
                        file.name,
                        requestImageFile
                    )

                    profileViewModel.editProfile(
                        currentUser.token,
                        currentUser.username,
                        imageMultipart,
                        binding.name.text.toString().toRequestBody("text/plain".toMediaType()),
                        binding.email.text.toString().toRequestBody("text/plain".toMediaType()),
                        binding.bio.text.toString().toRequestBody("text/plain".toMediaType()),
                        delPict)

                    dialog.hide()
                }else {
                    if(delPict){
                        profileViewModel.editProfile(
                            currentUser.token,
                            currentUser.username,
                            null,
                            binding.name.text.toString().toRequestBody("text/plain".toMediaType()),
                            binding.email.text.toString().toRequestBody("text/plain".toMediaType()),
                            binding.bio.text.toString().toRequestBody("text/plain".toMediaType()),
                            delPict)

                        dialog.hide()
                    }
                }
            }
            dialog.setContentView(bottomView)
            dialog.show()
        }
        if(v == binding.back){
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}