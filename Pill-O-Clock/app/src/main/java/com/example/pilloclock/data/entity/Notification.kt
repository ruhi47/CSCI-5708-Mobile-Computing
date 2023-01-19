package com.example.pilloclock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pilloclock.constants.NotificationStatus
import java.sql.Timestamp

@Entity
data class Notification(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var text: String?,
    var status: NotificationStatus?,
    var timestamp: String?,
) {
}
