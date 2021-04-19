package com.oleole.backgroundtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.oleole.backgroundtask.databinding.ActivityMainBinding
import com.oleole.backgroundtask.databinding.RandomNumberBinding
import java.util.concurrent.Executors

class RandomNumber : AppCompatActivity() {
    private lateinit var binding: RandomNumberBinding
    private var bgthread: Thread? = null
    private var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RandomNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        binding.btnStart.setOnClickListener {
            check = false
            executor.execute {
                // Simulate process in background thread
                try {
                    while (!check) {
                        // Simulating something timeconsuming
                        Thread.sleep(500) // in milisecond
                        val angka = (0..9).random()
                        binding.tv.post(Runnable {
                            binding.tv.text = angka.toString()
                        })
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post {
                    bgthread = Thread()
                    bgthread?.start()
                }
            }
        }

        binding.btnStop.setOnClickListener {
            check = true
        }
    }
}