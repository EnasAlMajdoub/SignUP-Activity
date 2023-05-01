package com.example.signup_activity

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService3 : FirebaseMessagingService() {
    override fun onMessageReceived(@NonNull message: RemoteMessage) {
        super.onMessageReceived(message)
        getFirebaseMessage(message.notification!!.title, message.notification!!.body)
    }

    @SuppressLint("MissingPermission")
    private fun getFirebaseMessage(title: String?, msg: String?) {
        val builder =
            NotificationCompat.Builder(this, "myFirebaseNotification") // .setSmallIcon(R.drawable.)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
        val manger = NotificationManagerCompat.from(this)
        manger.notify(101, builder.build())
    }
}