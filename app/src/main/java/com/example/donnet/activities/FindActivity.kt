package com.example.donnet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.donnet.R
import com.example.donnet.databinding.ActivityFindBinding
import com.example.donnet.databinding.ActivityMainBinding

class FindActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFindBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }


}