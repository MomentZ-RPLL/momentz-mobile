package com.kai.momentz.view.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.kai.momentz.R
import com.kai.momentz.customview.DefaultButton
import com.kai.momentz.databinding.FragmentProfileImageBinding
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.DataProfile
import com.kai.momentz.utils.reduceFileImage
import com.kai.momentz.utils.uriToFile
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.home.HomeActivity
import com.kai.momentz.view.profile.EditProfileFragment
import com.kai.momentz.view.profile.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class ProfileImageFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileImageBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var currentPhotoPath: String
    private lateinit var bottomView: View
    private var getFile: File? = null
    private lateinit var currentUser: RegisterRequest
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileImageBinding.inflate(inflater, container, false)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        currentUser = (arguments?.getParcelable("key_person") as? RegisterRequest)!!

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel(){
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[ProfileViewModel::class.java]


        token = profileViewModel.getToken()
        profileViewModel.getProfile(token, currentUser.username!!)

        profileViewModel.updateProfileResponse.observe(requireActivity()) { response ->
            if(response != null){
                if(response.status == "200"){
                    Toast.makeText(requireContext(), "Edit Photo Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }

        profileViewModel.profileResponse.observe(requireActivity()) { user ->
            if(user != null){
                Glide.with(requireActivity())
                    .load( user.data!!.profilePicture)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.profilePicture)
            }
        }

        profileViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
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
                bottomView.findViewById<ShapeableImageView>(R.id.profilePicturePreview).setImageBitmap(
                    BitmapFactory.decodeFile(file.path))
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireContext().packageManager)

        com.kai.momentz.utils.createTempFile(requireContext().applicationContext).also {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.editIcon.setOnClickListener(this)
        binding.next.setOnClickListener(this)
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
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View) {
        if(v == binding.editIcon){
            val dialog = BottomSheetDialog(requireContext())
            bottomView = layoutInflater.inflate(R.layout.edit_photo_bottom_dialog, null)
            var delPict = false


            val camSrc = bottomView.findViewById<TextView>(R.id.cameraSource)
            val fileMngrSrc = bottomView.findViewById<TextView>(R.id.folderSource)
            val delete = bottomView.findViewById<TextView>(R.id.deleteImage)
            val edit = bottomView.findViewById<DefaultButton>(R.id.editPhoto)

            Glide.with(requireActivity())
                .load( resources.getDrawable(R.drawable.profile_picture, null))
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
                        token,
                        currentUser.username!!,
                        imageMultipart,
                        currentUser.name.toRequestBody("text/plain".toMediaType()),
                        currentUser.email.toRequestBody("text/plain".toMediaType()),
                        "".toRequestBody("text/plain".toMediaType()),
                        delPict)

                    dialog.hide()
                }else {
                    if(delPict){
                        profileViewModel.editProfile(
                            token,
                            currentUser.username!!,
                            null,
                            currentUser.name.toRequestBody("text/plain".toMediaType()),
                            currentUser.email.toRequestBody("text/plain".toMediaType()),
                            "".toRequestBody("text/plain".toMediaType()),
                            delPict)
                        dialog.hide()
                    }
                }
            }
            dialog.setContentView(bottomView)
            dialog.show()
        }
        if(v==binding.next){
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


}