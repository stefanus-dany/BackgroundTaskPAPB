package com.oleole.backgroundtask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BTJava extends Activity implements View.OnClickListener {
    private ProgressBar progress;
    private TextView text;
    private Button btn;
    private Thread bgthread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        text = (TextView) findViewById(R.id.textView1);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Buat Thread baru setiap kali tombol start progress di klik
        // Setiap kali thread akan dijalankan, harus dibuat baru,
        // Thread yang sudah finish/terminated tidak bisa dijalankan kembali
        if (bgthread == null || bgthread.getState() == Thread.State.TERMINATED) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i <= 10; i++) {
                            final int value = i;
                            // Simulating something timeconsuming
                            Thread.sleep(1000); // in milisecond
                            progress.post(new Runnable() {
                                @Override
                                public void run() {
                                    text.setText("Updating " + value + "/10");
                                    progress.setProgress(value);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            bgthread = new Thread(runnable);
            bgthread.start();
        }
    }
}
