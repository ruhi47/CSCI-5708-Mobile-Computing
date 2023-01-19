package com.example.pilloclock.adapter

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pilloclock.R
import com.example.pilloclock.constants.StringConstants.REFILL_TIME
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.Pill
import com.example.pilloclock.data.model.Task
import com.example.pilloclock.data.repo.PillRepository
import com.example.pilloclock.services.NotificationService
import java.util.*
import java.util.stream.Collector

class TaskListViewAdapter(private val context: AppCompatActivity,
                          private val tasks: MutableList<Task>,
                          )
    : ArrayAdapter<Task>(context, R.layout.task_list, tasks) {
    private val pillRepos = PillRepository(AppDatabase.getDatabase(context).pillDao())
    private val notificationService = NotificationService()

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.task_list, null, true)

        val taskCheckBox = rowView.findViewById(R.id.taskCheckBox) as CheckBox
        taskCheckBox.text = tasks[position].taskName
        taskCheckBox.isChecked = tasks[position].isCompleted
        taskCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            pillRepos.updatePillTakenAndCount(tasks[position].pillId, b)
            var pill: Pill = pillRepos.getPills().filter { it.id == tasks[position].pillId }[0]
            if(pill.refill == true && pill.pillsLeft!! < 4){
                notificationService.sendNotification(context,REFILL_TIME,"Time to refill "+pill.name,context,0)
            }
        }
        return rowView
    }

}