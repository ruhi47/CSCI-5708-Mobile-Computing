package com.example.pilloclock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.text.DateFormat
import java.util.*

@Entity
data class User(
    @PrimaryKey val id: Int,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
    val pillsTaken: Int?,
    val pillsMissed: Int?,
    val performance: String?,
    val gender: String?,
)
