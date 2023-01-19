package com.example.pilloclock.services

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.pilloclock.R
import com.example.pilloclock.constants.NotificationStatus
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.Notification
import com.example.pilloclock.data.repo.NotificationRepository
import com.example.pilloclock.receiver.*
import java.sql.Timestamp
import java.time.Instant

class NotificationService {
    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification(applicationContext: Context,
                             notificationTitleStr: String,
                             notificationMessageStr: String,
                             appCompatActivity: AppCompatActivity,
                             intervalMillis: Long) {
        val intent = Intent(applicationContext, MyAlarmReceiver::class.java)
        intent.putExtra(notificationTitle,notificationTitleStr)
        intent.putExtra(notificationMessage,notificationMessageStr)

        val pIntent = PendingIntent.getBroadcast(
            applicationContext, notificatioID,
            intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarm = appCompatActivity.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        alarm.setRepeating(
            AlarmManager.RTC_WAKEUP, intervalMillis,AlarmManager.INTERVAL_DAY,pIntent
        )
        addNotification(appCompatActivity, notificationMessageStr)
    }

    fun sendNotification(applicationContext: AppCompatActivity, notificationTitleStr: String, notificationMessageStr: String, appCompatActivity: AppCompatActivity, i: Int) {
        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitleStr)
            .setContentText(notificationMessageStr)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        val manager = appCompatActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificatioID, builder)
        addNotification(applicationContext, notificationMessageStr)
    }

    private fun addNotification(applicationContext: AppCompatActivity, text: String){
        val notificationRepos = NotificationRepository(AppDatabase.getDatabase(applicationContext).notificationDao())
        var notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification(0,text,NotificationStatus.UNREAD,Timestamp.from(Instant.now()).toString())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationRepos.addNotification(notification)
    }
}