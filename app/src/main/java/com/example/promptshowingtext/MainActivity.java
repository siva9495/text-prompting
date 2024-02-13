package com.example.promptshowingtext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promptshowingtext.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> txtFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Populate the list of .txt files
        try {
            txtFiles = new ArrayList<>(Arrays.asList(getAssets().list("")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> txtFileNames = new ArrayList<>();
        for (String file : txtFiles) {
            if (file.endsWith(".txt")) {
                txtFileNames.add(file);
            }
        }

        // Display the list of .txt files in a ListView
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, txtFileNames);
        listView.setAdapter(adapter);

        // Set a click listener for the ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start DisplayTextActivity to display the content of the selected .txt file
                Intent intent = new Intent(MainActivity.this, DisplayTextActivity.class);
                intent.putExtra("fileName", txtFiles.get(position));
                startActivity(intent);
            }
        });
    }
}
