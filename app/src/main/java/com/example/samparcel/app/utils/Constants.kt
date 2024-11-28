package com.samparcel.app.utils

object Constants {
    const val BASE_URL = "https://api.samparcel.com/v1/"
    const val DATABASE_NAME = "samparcel_database"

    object SharedPrefKeys {
        const val USER_TOKEN = "user_token"
        const val IS_LOGGED_IN = "is_logged_in"
    }

    object ApiEndpoints {
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
        const val TRACK_PARCEL = "parcels/track"
        const val CREATE_PARCEL = "parcels/create"
    }
}
