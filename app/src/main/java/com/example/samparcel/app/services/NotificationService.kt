package com.samparcel.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.samparcel.app.models.Parcel
import com.samparcel.app.R

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    companion object {
        private const val CHANNEL_ID = "SamParcel_Notifications"
        private const val CHANNEL_NAME = "SamParcel Tracking"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendParcelUpdateNotification(parcel: Parcel) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Parcel.kt Update")
            .setContentText("Tracking #${parcel.trackingNumber}: ${parcel.status}")
            .setSmallIcon(R.drawable.ic_parcel_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(parcel.trackingNumber.hashCode(), notification)
    }
}
