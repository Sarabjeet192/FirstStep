package com.cgc.firststep.ui.design_from_figma

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityFigmaScreenBinding
import kotlin.math.abs

class FigmaScreen : AppCompatActivity() {

    private lateinit var binding: ActivityFigmaScreenBinding
    private lateinit var gestureDetector: GestureDetector

    private var currentIndex = 0

    private val fragments = listOf(
        HomeFrag(),
        FavoriteFragment(),
        LeaderboardFragment(),
        SettingsFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFigmaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestureDetector = GestureDetector(this, SwipeGestureListener())

        // Load initial fragment
        loadFragment(currentIndex)
        updateUI(currentIndex)

        // Attach click listeners
        binding.dashHome.setOnClickListener { switchTo(0) }
        binding.dashFavorite.setOnClickListener { switchTo(1) }
        binding.dashLeaderboard.setOnClickListener { switchTo(2) }
        binding.dashSettings.setOnClickListener { switchTo(3) }

        // Attach touch listener to detect swipes
        binding.dashContainer.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun switchTo(newIndex: Int) {
        if (newIndex != currentIndex) {
            loadFragment(newIndex)
            updateUI(newIndex)
        }
    }

    private fun loadFragment(newIndex: Int) {
        val (enterAnim, exitAnim) = if (newIndex > currentIndex) {
            R.anim.slide_in_right to R.anim.slide_out_left
        } else {
            R.anim.slide_in_left to R.anim.slide_out_right
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(enterAnim, exitAnim)
            .replace(R.id.dashContainer, fragments[newIndex])
            .commit()

        currentIndex = newIndex
    }

    private fun updateUI(selectedIndex: Int) {
        val allLayouts = listOf(
            binding.dashHome,
            binding.dashFavorite,
            binding.dashLeaderboard,
            binding.dashSettings
        )

        val iconBgSelected = R.drawable.icon_bg
        val iconBgUnselected = R.drawable.transparent_bg
        val textColorSelected = R.color.select
        val textColorUnselected = R.color.unselect
        val typefaceSelected = ResourcesCompat.getFont(this, R.font.extrabold)
        val typefaceUnselected = ResourcesCompat.getFont(this, R.font.regular)

        allLayouts.forEachIndexed { index, layout ->
            val imageView = layout.getChildAt(0) as? ImageView
            val textView = layout.getChildAt(1) as? TextView
            val isSelected = index == selectedIndex

            if (isSelected) {
                imageView?.animate()?.scaleX(1.2f)?.scaleY(1.2f)
                    ?.setDuration(200)?.withEndAction {
                        imageView.scaleX = 1.1f
                        imageView.scaleY = 1.1f
                    }?.start()

                textView?.alpha = 0f
                textView?.animate()?.alpha(1f)?.setDuration(300)?.start()

                imageView?.setBackgroundResource(iconBgSelected)
                imageView?.setColorFilter(getColor(R.color.white))
                textView?.setTextColor(getColor(textColorSelected))
                textView?.typeface = typefaceSelected
            } else {
                imageView?.setBackgroundResource(iconBgUnselected)
                imageView?.setColorFilter(getColor(textColorUnselected))
                textView?.setTextColor(getColor(textColorUnselected))
                textView?.typeface = typefaceUnselected

                imageView?.scaleX = 1f
                imageView?.scaleY = 1f
                textView?.alpha = 1f
            }
        }
    }

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x.minus(e1?.x ?: 0f) ?: 0f
            val diffY = e2.y.minus(e1?.y ?: 0f) ?: 0f

            return if (abs(diffX) > abs(diffY) &&
                abs(diffX) > SWIPE_THRESHOLD &&
                abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (diffX > 0) swipeRight() else swipeLeft()
                true
            } else {
                false
            }
        }
    }

    private fun swipeLeft() {
        if (currentIndex < fragments.size - 1) {
            switchTo(currentIndex + 1)
        }
    }

    private fun swipeRight() {
        if (currentIndex > 0) {
            switchTo(currentIndex - 1)
        }
    }
}
