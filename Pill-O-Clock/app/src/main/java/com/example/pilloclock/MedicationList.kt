package com.example.pilloclock

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.Pill
import com.example.pilloclock.data.repo.PillRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_medication_list.*

class MedicationList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_list)

        // Initialize Variables
        var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val pillDao = AppDatabase.getDatabase(this.application).pillDao()
        val pillList = pillDao.getAll()

        val listView = findViewById<ListView>(R.id.medicationView)

        val resources = resources
        listView.adapter = MedicationListAdapter(this, pillList, resources, packageName)

        listView.setOnItemClickListener {parent, view, position, id ->
            val intent = Intent(this, ViewDetails::class.java)
            intent.putExtra("Position", position)
            startActivity(intent)
        }

        toolbarMedication.findViewById<View>(R.id.addIcon).setOnClickListener {
            val intent = Intent(this, AddMedication::class.java)
            startActivity(intent)
        }
        // Bottom Navigation Bar
        // Set Selected
        bottomNavigation.setSelectedItemId(R.id.bottom_nav_item_medications)

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
                    startActivity(intent)
                }
            }
            true
        }
    }

    // Custom adapter to display ListView items
    private class MedicationListAdapter(context: Context, pillList: List<Pill>, resources: Resources, packageName: String): BaseAdapter() {
        private val mContext: Context = context
        private val mPillList: List<Pill> = pillList
        private val mResources: Resources = resources
        private val mPackageName: String = packageName

        override fun getCount(): Int {
            return mPillList.size
        }

        override fun getItem(p0: Int): Any {
           return ""
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            //Populate list view with items from the Pill list

            val layoutInflator = LayoutInflater.from(mContext)
            val med_list = layoutInflator.inflate(R.layout.medication_list_item, p2, false)
            val currItem = mPillList.get(p0)

            val nameTextView = med_list.findViewById<TextView>(R.id.medName)
            nameTextView.text = currItem.name

            val brandTextView = med_list.findViewById<TextView>(R.id.medBrandText)
            brandTextView.text = currItem.brand

            val dosageTextView = med_list.findViewById<TextView>(R.id.dosageText)
            dosageTextView.text = "${currItem.dosage} mg"

            val uri = """drawable/${currItem.icon}"""
            val imageResource = mResources.getIdentifier(uri, null, mPackageName)
            val res = mResources.getDrawable(imageResource, null)
            val iconImageView = med_list.findViewById<ImageView>(R.id.iconImage)
            iconImageView.setImageDrawable(res)
            return med_list
        }

    }
}