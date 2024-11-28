package com.samparcel.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.samparcel.app.databinding.FragmentParcelBookingBinding
import com.example.samparcel.app.models.Parcel
import com.example.samparcel.app.models.User
import com.example.samparcel.app.viewmodels.ParcelViewModel
import com.example.samparcel.app.viewmodels.UserViewModel
import com.samparcel.app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class ParcelBookingFragment : Fragment() {
    private var _binding: FragmentParcelBookingBinding? = null
    private val binding get() = _binding!!

    private val parcelViewModel: ParcelViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParcelBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBookingListeners()
        observeParcelCreation()
    }

    private fun setupBookingListeners() {
        binding.btnBookParcel.setOnClickListener {
            val weight = binding.etWeight.text.toString().toDoubleOrNull()
            val dimensions = binding.etDimensions.text.toString()

            if (weight != null && dimensions.isNotBlank()) {
                createParcel(weight, dimensions)
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createParcel(weight: Double, dimensions: String) {
        // This is a mock implementation - in real-world, you'd fetch current user and recipient
        val currentUser = User(
            id = "current_user_id",
            fullName = "Sender Name",
            email = "sender@example.com",
            phone = "+1234567890",
            address = null
        )

        val recipientUser = User(
            id = "recipient_user_id",
            fullName = "Recipient Name",
            email = "recipient@example.com",
            phone = "+0987654321",
            address = null
        )

        val parcel = Parcel(
            trackingNumber = "TRACK-${System.currentTimeMillis()}",
            sender = currentUser,
            recipient = recipientUser,
            weight = weight,
            dimensions = dimensions,
            status = ParcelStatus.PENDING,
            createdAt = LocalDateTime.now(),
            estimatedDelivery = LocalDateTime.now().plusDays(3)
        )

        parcelViewModel.createParcel(parcel)
    }

    private fun observeParcelCreation() {
        viewLifecycleOwner.lifecycleScope.launch {
            parcelViewModel.parcelCreationState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        binding.btnBookParcel.isEnabled = false
                    }
                    is Resource.Success -> {
                        binding.btnBookParcel.isEnabled = true
                        Toast.makeText(context, "Parcel booked successfully", Toast.LENGTH_SHORT).show()
                        // Clear form or navigate
                    }
                    is Resource.Error -> {
                        binding.btnBookParcel.isEnabled = true
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}