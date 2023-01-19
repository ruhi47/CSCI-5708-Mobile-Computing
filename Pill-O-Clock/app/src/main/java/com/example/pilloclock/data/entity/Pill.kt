package com.example.pilloclock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pill(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val brand: String?,
    val time: String?,
    val days: String?,
    val icon: String?,
    val startDate: String?,
    val endDate: String?,
    val dosage: String?,
    val pillsLeft: Double?,
    val refill: Boolean?,
    val addedDate: String?,
    val description: String?,
    val notes: String?,
    val doctor: String?,
    val isTaken: Boolean?,
)
