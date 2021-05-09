package com.example.donnet.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.donnet.R
import com.example.donnet.databinding.ActivityDonateRegisterBinding
import com.example.donnet.doas.DonnorDoa
import com.example.donnet.models.donnor
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.lang.Exception


class DonateRegister : AppCompatActivity(){

    private lateinit var binding: ActivityDonateRegisterBinding
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0 // A variable which will hold the longitude value.
    private lateinit var gspot: GeoPoint
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.tinder_red)

        if(!Places.isInitialized()){
            Places.initialize(this@DonateRegister , resources.getString(R.string.API_KEY))
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnDonnorCurrLoc.setOnClickListener {

            if (!isLocationEnabled()) {
                    Toast.makeText(
                        this,
                        "Your location provider is turned off. Please turn it on.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)

                } else {

                    Dexter.withContext(this)
                        .withPermissions(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        .withListener(object : MultiplePermissionsListener {
                            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                                if (report!!.areAllPermissionsGranted()) {
                                    requestNewLocationData()
                                }
                            }


                            override fun onPermissionRationaleShouldBeShown(
                                permissions: MutableList<PermissionRequest>?,
                                token: PermissionToken?
                            ) {
                                showRationalDialogForPermissions()
                            }
                        }).onSameThread()
                        .check()
                }
        }


        binding.btnDonnorRegister.setOnClickListener {
            fillDataBase()
        }

    }

    private fun fillDataBase() {
        gspot = GeoPoint(mLatitude  , mLongitude)

        val user  = donnor(
            binding.tinDonorName.text.toString() ,
            binding.tinDonorAge.text.toString().toInt(),
            binding.tinDonorNumber.text.toString(),
            binding.tinDonorBloodtype.text.toString(),
            gspot
            )

        val donorDao = DonnorDoa()

        donorDao.addUser(user)
        Toast.makeText(this@DonateRegister ,
            "User Info taken Successfully!" ,
            Toast.LENGTH_SHORT
        ).show()
        finish()

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            mLatitude = mLastLocation.latitude
            Log.e("Current Latitude", "$mLatitude")
            mLongitude = mLastLocation.longitude
            Log.e("Current Longitude", "$mLongitude")

            Toast.makeText(this@DonateRegister ,
                "User Location taken Successfully!" ,
                Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun isLocationEnabled():Boolean{
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE)as LocationManager
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog,
                                           _ ->
                dialog.dismiss()
            }.show()
    }

}