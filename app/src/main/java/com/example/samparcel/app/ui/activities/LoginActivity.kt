package com.samparcel.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.samparcel.app.viewmodels.AuthViewModel
import com.samparcel.app.databinding.ActivityLoginBinding
import com.samparcel.app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeLoginState()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            authViewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            // Navigate to registration activity
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            authViewModel.loginState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        binding.btnLogin.isEnabled = false
                        // Show loading indicator
                    }
                    is Resource.Success -> {
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    }
                    is Resource.Error -> {
                        binding.btnLogin.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
}