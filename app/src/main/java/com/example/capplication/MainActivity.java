package com.example.capplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static MainActivity mainActivity;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    TextView textview_title;
    TextView textview_content;
    Button button_start;
    Button button_stop;
    TimerThread timerThread;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview_title = findViewById(R.id.textview_title);
        textview_content = findViewById(R.id.textview_content);
        textview_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        button_start = findViewById(R.id.button_start);
        button_stop = findViewById(R.id.button_stop);

        button_start.setOnClickListener(v -> {
            if (timerThread == null) {
                timerThread = new TimerThread();
                timerThread.start();

                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
        button_stop.setOnClickListener(v -> {
            if (timerThread != null) {
                timerThread.timerFlag = false;
            }
        });
    }

    /**
     * Handler
     */
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    textview_title.setText(String.valueOf(msg.obj));
                    break;
                case 2:
                    textview_content.append(String.valueOf(msg.obj));
                    break;
            }
            return false;
        }
    });

    public Handler getHandler() {
        return handler;
    }

    class TimerThread extends Thread {
        boolean timerFlag = true;

        @Override
        public void run() {
            while (timerFlag) {
                Date date = new Date(System.currentTimeMillis());       // get current time
                Message msg = new Message();
                msg.what = 1;
                msg.obj = sdf.format(date);
                handler.sendMessage(msg);

                // Only the original thread that created a view hierarchy can touch its views.
                // MainActivity.this.updateText(format.format(date));
            }
        }
    }
}