package com.example.signup_activity

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(@NonNull remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        getFirebaseMessage(
            remoteMessage.notification!!.title, remoteMessage.notification!!
                .body
        )
    }

    @SuppressLint("MissingPermission")
    fun getFirebaseMessage(title: String?, msg: String?) {
        val builder = NotificationCompat.Builder(
            this,
            "myFirebaseChannel"
        ) //  .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
        val manager = NotificationManagerCompat.from(this)
        manager.notify(101, builder.build())
    }
}