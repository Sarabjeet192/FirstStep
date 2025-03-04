package com.cgc.firststep.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivitySimpleAnimationBinding

class SimpleAnimation : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleAnimationBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saAnimBtn.setOnClickListener {


//            fadeInAnim()
//            objAnim()
//            rotateAnim()
//            valueAnim()
//            infiniteZoom()

        }


    }

    private fun infiniteZoom() {
        ObjectAnimator.ofPropertyValuesHolder(
            binding.saImage,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f), // Zoom In
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f)  // Zoom In
        ).apply {
            duration = 500  // 500ms duration
            repeatCount = ValueAnimator.INFINITE  // Infinite looping
            repeatMode = ValueAnimator.REVERSE  // Reverse for zoom out
            start()
        }
    }

    private fun valueAnim() {
        val colorAnimator = ValueAnimator.ofArgb(Color.BLACK, Color.BLUE)
        colorAnimator.duration = 5000
        colorAnimator.addUpdateListener { animator ->
            binding.saAnimBtn.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimator.start()
    }

    private fun rotateAnim() {
        val rotate = ObjectAnimator.ofFloat(binding.saImage, "rotation", 0f, 360f)
        rotate.duration = 5000
        rotate.start()
    }

    private fun objAnim() {
        ObjectAnimator.ofFloat(binding.saImage, "translationX", 0f, 500f).apply {
            duration = 5000
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                    binding.saImage.translationX = 0f
                }
            })
            start()
        }
    }

    private fun fadeInAnim() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.saImage.startAnimation(fadeIn)
    }

}