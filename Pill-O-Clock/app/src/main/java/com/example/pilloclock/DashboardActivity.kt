package com.example.pilloclock

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pilloclock.adapter.TaskListViewAdapter
import com.example.pilloclock.fragments.NotificationFragment
import com.example.pilloclock.services.TaskService
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize Variables
        var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        var topDashboard = findViewById<MaterialToolbar>(R.id.topAppBarDashboard)

        // Bottom Navigation Bar
        // Set Selected
        bottomNavigation.setSelectedItemId(R.id.bottom_nav_item_home)

        toolbarDashboard.findViewById<View>(R.id.notificationIcon).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, NotificationFragment.newInstance(1))
                .commitNow()
        }
        toolbarDashboard.findViewById<View>(R.id.profileIcon).setOnClickListener {
            val intent = Intent(this, EditUserProfile::class.java)
            startActivity(intent)
        }

        // Perform Navigation to different activities
        bottomNavigation.setOnItemSelectedListener{
            when (it.itemId){
                R.id.bottom_nav_item_home -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent);
                }

                R.id.bottom_nav_item_calendar -> {
                    val intent = Intent(this, CalendarActivity::class.java)
                    startActivity(intent);
                }

                R.id.bottom_nav_item_medications -> {
                    val intent = Intent(this, MedicationList::class.java)
                    startActivity(intent);
                }
                R.id.bottom_nav_item_more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent);
                }
            }
            true
        }



        val taskService = TaskService(this.applicationContext)
        val today = LocalDate.now()
        val todayFormatted = today.format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()

        val listView = findViewById<ListView>(R.id.taskView)
        val myListAdapter = TaskListViewAdapter(this,taskService.getTaskList(todayFormatted))
        listView.adapter = myListAdapter
    }
    private fun navigateToActitity(java: Type) {
        TODO("Implement navigation")
    }

}