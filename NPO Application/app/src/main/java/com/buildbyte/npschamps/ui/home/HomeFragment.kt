package com.buildbyte.npschamps.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.buildbyte.npschamps.R
import com.buildbyte.npschamps.databinding.FragmentHomeBinding
import com.buildbyte.npschamps.ImageSliderAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageSlider: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private val images = listOf(R.drawable.placehold, R.drawable.placehold2, R.drawable.ph3)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the image slider
        imageSlider = binding.imageSlider
        val adapter = ImageSliderAdapter(images)
        imageSlider.adapter = adapter

        // Start auto-slide for the image slider
        autoSlideImages()

        // Load animations
        val slideInLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
        val slideInRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)

        // Apply animations
        binding.header.startAnimation(slideInLeft)
        binding.firstCard.startAnimation(slideInRight)
        binding.secondCard.startAnimation(slideInRight)
        binding.thirdCard.startAnimation(slideInRight)

        return root
    }

    private fun autoSlideImages() {
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = (imageSlider.currentItem + 1) % images.size
                imageSlider.currentItem = nextItem
                handler.postDelayed(this, 3000) // Change image every 3 seconds
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null) // Stop the auto-slide when view is destroyed
    }
}
