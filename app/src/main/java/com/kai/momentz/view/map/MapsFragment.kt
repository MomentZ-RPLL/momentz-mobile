package com.kai.momentz.view.map

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.kai.momentz.R
import com.kai.momentz.databinding.FragmentMapsBinding
import com.kai.momentz.model.response.TimelineDataItem
import com.kai.momentz.view.ViewModelFactory

class MapsFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var token: String
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()

    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mapViewModel.getTimeline(token)



        mapViewModel.timelineResponse.observe(this) { storyMap ->
            addManyMarker(storyMap.data as List<TimelineDataItem>)
        }

        setMapStyle()
        getMyLocation()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setupViewModel()
        setOverflowMenuColor(R.color.white)
    }

    private fun setOverflowMenuColor(colorResId: Int) {
        val toolbar = binding.toolbar
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_menu_24)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(requireContext(), colorResId))
        toolbar.overflowIcon = wrappedDrawable
    }

    private fun setupViewModel() {
        mapViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[MapViewModel::class.java]

        mapViewModel.getUser().observe(requireActivity()) { user ->
            if (user != null) {
                token = user.token
            }
        }
    }

    private fun addManyMarker(listStoryMap: List<TimelineDataItem>) {
        listStoryMap.forEach { listStory ->

            val lat = listStory.lat
            val lon = listStory.lon

            if (lat != null && lon != null) {
                val latLng = LatLng(lat, lon)

                // Menggunakan Glide untuk memuat gambar dari URL atau sumber daya lainnya
                Glide.with(requireContext())
                    .load(listStory.postMedia)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Handle jika gambar tidak dapat dimuat
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {

                            Handler(Looper.getMainLooper()).post {
                                val resizedBitmap = createResizedBitmapFromDrawable(resource, 200, 200)

                                // Membuat gambar dengan teks dan latar belakang seperti card
                                val finalBitmap = addTextAndCardBackground(resizedBitmap, listStory.username!!)

                                val markerOptions = MarkerOptions().position(latLng)
                                    .title(listStory.username)
                                    .icon(BitmapDescriptorFactory.fromBitmap(finalBitmap))

                                val marker = mMap.addMarker(markerOptions)
                                boundsBuilder.include(latLng)

                                mMap.setOnMarkerClickListener { clickedMarker ->
                                    if (clickedMarker == marker) {
                                        showToast("Marker ${listStory.username} diklik")
                                        true
                                    } else {
                                        false
                                    }
                                }
                            }
                            return true
                        }
                    })
                    .submit()
            }
        }
    }

    private fun addTextAndCardBackground(bitmap: Bitmap?, text: String): Bitmap {
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 30f
            isAntiAlias = true
        }

        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)

        val width = bitmap?.width ?: 0
        val height = bitmap?.height ?: 0


        val margin = 20
        val usernameImageMargin = 10
        val extraPadding = 100

        val cardWidth = width + 2 * margin + extraPadding
        val cardHeight = height + textBounds.height() + 2 * margin + usernameImageMargin + extraPadding

        val resultBitmap = Bitmap.createBitmap(cardWidth, cardHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)

        val cardDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 20f
            setColor(Color.WHITE)
        }
        cardDrawable.setBounds(margin, margin, cardWidth - margin, cardHeight - margin)
        cardDrawable.draw(canvas)

        val roundedDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedDrawable.cornerRadius = 20f
        roundedDrawable.setBounds(
            margin,
            margin + textBounds.height() + usernameImageMargin,
            cardWidth - margin,
            cardHeight - margin
        )
        roundedDrawable.draw(canvas)

        canvas.drawText(text, margin.toFloat(), margin.toFloat() + textBounds.height().toFloat(), paint)

        return resultBitmap
    }

    private fun createBitmapFromDrawable(drawable: Drawable?): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth ?: 0,
            drawable?.intrinsicHeight ?: 0,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    private fun createResizedBitmapFromDrawable(
        drawable: Drawable?,
        desiredWidth: Int,
        desiredHeight: Int
    ): Bitmap {
        val bitmap = createBitmapFromDrawable(drawable)
        return Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true)
    }

    private fun showToast(message: String) {
        // Fungsi ini menampilkan pesan toast. Sesuaikan dengan cara Anda menampilkan informasi di aplikasi Anda.
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}