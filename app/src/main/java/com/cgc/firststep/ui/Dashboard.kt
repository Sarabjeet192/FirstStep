package com.cgc.firststep.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityDashboardBinding
import com.cgc.firststep.ui.fragment.HomeFragment
import com.cgc.firststep.ui.fragment.ProductFragment
import com.cgc.firststep.ui.fragment.SearchFragment

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

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
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}