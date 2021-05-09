package com.example.donnet.doas

import com.example.donnet.models.donnor
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Document

class DonnorDoa {

    private val db  = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("Donnor")

    fun addUser(don: donnor?) {
        don?.let {
            GlobalScope.launch(Dispatchers.IO) {

                usersCollection.document(don.phone).set(it)

            }
        }
    }



}