package com.example.pilloclock.data.repo

import androidx.lifecycle.LiveData
import com.example.pilloclock.data.dao.NotificationDao
import com.example.pilloclock.data.entity.Notification

class NotificationRepository (private val notificationDao: NotificationDao) {
    val notifications : LiveData<List<Notification>> = notificationDao.getAll()

    fun addNotification(notification: Notification) = notificationDao.insertAll(notification)
    fun getNotification() = notificationDao.getAll()
    fun emptyTable() = notificationDao.emptyTable()
}