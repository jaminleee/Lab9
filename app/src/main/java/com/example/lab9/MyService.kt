package com.example.lab9

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    private val binder = LocalBinder()
    var tickCount:Int = 0

    inner class LocalBinder : Binder() {
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun doService(param: String){
        println("do service $param")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartcommand")
        //startForeground(1,)
        //getExtra로 init 값을 받아오면 된다'
        tickCount= intent?.getIntExtra("init",0)?: 0
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            for(i in 1..10){
                delay(1000)
                println("#")
                //startForeground(1,)
            }
        }
        scope.launch {
            for(i in 1..10){
                delay(1000)
                println("$")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}