package com.cgc.firststep.ui

import NetworkViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityDashboardBinding
import com.cgc.firststep.ui.fragment.HomeFragment
import com.cgc.firststep.ui.fragment.ProductFragment
import com.cgc.firststep.ui.fragment.SearchFragment

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set default fragment
        loadFragment(HomeFragment())

        // Handle Bottom Navigation Clicks
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
                R.id.nav_profile -> loadFragment(ProductFragment())
            }
            true
        }

        // Observe network changes
        networkViewModel.networkLiveData.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.networkStatus.text = "Connected to Internet"
            } else {
                binding.networkStatus.text = "No Internet Connectivity"
            }
        })


        LocalBroadcastManager.getInstance(this@Dashboard)
            .registerReceiver(receiver, IntentFilter("com.cgc.firststep.MY_LOCAL_BROADCAST"))
    }

    // Receiving a local broadcast
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val msg = intent!!.getStringExtra("message")
            binding.broadcastMessage.text = msg
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}