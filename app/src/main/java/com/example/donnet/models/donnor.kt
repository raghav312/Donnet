package com.example.donnet.models

import android.location.Location
import com.google.firebase.firestore.GeoPoint

data class donnor (
        val name: String ="",
        val age:Int=0 ,
        val phone:String="",
        val blood_type :String="",
        val spot: GeoPoint = GeoPoint(0.0, 0.0))