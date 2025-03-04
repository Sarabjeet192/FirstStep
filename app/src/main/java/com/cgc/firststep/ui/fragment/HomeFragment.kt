package com.cgc.firststep.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cgc.firststep.R
import com.cgc.firststep.databinding.FragmentHomeBinding
import com.cgc.firststep.databinding.ItemBannerBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BannerAdapter

    private val bannerImages = listOf(
        R.drawable.banner,
        R.drawable.banner_two,
        R.drawable.banner_three,
        R.drawable.banner_four
    )

    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupAutoScroll()
    }

    private fun setupViewPager() {
        adapter = BannerAdapter()
        binding.viewPagerBanner.adapter = adapter
        binding.viewPagerBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                updateDotsIndicator(position)
            }
        })
        setupDotsIndicator()
    }

    private fun setupDotsIndicator() {
        val dots = Array(bannerImages.size) { ImageView(requireContext()) }
        binding.dotsIndicator.removeAllViews()

        dots.forEachIndexed { index, imageView ->
            imageView.setImageResource(if (index == 0) R.drawable.dot_selected else R.drawable.dot_unselected)
            val params = LinearLayout.LayoutParams(20, 20)
            params.setMargins(8, 0, 8, 0)
            imageView.layoutParams = params
            binding.dotsIndicator.addView(imageView)
        }
    }

    private fun updateDotsIndicator(position: Int) {
        for (i in 0 until binding.dotsIndicator.childCount) {
            val imageView = binding.dotsIndicator.getChildAt(i) as ImageView
            imageView.setImageResource(if (i == position) R.drawable.dot_selected else R.drawable.dot_unselected)
        }
    }

    private fun setupAutoScroll() {
        val runnable = object : Runnable {
            override fun run() {
                if (adapter.itemCount > 0) {
                    currentPage = (currentPage + 1) % adapter.itemCount
                    binding.viewPagerBanner.setCurrentItem(currentPage, true)
                    handler.postDelayed(this, 3000) // Auto-scroll every 3 seconds
                }
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Stop auto-scroll when fragment is destroyed
    }


    inner class BannerAdapter() : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

        inner class BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(imageResId: Int) {
                binding.imageViewBanner.setImageResource(imageResId)
            }

            fun setPos(mPos: String) {
                binding.bannerCount.text = mPos
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BannerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
            holder.bind(bannerImages[position])
            holder.setPos("Banner ${(position+1)}")
        }

        override fun getItemCount(): Int = bannerImages.size
    }
}