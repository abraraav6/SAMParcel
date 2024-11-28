package com.example.samparcel.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    @SerializedName("full_name") val fullName: String,
    val email: String,
    val phone: String,
    val address: Address
)