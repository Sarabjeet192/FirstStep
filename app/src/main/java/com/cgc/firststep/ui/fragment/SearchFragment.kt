package com.cgc.firststep.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cgc.firststep.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        val fragmentList = listOf(
            NewBookingFragment(),
            AcceptedFragment(),
            CompletedFragment()
        )

        val titles = listOf("New Booking", "Accepted", "Completed")

        viewPagerAdapter = ViewPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    inner class ViewPagerAdapter(
        fragment: Fragment,
        private val fragmentList: List<Fragment>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment = fragmentList[position]
    }

}
