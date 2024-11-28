package com.example.samparcel.app.repositories

import com.example.samparcel.app.models.User
import com.samparcel.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    // Inject Firebase Auth or your preferred auth service
) {
    suspend fun login(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            // Implement actual login logic
            val user = User(
                id = "user123",
                fullName = "John Doe",
                email = email,
                phone = "+1234567890",
                address = " "
            )
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error("Login failed: ${e.localizedMessage}"))
        }
    }

    suspend fun register(user: User, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            // Implement user registration logic
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error("Registration failed: ${e.localizedMessage}"))
        }
    }

    fun logout() {
        // Implement logout logic
    }
}