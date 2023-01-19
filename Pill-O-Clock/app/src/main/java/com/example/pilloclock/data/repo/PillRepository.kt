package com.example.pilloclock.data.repo

import com.example.pilloclock.data.dao.PillDao
import com.example.pilloclock.data.entity.Pill

class PillRepository(private val pillDao: PillDao) {
    fun addPill(pill: Pill) = pillDao.insertAll(pill)

    fun updatePill(pill: Pill) = pillDao.update(pill)

    fun updatePillTakenAndCount(pillId: Int, isTaken: Boolean):Int = pillDao.updateIsTaken(pillId, isTaken)

    fun getPills():List<Pill> = pillDao.getAll()
}