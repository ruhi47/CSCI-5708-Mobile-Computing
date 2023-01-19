package com.example.pilloclock

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.example.FDAMedicationResponse
import com.example.pilloclock.data.AppDatabase
import com.example.pilloclock.data.entity.Pill
import com.example.pilloclock.data.repo.PillRepository
import com.example.pilloclock.services.FDAMedicationService
import com.example.pilloclock.services.NotificationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class VerifyDetails : AppCompatActivity() {
    var pillModel: PillModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_details)
        // Get the Pill object from EnterDetails page
        pillModel = intent.getSerializableExtra("Pill") as PillModel?

        // Init views
        val nameText = findViewById<TextView>(R.id.nameText)
        val brandText = findViewById<TextView>(R.id.brandText)
        val dosageText = findViewById<TextView>(R.id.dosageText)
        val startDateText = findViewById<TextView>(R.id.startDateText)
        val endDateText = findViewById<TextView>(R.id.endDateText)
        val refillText = findViewById<TextView>(R.id.refillText)
        val intakeText = findViewById<TextView>(R.id.intakeText)
        val icon = findViewById<ImageView>(R.id.iconImg)

        // Set the icon
        val uri = """drawable/${pillModel!!.icon}"""
        val imageResource = resources.getIdentifier(uri, null, packageName)
        val res = resources.getDrawable(imageResource, null)
        icon.setImageDrawable(res)

        // Fill in details
        nameText.text = pillModel!!.name
        brandText.text = pillModel!!.brand
        dosageText.text = pillModel!!.dosage
        startDateText.text = pillModel!!.startDate
        if (pillModel!!.endDate != "") {
            endDateText.text = pillModel!!.endDate
        }
        else {
            endDateText.text = pillModel!!.pillsLeft.toString()
        }
        refillText.text = pillModel!!.refill.toString()
        val intake = """${pillModel!!.days} at ${pillModel!!.time}"""
        intakeText.text = intake
    }

    fun clickEdit(view: View) {
        val intent = Intent(this, EnterDetails::class.java)
        intent.putExtra("Pill", pillModel)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun clickSubmit(view: View) {
        val loadingBar = findViewById<ProgressBar>(R.id.loadingBar)
        loadingBar.visibility = ProgressBar.VISIBLE
        getPurpose { purpose ->
            if (purpose != null) {
                pillModel!!.description = purpose
            } else {
                System.err.println("null")
            }
            val pillDao = AppDatabase.getDatabase(this.application).pillDao()
            val size = pillDao.getAll().size
            val pillEntity = Pill(size+1, pillModel!!.name, pillModel!!.brand, pillModel!!.time, pillModel!!.days, pillModel!!.icon, pillModel!!.startDate, pillModel!!.endDate, pillModel!!.dosage, pillModel!!.pillsLeft, pillModel!!.refill, pillModel!!.addedDate, pillModel!!.description, pillModel!!.notes, pillModel!!.doctor, false)
            val pillRepository = PillRepository(pillDao)
            pillRepository.addPill(pillEntity)
            loadingBar.visibility = ProgressBar.GONE

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = pillModel!!.time.split(":")[0].toInt()
            calendar[Calendar.MINUTE] = pillModel!!.time.split(":")[1].toInt()
            calendar[Calendar.SECOND] = 0
            val notificationService = NotificationService()
            notificationService.scheduleNotification(this.applicationContext,
            "Medication Time",
            "Time to take "+pillModel!!.name,
            this,
                calendar.timeInMillis)

            val intent = Intent(this, MedicationList::class.java)
            startActivity(intent)
        }
    }

    fun getPurpose(callback: (String?) -> Unit) {
        // returns the purpose of the pill from the FDA API
        val BASE_URL = "https://api.fda.gov/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FDAMedicationService::class.java)
        val call = service.getMedicationDetails("5", pillModel!!.name)

        call.enqueue(object : Callback<FDAMedicationResponse> {
            override fun onResponse(call: Call<FDAMedicationResponse>, response: Response<FDAMedicationResponse>) {
                if (response.code() == 200) {
                    val fdaMedicationResponse = response.body()!!
                    val purpose = fdaMedicationResponse.results[0].purpose
                    var purposeText = ""
                    for(x in purpose) {
                        if(x != "Purpose") {
                            purposeText += x
                        }
                    }
                    callback(purposeText)
                }
            }

            override fun onFailure(call: Call<FDAMedicationResponse>, t: Throwable) {
                //TODO failure
                callback(null)
            }
        })
    }
}