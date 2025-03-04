package com.cgc.firststep.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cgc.firststep.R


class AcceptedFragment : Fragment() {

    private val bannerImages = listOf(
        R.drawable.banner,
        R.drawable.banner_two,
        R.drawable.banner_three,
        R.drawable.banner_four
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted, container, false)


    }

}