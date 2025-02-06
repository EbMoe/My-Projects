package com.buildbyte.npschamps.ui.donation

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.buildbyte.npschamps.R

class Donation : Fragment() {

    companion object {
        fun newInstance() = Donation()
    }

    private lateinit var viewModel: DonationViewModel
    private lateinit var zapperLayout: LinearLayout
    private lateinit var eftLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_donation, container, false)

        // Load fade-in animation
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        // Apply the fade-in animation to the donation images
        view.findViewById<ImageView>(R.id.qr_code_image).startAnimation(fadeIn)
        view.findViewById<ImageView>(R.id.eft_image).startAnimation(fadeIn)

        zapperLayout = view.findViewById(R.id.zapper_layout)
        eftLayout = view.findViewById(R.id.eft_layout)

        zapperLayout.setOnClickListener{
            openOrDownloadApp("com.zapper.android")
        }

        eftLayout.setOnClickListener{
            val details = "BANK: Nedbank\\nACCOUNT NAME: NPS Champions NPC\\nACCOUNT TYPE: Current Account\\nBRANCH CODE: 198765\\nACCOUNT NUMBER: 1204162700\\nREFERENCE: [Name + Surname]" // Replace with actual details
            copyToClipboard(details)
        }

        return view
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Details copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun openOrDownloadApp(packageName: String) {
        val pm: PackageManager = requireContext().packageManager
        try {
            // Check if the app is installed
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            // If installed, open the app
            val intent = pm.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                startActivity(intent)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // If not installed, try to open the Google Play Store app
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // If Play Store app is not available, open in a web browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                startActivity(intent)
            }
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DonationViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
