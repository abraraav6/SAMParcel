package com.samparcel.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samparcel.app.databinding.FragmentHomeBinding
import com.samparcel.app.ui.adapters.ParcelListAdapter
import com.samparcel.app.viewmodels.ParcelViewModel
import com.samparcel.app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val parcelViewModel: ParcelViewModel by viewModels()
    private lateinit var parcelAdapter: ParcelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeParcels()

        // Fetch user parcels (assume current user's ID)
        parcelViewModel.getUserParcels("current_user_id")
    }

    private fun setupRecyclerView() {
        parcelAdapter = ParcelListAdapter { parcel ->
            // Handle parcel item click
        }

        binding.rvParcels.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = parcelAdapter
        }
    }

    private fun observeParcels() {
        viewLifecycleOwner.lifecycleScope.launch {
            parcelViewModel.userParcelsState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        state.data?.let { parcels ->
                            parcelAdapter.submitList(parcels)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        // Show error message
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