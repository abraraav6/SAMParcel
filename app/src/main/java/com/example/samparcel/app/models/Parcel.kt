package com.example.samparcel.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

@Entity(tableName = "parcels")
data class Parcel(
    @PrimaryKey val trackingNumber: String,
    val sender: User,
    val recipient: User,
    val weight: Double,
    val dimensions: String,
    val status: ParcelStatus,
    @SerializedName("created_at") val createdAt: LocalDateTime,
    @SerializedName("estimated_delivery") val estimatedDelivery: LocalDateTime
)

enum class ParcelStatus {
    PENDING, IN_TRANSIT, DELIVERED, RETURNED
}