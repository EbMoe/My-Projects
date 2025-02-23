package com.alpha.aviatorview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.alpha.aviatorview.databinding.FragmentFlightDetailsBinding

class FlightDetailsBottomSheet(private val flight: FlightData) : BottomSheetDialogFragment() {

    private var _binding: FragmentFlightDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Update UI with flight details using View Binding
        binding.callsignText.text = flight.callsign
        binding.departureText.text = flight.origin ?: "Unknown"
        binding.destinationText.text = flight.destination ?: "Unknown"
        binding.altitudeText.text = "${flight.altitude} ft"
        binding.speedText.text = "${flight.speed} kts"
        binding.aircraftText.text = flight.aircraft ?: "N/A"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
