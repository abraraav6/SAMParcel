package com.samparcel.app.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.samparcel.app.viewmodels.ParcelViewModel
import com.samparcel.app.databinding.ActivityParcelTrackingBinding
import com.samparcel.app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParcelTrackingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParcelTrackingBinding
    private val parcelViewModel: ParcelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcelTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trackingNumber = intent.getStringExtra("TRACKING_NUMBER") ?: return

        setupTrackingObserver(trackingNumber)

        binding.btnTrack.setOnClickListener {
            parcelViewModel.trackParcel(trackingNumber)
        }
    }

    private fun setupTrackingObserver(trackingNumber: String) {
        lifecycleScope.launch {
            parcelViewModel.parcelTrackingState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        // Show loading indicator
                    }
                    is Resource.Success -> {
                        state.data?.let { parcel ->
                            binding.tvTrackingNumber.text = parcel.trackingNumber
                            binding.tvStatus.text = parcel.status.toString()
                            binding.tvEstimatedDelivery.text = parcel.estimatedDelivery.toString()
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(this@ParcelTrackingActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }
            }
        }
    }
}