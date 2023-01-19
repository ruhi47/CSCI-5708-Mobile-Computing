package com.example.pilloclock

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter : RecyclerView.Adapter<CalendarViewHolder> {
    // Initialize variables
    var monthDays: ArrayList<String> = ArrayList(28)
    var onItemListener: OnItemListener

    // Constructor
    constructor(monthDays: ArrayList<String>, onItemListener: CalendarActivity) {
        this.monthDays = monthDays
        this.onItemListener = onItemListener
    }

    // Inflate the calendar cell for each day
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        var inflater:LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = inflater.inflate(R.layout.calendar_cell, parent, false)
        var layoutParams:ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.2).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    // Set text of cell
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth?.setText(monthDays.get(position))
        var dayMatch = holder.dayOfMonth?.text?.toString().equals(CalendarActivity.cursor.dayOfMonth.toString())
        var monthMatch = CalendarActivity.selectedDate?.month?.equals(CalendarActivity.cursor.month)
        if(dayMatch == true && monthMatch == true){
            holder.cellContainer?.setBackgroundColor(Color.LTGRAY)
        }
    }

    // Return number of days in month
    override fun getItemCount(): Int {
        return monthDays.size
    }

    // On item click listener
    interface OnItemListener {
        fun onItemClick(position: Int, day: String)
    }
}