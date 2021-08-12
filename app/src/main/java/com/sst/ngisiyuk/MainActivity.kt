package com.sst.ngisiyuk

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.viewmodels.HistoryViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var userDataPrefs : SharedPreferences
    @Inject lateinit var auth :FirebaseAuth

    private val userDataVM:UserDataViewModel by viewModels()
    private val historyVM by viewModels<HistoryViewModel>()


    @SuppressLint("ResourceType", "ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDataVM

        val navigation = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navigation.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
        }

    }
    
    companion object {
        val TAG = "Main Activity"
    }
}