package com.example.pilloclock

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.example.FDAMedicationResponse
import com.example.pilloclock.receiver.CHANNEL_ID
import com.example.pilloclock.services.FDAMedicationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LandingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        createNotificationChannel()

        val btnLogInClick = findViewById<Button>(R.id.login_button)
        btnLogInClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // start LoginActivity
            startActivity(intent)
        }
        val btnSignUpClick = findViewById<Button>(R.id.sign_up_button)
        btnSignUpClick.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // start SignUpActivity
            startActivity(intent)
        }
        val btnSkip = findViewById<Button>(R.id.skip_button)
        btnSkip.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            // skips to dashboard
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



