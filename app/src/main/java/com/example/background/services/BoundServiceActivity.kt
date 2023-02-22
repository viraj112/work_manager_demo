package com.example.background.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.background.R
import com.example.background.services.BoundService.MyBinder
import kotlinx.android.synthetic.main.activity_bound_service.*


class BoundServiceActivity : AppCompatActivity() {
    var mBoundService: BoundService? = null
    var mServiceBound = false
    var mServiceConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_service)

        mServiceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName) {
                mServiceBound = false
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val myBinder = service as MyBinder
                mBoundService =myBinder.getSerVice()
                mServiceBound = true
            }
        }
        print_timestamp.setOnClickListener {
            if (mServiceBound) {
                timestamp_text.text =(mBoundService?.getTimestamp());
            }
        }
        stop_service.setOnClickListener {
            if (mServiceBound) {
                unbindService(mServiceConnection as ServiceConnection)
                mServiceBound = false
            }
            val intent = Intent(
                this@BoundServiceActivity,
                BoundService::class.java
            )
            stopService(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        startService(intent)
        mServiceConnection?.let { bindService(intent, it, Context.BIND_AUTO_CREATE) }
    }

    override fun onStop() {
        super.onStop()
        if (mServiceBound) {
            unbindService(mServiceConnection!!)
            mServiceBound = false
        }
    }

}