package com.example.lab9

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.lab9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonGet.setOnClickListener {
            /*Intent(this, MyService::class.java).also{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    startForegroundService(it)
                else
                    startService(it)
            }*/
            myService?.doService("Hello")
        }
    }
    private var myService: MyService? = null

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyService.LocalBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
        }

    }

    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)    //Bound 서비스 bind, 결과가 serviveConnection으로 전달
        }
    }

    override fun onStop() {
        super.onStop()
        Intent(this, MyService::class.java).also {
            unbindService(serviceConnection)
        }
    }
}