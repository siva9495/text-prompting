package com.example.promptshowingtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DisplayTextActivity extends AppCompatActivity {

    private static final double SCROLL_SPEED = 0.1; // Speed in pixels per second
    private static final String ACTION_CONTROL_SCROLL = "com.example.senderapptextprompting.ACTION_CONTROL_SCROLL";

    private Handler handler = new Handler();
    private ScrollView scrollView;
    private boolean isScrolling = false;

    // Broadcast receiver for receiving control commands
    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(ACTION_CONTROL_SCROLL)) {
                String command = intent.getStringExtra("command");
                if (command != null) {
                    switch (command) {
                        case "start":
                            startTextScrolling();
                            break;
                        case "stop":
                            stopTextScrolling();
                            break;
                        default:
                            // Handle unknown commands
                            break;
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);

        TextView textView = findViewById(R.id.textView);
        scrollView = findViewById(R.id.scrollView);

        // Retrieve the filename passed from MainActivity
        String fileName = getIntent().getStringExtra("fileName");

        // Load and display the content of the specified text file from the internal storage directory
        String internalStorageText = loadTxtFromDirectory(fileName);

        // Load and display the content of the specified text file from assets
        String assetsText = loadTxtFromAssets(fileName);

        // Concatenate the text from both sources
        String combinedText = internalStorageText + "\n\n" + assetsText;

        // Set the combined text to the TextView
        textView.setText(combinedText);

        // Register broadcast receiver
        IntentFilter filter = new IntentFilter(ACTION_CONTROL_SCROLL);
        registerReceiver(controlReceiver, filter);

        // Start scrolling the text
        startTextScrolling();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(controlReceiver);
        // Stop scrolling if the activity is destroyed
        stopTextScrolling();
    }

    private void startTextScrolling() {
        if (!isScrolling) {
            handler.postDelayed(scrollRunnable, 1000); // Delay for starting scrolling, 1000 milliseconds = 1 second
            isScrolling = true;
        }
    }

    private void stopTextScrolling() {
        handler.removeCallbacks(scrollRunnable);
        isScrolling = false;
    }

    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            // Calculate the amount of scroll based on the speed and time elapsed
            int scrollAmount = (int) (SCROLL_SPEED * 800 / 60); // 1000 milliseconds in a second, 40 frames per second
            // Scroll the ScrollView
            scrollView.scrollBy(0, scrollAmount);
            // Repeat the scrolling
            handler.postDelayed(this, 16); // 16 milliseconds for 60 frames per second
        }
    };

    private String loadTxtFromDirectory(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File dir = new File(getExternalFilesDir(null), "my_files");
            File file = new File(dir, fileName);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
            } else {
                Log.e("DisplayTextActivity", "File not found in external storage: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DisplayTextActivity", "Error reading file from external storage: " + e.getMessage());
        }
        return stringBuilder.toString();
    }


    private String loadTxtFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
