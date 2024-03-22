package com.example.firebasetwo.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.ActivityMainBinding
import com.example.firebasetwo.databinding.ActivityToDoNotesBinding

class ToDoNotesActivity : AppCompatActivity() {


    private lateinit var NavC: NavController
    private lateinit var bind: ActivityToDoNotesBinding
    private lateinit var navHost: NavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityToDoNotesBinding.inflate(layoutInflater)
        setContentView (bind.root)

        navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        NavC = navHost.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavC.navigateUp() || super.onSupportNavigateUp()
    }
}