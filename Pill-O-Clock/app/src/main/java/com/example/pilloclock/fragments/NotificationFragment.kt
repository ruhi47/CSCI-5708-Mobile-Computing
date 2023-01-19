package com.example.pilloclock.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilloclock.R
import com.example.pilloclock.adapter.MyNotificationRecyclerViewAdapter
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.repo.NotificationRepository
import kotlinx.android.synthetic.main.fragment_item_list.*


/**
 * A fragment representing a list of Items.
 */
class NotificationFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(activity)
        val adapter = MyNotificationRecyclerViewAdapter()
        list.adapter = adapter
        //[Fix] To fix RecyclerView TextView disappear issue
        list.recycledViewPool.setMaxRecycledViews(0, 0)
        val notificationDao = AppDatabase.getDatabase(requireContext()).notificationDao()
        val notificationRepo = NotificationRepository(notificationDao)

        notificationRepo.notifications.observe(viewLifecycleOwner) { notification ->
            notification?.let {
                adapter.setNotifications(
                    it
                )
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                NotificationFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}