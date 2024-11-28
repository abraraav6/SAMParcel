package com.example.samparcel.app.models

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Address(
    val street: String,
    val city: String,
    val state: String,
    @Embedded(prefix = "postal_") val postalCode: String,
    val country: String
)
