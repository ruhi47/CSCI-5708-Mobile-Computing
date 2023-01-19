package com.example.pilloclock.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pilloclock.data.entity.Notification

@Dao
interface NotificationDao {
    @Insert
    fun insertAll(vararg notification: Notification)

    @Delete
    fun delete(notification: Notification)

    @Query("SELECT * FROM notification ORDER BY timestamp")
    fun getAll(): LiveData<List<Notification>>

    @Query("DELETE FROM notification")
    fun emptyTable()
}