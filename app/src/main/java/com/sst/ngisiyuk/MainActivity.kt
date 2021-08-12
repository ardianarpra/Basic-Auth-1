package com.sst.ngisiyuk

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.sst.ngisiyuk.viewmodels.HistoryViewModel
import com.sst.ngisiyuk.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var userDataPrefs : SharedPreferences
    @Inject lateinit var auth :FirebaseAuth

    private val userDataVM:UserDataViewModel by viewModels()
    private val historyVM by viewModels<HistoryViewModel>()

    lateinit var navController :NavController
    var isOpened by Delegates.notNull<Boolean>()

    @SuppressLint("ResourceType", "ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userDataVM
        isOpened = userDataPrefs.getBoolean("isOpened", false)
        val navigation = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val inflater = navigation.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)

        if (isOpened) graph.startDestination = R.id.homeTabContainerFragment
        else graph.startDestination = R.id.splashFragment

        navigation.navController.graph = graph
        navController = navigation.navController



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
            when(destination.id){
                R.id.splashFragment -> supportActionBar?.hide()
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.show()
                }
            }
        }

        if (isOpened){}

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
    
    companion object {
        val TAG = "Main Activity"
    }
}