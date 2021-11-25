package com.example.triple_kill_7_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triple_kill_7_2.databinding.AboutActivityBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: AboutActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AboutActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}