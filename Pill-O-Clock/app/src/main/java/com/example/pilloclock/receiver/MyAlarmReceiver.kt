package com.example.pilloclock.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pilloclock.R

const val REQUEST_CODE = 12345
const val notificatioID = 12
const val notificationTitle = "notificationTitle"
const val notificationMessage = "notificationMessage"
const val CHANNEL_ID = "channel1"

class MyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent?.getStringExtra(notificationTitle))
            .setContentText(intent?.getStringExtra(notificationMessage))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificatioID, builder)
    }
}