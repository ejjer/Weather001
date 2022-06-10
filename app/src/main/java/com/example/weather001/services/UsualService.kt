package com.example.weather001.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.*

class UsualService:Service(),CoroutineScope by MainScope() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        launch{
            delay(4000)
            Toast.makeText(this@UsualService,"Usual Service started",Toast.LENGTH_SHORT).show()
        }
        return START_NOT_STICKY

    }
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    companion object {
        fun start(context: Context) {
            val usualServiceIntent = Intent(context, UsualService::class.java)
            context.startService(usualServiceIntent)
        }

        fun stop(context: Context) {
            val usualServiceIntent = Intent(context, UsualService::class.java)
            context.stopService(usualServiceIntent)
        }
    }

}