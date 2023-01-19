package com.example.pilloclock.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.pilloclock.R
import com.example.pilloclock.constants.NotificationStatus
import com.example.pilloclock.data.entity.Notification
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [Notification].
 */
class MyNotificationRecyclerViewAdapter(
    private val VIEW_TYPE_ITEM: Int = 0,
    private val VIEW_TYPE_LOADING: Int = 1)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var values: List<Notification> = emptyList<Notification>()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            populateItemRows((holder as ItemViewHolder?)!!, position)
        } else if (holder is LoadingViewHolder) {
            showLoadingView((holder as LoadingViewHolder?)!!, position)
        }
    }

    override fun getItemCount(): Int = values.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNotifications(notification: List<Notification>) {
        this.values = notification
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (values.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notificationTitle: TextView
        var notificationDuration: TextView
        var notificationCheckBox: CheckBox

        init {
            notificationTitle = itemView.findViewById(R.id.notificationTitleTextView)
            notificationDuration = itemView.findViewById(R.id.notificationDurationTextView)
            notificationCheckBox = itemView.findViewById(R.id.notificationCheckBox)
        }
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }


    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val p = PrettyTime()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val item: Notification = values[position]
        when (item.status) {
            NotificationStatus.CHECKBOX_READ -> {
                viewHolder.notificationCheckBox.text = item.text
                viewHolder.notificationTitle.visibility = View.INVISIBLE
                cal.time = sdf.parse(item.timestamp)
                viewHolder.notificationDuration.text = p.format(cal)
            }
            NotificationStatus.CHECKBOX_UNREAD -> {
                viewHolder.notificationCheckBox.text = item.text
                viewHolder.notificationCheckBox.setTypeface(null, Typeface.BOLD)
                viewHolder.notificationTitle.visibility = View.INVISIBLE
                cal.time = sdf.parse(item.timestamp)
                viewHolder.notificationDuration.text = p.format(cal)
            }
            NotificationStatus.READ -> {
                viewHolder.notificationTitle.text = item.text
                viewHolder.notificationCheckBox.visibility = View.INVISIBLE
                cal.time = sdf.parse(item.timestamp)
                viewHolder.notificationDuration.text = p.format(cal)
            }
            NotificationStatus.UNREAD -> {
                val spanString = SpannableString(item.text)
                spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
                viewHolder.notificationTitle.text = spanString
                viewHolder.notificationCheckBox.visibility = View.INVISIBLE
                cal.time = sdf.parse(item.timestamp)
                viewHolder.notificationDuration.text = p.format(cal)
            }
        }
    }
}