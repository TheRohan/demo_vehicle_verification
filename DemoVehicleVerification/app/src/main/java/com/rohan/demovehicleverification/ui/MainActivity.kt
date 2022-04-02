package com.rohan.demovehicleverification.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.databinding.ActivityMainBinding
import com.rohan.demovehicleverification.other.Utility.checkNetworkEnableAndShowDialog
import com.rohan.demovehicleverification.other.Utility.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var navHostFragment: NavHostFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        checkNetworkEnableAndShowDialog(this)
    }


}