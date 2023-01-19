package com.example.pilloclock.services

import android.content.Context
import android.util.Log
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.model.Task
import com.example.pilloclock.data.repo.PillRepository
import java.text.SimpleDateFormat
import java.util.*

class TaskService(context: Context) {
    private val pillRepos = PillRepository(AppDatabase.getDatabase(context).pillDao())

    fun getTaskList(date: String): MutableList<Task> {
        val pills = pillRepos.getPills()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        var tasks:MutableList<Task> = mutableListOf()

        pills.forEach {
            // Variables for start, end, mydate, and day of the week of mydate
            val start: Date = sdf.parse(it.startDate)
            val end: Date = sdf.parse(it.endDate)
            val myDate: Date = sdf.parse(date)
            val dayofWeek = SimpleDateFormat("EE", Locale.ENGLISH).format(myDate.time)

            // If the number of pills is > 0 and it is the correct day, add to task list
            if (it.pillsLeft!! > 0 && it.days?.contains(dayofWeek) == true){
                tasks.add(Task(it.id,it.name+" needs to be taken at "+it.time,
                    it.isTaken == true))
            }

            // If the current date passed in is between (inclusive) the start and end date, and is
            // the correct day, add to task list
            else if (((myDate.before(end) && myDate.after(start)) || myDate.equals(end) ||
                        myDate.equals(start)) && it.days?.contains(dayofWeek) == true){
                tasks.add(Task(it.id, it.name+" needs to be taken at "+it.time,
                    it.isTaken == true))
            }

            // If refills are enabled and there is less than three pills left, add refill to task list
            if(it.refill == true && it.pillsLeft < 3){
                tasks.add(Task(it.id, it.name+" needs to be refilled", false))
            }

        }
        return tasks
    }

}