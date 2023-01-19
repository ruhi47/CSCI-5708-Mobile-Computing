package com.example.pilloclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.Pill

class ViewDetails : AppCompatActivity() {
    var pillPos: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_details)

        // Get the position of the Pill in the Pill List
        val position = intent.getSerializableExtra("Position") as Int
        pillPos = position

        // Fetch all pills from the database
        val pillDao = AppDatabase.getDatabase(this.application).pillDao()
        val pillList = pillDao.getAll()

        // Init views
        val nameText = findViewById<TextView>(R.id.nameText)
        val brandText = findViewById<TextView>(R.id.brandText)
        val dosageText = findViewById<TextView>(R.id.dosageText)
        val startDateText = findViewById<TextView>(R.id.startDateText)
        val endDateText = findViewById<TextView>(R.id.endDateText)
        val refillText = findViewById<TextView>(R.id.refillText)
        val intakeText = findViewById<TextView>(R.id.intakeText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)
        val notesText = findViewById<TextView>(R.id.notesText)
        val icon = findViewById<ImageView>(R.id.iconImg)

        // Get the pill that the user clicked on and fill in the details
        val currPill = pillList[pillPos!!]
        nameText.text = currPill.name
        brandText.text = currPill.brand
        dosageText.text = currPill.dosage
        startDateText.text = currPill.startDate
        if (currPill.endDate != "") {
            endDateText.text = currPill.endDate
        }
        else {
            endDateText.text = currPill.pillsLeft.toString()
        }
        refillText.text = currPill.refill.toString()
        val intake = """${currPill.days} at ${currPill.time}"""
        intakeText.text = intake
        if(currPill.description != "") {
            val description = currPill.description!!.split("Purpose ")
            descriptionText.text = description[0]
        }
        else {
            descriptionText.text = "No description"
        }
        if(currPill.notes != "") {
            notesText.text = currPill.notes
        }
        else {
            notesText.text = "No notes"
        }
        notesText.text = currPill.notes
        // set the Pill icon
        val uri = """drawable/${currPill.icon}"""
        val imageResource = resources.getIdentifier(uri, null, packageName)
        val res = resources.getDrawable(imageResource, null)
        icon.setImageDrawable(res)
    }

    fun clickEditButton(view: View) {
        val intent = Intent(this, EditDetails::class.java)
        intent.putExtra("Position", pillPos)
        startActivity(intent)
    }
}