package com.example.donnet.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donnet.R
import com.example.donnet.adapters.DonorsAdapter
import com.example.donnet.databinding.ActivityFindBinding
import com.example.donnet.doas.DonnorDoa
import com.example.donnet.models.donnor
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.nio.file.attribute.AclEntry
import java.util.*

class FindActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindBinding
    private lateinit var dd: DonnorDoa
    private lateinit var adapter: DonorsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFindBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        buildRecyclerView()



    }

    private fun buildRecyclerView() {
        dd = DonnorDoa()
        val donCollections = dd.usersCollection
        val query = donCollections.orderBy("phone", Query.Direction.DESCENDING)
        val rvoptions = FirestoreRecyclerOptions
            .Builder<donnor>()
            .setQuery(query , donnor::class.java).build()


        adapter = DonorsAdapter(rvoptions)
        adapter.startListening()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }


    override fun onStart() {
        super.onStart()

        Log.e("Started"," Listening")
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
        Log.e("Stopped"," Listening")
    }






}