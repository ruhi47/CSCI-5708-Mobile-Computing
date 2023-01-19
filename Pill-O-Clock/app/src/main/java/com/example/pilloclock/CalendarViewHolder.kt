package com.example.pilloclock

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CalendarViewHolder : ViewHolder, View.OnClickListener{
    // Initalize variables
    var dayOfMonth: TextView? = null
    var cellContainer: View? = null
    private var onItemListener : CalendarAdapter.OnItemListener

    // Constructor
    // Sets the date in the Calendar cell to be the correct date, and give cell onclick listener
    constructor(
        itemView: View,
        onItemListener: CalendarAdapter.OnItemListener
    ) : super(itemView) {
        cellContainer = itemView.findViewById(R.id.cellContainer);
        dayOfMonth = itemView.findViewById(R.id.cellDay)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    // Call onItemClick from Calendar Activity
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth?.text.toString())
    }
}