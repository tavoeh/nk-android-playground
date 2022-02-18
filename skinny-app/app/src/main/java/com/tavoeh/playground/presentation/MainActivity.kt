package com.tavoeh.playground.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tavoeh.playground.databinding.ActivityMainBinding

typealias FirstFeatureLanding = com.tavoeh.firstfearture.presentation.LandingActivity
typealias SecondFeatureLanding = com.tavoeh.secondfeature.presentation.LandingActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFirstFeature.setOnClickListener {
            val intent = Intent(this, FirstFeatureLanding::class.java)
            startActivity(intent)
        }
        binding.btnSecondFeature.setOnClickListener {
            val intent = Intent(this, SecondFeatureLanding::class.java)
            startActivity(intent)
        }
    }
}
