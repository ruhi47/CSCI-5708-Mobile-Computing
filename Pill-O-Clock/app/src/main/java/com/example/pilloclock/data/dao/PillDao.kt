package com.example.pilloclock.data.dao

import androidx.room.*
import com.example.pilloclock.data.entity.Pill

@Dao
interface PillDao {
    @Insert
    @OnConflictStrategy
    fun insertAll(vararg pill: Pill)

    @Update
    fun update(pill: Pill): Int

    @Query("UPDATE pill SET isTaken = :isTaken, pillsLeft = pillsLeft - 1 WHERE id = :pillId")
    fun updateIsTaken(pillId: Int, isTaken: Boolean): Int

    @Delete
    fun delete(pill: Pill)

    @Query("SELECT * FROM pill")
    fun getAll(): List<Pill>
}