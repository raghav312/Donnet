package com.example.donnet.activities

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.donnet.R
import com.example.donnet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        binding.btnDonate.setOnClickListener {
            val intent = Intent(this@MainActivity ,DonateRegister::class.java )
            startActivity(intent)
        }
        binding.btnInquire.setOnClickListener {
            val intent = Intent(this@MainActivity ,FindActivity::class.java )
            startActivity(intent)
        }
    }
}