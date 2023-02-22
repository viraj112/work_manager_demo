package com.example.background.services

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.background.BlurActivity
import com.example.background.R
import com.example.background.services.ForeGroundServiceActivity

class MyForegroundService : Service() {
    val CHANNEL_ID = "ForegroundService Kotlin"
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(service: Intent?, flags: Int, startId: Int): Int {
        val input: String? = service?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, BoundServiceActivity::class.java)
        val pendingIntent: PendingIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_MUTABLE);
        }
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sample Foreground Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID, "Foreground Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager: NotificationManager? = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}