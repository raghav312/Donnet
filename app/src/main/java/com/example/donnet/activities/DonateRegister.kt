package com.example.donnet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.core.content.ContextCompat
import com.example.donnet.R
import com.example.donnet.databinding.ActivityDonateRegisterBinding
import com.example.donnet.databinding.ActivityFindBinding
import com.example.donnet.databinding.ActivityMainBinding

class DonateRegister : AppCompatActivity() {
    lateinit var binding: ActivityDonateRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}