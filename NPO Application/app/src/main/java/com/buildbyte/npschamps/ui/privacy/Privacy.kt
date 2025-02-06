package com.buildbyte.npschamps.ui.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.buildbyte.npschamps.R
import com.buildbyte.npschamps.databinding.FragmentPrivacyBinding

class Privacy : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load animations
        val slideInLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
        val slideInRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)

        // Apply animations to the title and sections
        binding.privacyPolicyTitle.startAnimation(slideInLeft)
        binding.privacyIntro.startAnimation(slideInRight)
        binding.infoCollectionTitle.startAnimation(slideInLeft)
        binding.infoCollectionDetails.startAnimation(slideInRight)
        binding.infoUseTitle.startAnimation(slideInLeft)
        binding.infoUseDetails.startAnimation(slideInRight)
        binding.dataSecurityTitle.startAnimation(slideInLeft)
        binding.dataSecurityDetails.startAnimation(slideInRight)
        binding.userRightsTitle.startAnimation(slideInLeft)
        binding.userRightsDetails.startAnimation(slideInRight)
        binding.contactUsTitle.startAnimation(slideInLeft)
        binding.contactUsDetails.startAnimation(slideInRight)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
