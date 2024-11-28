package com.samparcel.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samparcel.app.databinding.FragmentProfileBinding
import com.example.samparcel.app.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserProfile()
        setupUpdateButton()
    }

    private fun observeUserProfile() {
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            binding.apply {
                editTextName.setText(user.name)
                editTextEmail.setText(user.email)
                editTextPhone.setText(user.phoneNumber)
            }
        }
    }

    private fun setupUpdateButton() {
        binding.buttonUpdateProfile.setOnClickListener {
            val updatedName = binding.editTextName.text.toString()
            val updatedEmail = binding.editTextEmail.text.toString()
            val updatedPhone = binding.editTextPhone.text.toString()

            userViewModel.updateProfile(updatedName, updatedEmail, updatedPhone)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}