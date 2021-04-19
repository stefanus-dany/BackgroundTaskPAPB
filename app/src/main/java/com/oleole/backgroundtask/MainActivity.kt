package com.oleole.backgroundtask

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.oleole.backgroundtask.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

        private var bgthread : Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.button1.setOnClickListener {
//            // Buat Thread baru setiap kali tombol start progress di klik
//            // Setiap kali thread akan dijalankan, harus dibuat baru,
//            // Thread yang sudah finish/terminated tidak bisa dijalankan kembali
//            // Buat Thread baru setiap kali tombol start progress di klik
//            // Setiap kali thread akan dijalankan, harus dibuat baru,
//            // Thread yang sudah finish/terminated tidak bisa dijalankan kembali
//            if (bgthread == null || bgthread?.state == Thread.State.TERMINATED) {
//                val runnable = Runnable {
//                    try {
//                        for (i in 0..10) {
//                            // Simulating something timeconsuming
//                            Thread.sleep(1000) // in milisecond
//                            binding.progressBar1.post(Runnable {
//                                binding.textView1.text = "Updating $i/10"
//                                binding.progressBar1.progress = i
//                            })
//                        }
//                    } catch (e: InterruptedException) {
//                        e.printStackTrace()
//                    }
//                }
//                bgthread = Thread(runnable)
//                bgthread?.start()
//            }
//        }


        val executor = Executors.newSingleThreadExecutor()
        // OR : val executor = Executors.newFixedThreadPool(4)
        // OR : val executor = Executors.newCachedThreadPool()
        val handler = Handler(Looper.getMainLooper())
        binding.button1.setOnClickListener {
            executor.execute {
                // Simulate process in background thread
                try {
                    for (i in 0..10) {
                        // Simulating something timeconsuming
                        Thread.sleep(1000) // in milisecond
                        binding.progressBar1.post(Runnable {
                            binding.textView1.text = "Updating $i/10"
                            binding.progressBar1.progress = i
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
    }
}