package com.example.samparcel.app.repositories

import com.example.samparcel.app.models.User
import com.samparcel.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    // Inject Room DAO or Network Service
) {
    suspend fun updateUserProfile(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            // Implement user profile update logic
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error("Profile update failed: ${e.localizedMessage}"))
        }
    }

    suspend fun getUserProfile(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            // Implement logic to fetch user profile
            // This is a mock implementation
            val mockUser = User(
                id = userId,
                fullName = "John Doe",
                email = "john.doe@example.com",
                phone = "+1234567890",
                address = null
            )
            emit(Resource.Success(mockUser))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch profile: ${e.localizedMessage}"))
        }
    }
}