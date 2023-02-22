package com.example.background.services

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.background.BuildConfig
import com.example.background.R
import kotlinx.android.synthetic.main.activity_fore_ground_service.*


class ForeGroundServiceActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fore_ground_service)
        btn_start.setOnClickListener {
            val startIntent = Intent(this, MyForegroundService::class.java)
            startIntent.putExtra("inputExtra", "Sample Service Example")
            startForegroundService(startIntent)
            updateTextStatus()
        }
        btn_stop.setOnClickListener {
            val intentStop = Intent(this, MyForegroundService::class.java)
           /* intentStop.action = ACTION_STOP_FOREGROUND
            startService(intentStop)
            Handler().postDelayed({
                updateTextStatus()
            },100)*/
            stopService(intentStop)
        }

        updateTextStatus()
    }

    private fun updateTextStatus() {
        if(isMyServiceRunning(MyForegroundService::class.java)){
           txt_service_status.text = "Service is Running"
        }else{
           txt_service_status.text = "Service is NOT Running"
        }
    }


    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    companion object{
        const val  ACTION_STOP_FOREGROUND = "${BuildConfig.APPLICATION_ID}.stopforeground"
    }}