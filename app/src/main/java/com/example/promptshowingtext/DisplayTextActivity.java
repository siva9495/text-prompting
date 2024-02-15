package com.example.promptshowingtext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DisplayTextActivity extends AppCompatActivity {


    private static final double SCROLL_SPEED = 0.1; // Speed in pixels per second

    private Handler handler = new Handler();

    private ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);

        TextView textView = findViewById(R.id.textView);
        scrollView = findViewById(R.id.scrollView);

        // Retrieve the filename passed from MainActivity
        String fileName = getIntent().getStringExtra("fileName");

        // Load and display the content of the specified text file
        textView.setText(loadTxtFileContent(fileName));

        // Start scrolling the text
        startTextScrolling();

    }

    private void startTextScrolling(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Calculate the amount of scroll based on the speed and time elapsed
                int scrollAmount = (int) (SCROLL_SPEED * 800 / 60); // 1000 milliseconds in a second, 40 frames per second

                // Scroll the ScrollView
                scrollView.scrollBy(0, scrollAmount);

                // Repeat the scrolling
                handler.postDelayed(this, 16); // 16 milliseconds for 60 frames per second
            }
        }, 1000); //Delay for starting scrolling, 1000 milliseconds = 1 second
    }



    private String loadTxtFileContent(String fileName) {
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
