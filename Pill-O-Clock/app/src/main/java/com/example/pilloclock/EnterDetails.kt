package com.example.pilloclock

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class EnterDetails : AppCompatActivity() {
    var iconImage: ImageView? = null
    var nameEdit: EditText? = null
    var brandEdit: EditText? = null
    var dosageEdit: EditText? = null
    var startDateButton: Button? = null
    var endDateButton: Button? = null
    var refillCheckbox: CheckBox? = null
    var monRadioButton: RadioButton? = null
    var tueRadioButton: RadioButton? = null
    var wedRadioButton: RadioButton? = null
    var thuRadioButton: RadioButton? = null
    var friRadioButton: RadioButton? = null
    var satRadioButton: RadioButton? = null
    var sunRadioButton: RadioButton? = null
    var timeButton: Button? = null
    var notesEdit: EditText? = null
    var pillCountEdit: EditText? = null
    var spinner: Spinner? = null

    var startDateSet: Boolean? = false
    var endDateSet: Boolean? = false
    var timeSet: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_details)

        // Get the pill object from the previous page if the user clicks Edit
        val pillModel = intent.getSerializableExtra("Pill") as PillModel?

        // init views
        iconImage = findViewById(R.id.iconImg)
        nameEdit = findViewById(R.id.medicationNameEdit)
        brandEdit = findViewById(R.id.brandNameEdit)
        dosageEdit = findViewById(R.id.dosageEdit)
        startDateButton = findViewById(R.id.startDateButton)
        endDateButton = findViewById(R.id.endDateButton)
        refillCheckbox = findViewById(R.id.refillCheckBox)
        monRadioButton = findViewById(R.id.monRadioButton)
        tueRadioButton = findViewById(R.id.tueRadioButton)
        wedRadioButton = findViewById(R.id.wedRadioButton)
        thuRadioButton = findViewById(R.id.thuRadioButton)
        friRadioButton = findViewById(R.id.friRadioButton)
        satRadioButton = findViewById(R.id.satRadioButton)
        sunRadioButton = findViewById(R.id.sunRadioButton)
        timeButton = findViewById(R.id.timeButton)
        notesEdit = findViewById(R.id.notesEdit)
        pillCountEdit = findViewById(R.id.pillCountEdit)
        spinner = findViewById(R.id.spinner)

        // Set the icon names as the adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Icons))
        spinner!!.adapter = adapter

        if(pillModel != null) {
            restoreValues(pillModel)
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickStartDatePicker(view: View) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val text = """$dayOfMonth-${monthOfYear + 1}-$year"""
                startDateButton!!.text = text
                startDateSet = true
            }, year, month, day)
            dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickEndDatePicker(view: View) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val text = """$dayOfMonth-${monthOfYear + 1}-$year"""
                endDateButton!!.text = text
                endDateSet = true
            }, year, month, day)
            dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickTimePicker(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = {view, h, m ->
            val text = "$h:$m"
            timeButton!!.text = text
            timeSet = true
        })
            , hour, minute, false)

        dialog.show()
    }

    fun clickNextButton(view: View) {
        val days = getDays()
        // Check for empty input fields
        if(nameEdit!!.text.isEmpty() || brandEdit!!.text.isEmpty() || dosageEdit!!.text.isEmpty() || !startDateSet!! || (!endDateSet!! && pillCountEdit!!.text.isEmpty()) || !timeSet!! || days == "") {
            val toast = Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG)
            toast.show()
        }
        else {
            // No input errors, so create the Serializable Pill model to send to the Verify page
            val pillModel = PillModel()
            pillModel.name = nameEdit!!.text.toString()
            pillModel.brand = brandEdit!!.text.toString()
            pillModel.dosage = dosageEdit!!.text.toString()
            pillModel.startDate = startDateButton!!.text.toString()
            if(endDateSet!!) {
                pillModel.endDate = endDateButton!!.text.toString()
            }
            else {
                pillModel.pillsLeft = pillCountEdit!!.text.toString().toDouble()
            }
            if(refillCheckbox!!.isChecked) {
                pillModel.refill = true
            }
            pillModel.time = timeButton!!.text.toString()
            pillModel.days = days
            pillModel.notes = notesEdit!!.text.toString()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            pillModel.addedDate = dateFormat.format(Date())
            pillModel.icon = spinner!!.selectedItem.toString()

            val intent = Intent(this, VerifyDetails::class.java)
            intent.putExtra("Pill", pillModel)
            startActivity(intent)
        }
    }

    // Get the days the user has selected for the pill intake
    private fun getDays(): String {
        var days = ""
        val daysRadioButtons = arrayOf(monRadioButton, tueRadioButton, wedRadioButton, thuRadioButton, friRadioButton, satRadioButton, sunRadioButton)
        for(d in daysRadioButtons) {
            if(d!!.isChecked) {
                days += """${d.text} """
            }
        }
        return days
    }

    // Fill in input fields with current values of the pill
    private fun restoreValues(pillModel: PillModel) {
        nameEdit!!.setText(pillModel.name)
        brandEdit!!.setText(pillModel.brand)
        dosageEdit!!.setText(pillModel.dosage)
        startDateButton!!.text = pillModel.startDate
        if(pillModel.endDate != "") {
            endDateButton!!.text = pillModel.endDate
        }
        if(pillModel.pillsLeft != 0.0) {
            pillCountEdit!!.setText(pillModel.pillsLeft.toString())
        }
        refillCheckbox!!.isChecked =  pillModel.refill
        val intakeRadioButtons = arrayOf(monRadioButton, tueRadioButton, wedRadioButton, thuRadioButton, friRadioButton, satRadioButton, sunRadioButton)
        for(x in intakeRadioButtons) {
            if(x!!.text in pillModel.days) {
                x.isChecked = true
            }
        }
        timeButton!!.text = pillModel.time
        notesEdit!!.setText(pillModel.notes)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Icons))
        spinner!!.adapter = adapter
        spinner!!.setSelection(adapter.getPosition(pillModel.icon))
    }

    fun clickCancelButton(view: View) {
        onBackPressed()
    }
}