package com.kai.momentz.view.post

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.imageview.ShapeableImageView
import com.kai.momentz.R
import com.kai.momentz.databinding.FragmentPostBinding
import com.kai.momentz.databinding.FragmentProfileBinding
import com.kai.momentz.utils.reduceFileImage
import com.kai.momentz.utils.uriToFile
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.profile.EditProfileFragment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding
    private lateinit var postViewModel: PostViewModel
    private lateinit var caption : TextView
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private lateinit var currentPhotoPath: String

    private var getFile: File? = null
    private var token: String? = null
    private var latitude : Double? = null
    private var longitude : Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraXButton.setOnClickListener{startTakePhoto()}
        binding.galleryButton.setOnClickListener{startOpenGallery()}
        binding.postButton.setOnClickListener{ this.postImage() }
        setupViewModel()

        return binding.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation(): Pair<Double?, Double?> {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            try {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    return Pair(latitude, longitude)
                }
            } catch (exception: SecurityException) {
                Log.e("Location", "Error: ${exception.localizedMessage}")
                return Pair(null, null)
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return Pair(null, null)
        }

        return Pair(null, null)
    }

    private fun setupViewModel() {
        postViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[PostViewModel::class.java]
        postViewModel.getUser().observe(requireActivity()){
                user -> token = user.token
        }
        postViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }
    }

    private fun postImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val description = binding.descriptionEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "post_media",
                file.name,
                requestImageFile
            )
            if(binding.checkbox.isChecked){
                val (lat, long) = getMyLocation()
                latitude = lat
                longitude = long
            }
            postViewModel.createPost(token!!, imageMultipart, description, latitude, longitude)

            postViewModel.errorResponse.observe(viewLifecycleOwner){response ->
//                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
//                finish()
            }

            postViewModel.errorResponse.observe(viewLifecycleOwner){response ->
                if(response.message!=null){
//                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
//            Toast.makeText(this@PostFragment, "Tolong isi gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun allPermissionsGranted() = PostFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startOpenGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                binding.previewImageView.setImageURI(uri)

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
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
}