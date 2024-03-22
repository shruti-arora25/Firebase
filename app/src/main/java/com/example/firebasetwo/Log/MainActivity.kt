package com.example.firebasetwo.Log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navC: NavController
    private lateinit var bind:ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }


    override fun onSupportNavigateUp(): Boolean {
        navC=findNavController(R.id.fragmentContainerView)
        return navC.navigateUp()||super.onSupportNavigateUp()
    }



}