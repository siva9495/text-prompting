package com.example.promptshowingtext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddFile = findViewById(R.id.BtnAddFile);

        btnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileAddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize list for files
        fileList = new ArrayList<>();

        // Get list of .txt files from assets
        try {
            String[] assetFiles = getAssets().list("");
            if (assetFiles != null) {
                for (String file : assetFiles) {
                    if (file.endsWith(".txt")) {
                        fileList.add(file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get list of .txt files from directory
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    fileList.add(file.getName());
                }
            }
        }

        // Display the combined list of .txt files in a ListView
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = fileList.get(position);

                // Check if the file is from assets or directory
                if (fileName.endsWith(".txt")) {
                    // Start DisplayTextActivity to display the content of the selected .txt file
                    Intent intent = new Intent(MainActivity.this, DisplayTextActivity.class);
                    intent.putExtra("fileName", fileName);
                    startActivity(intent);
                }
            }
        });
    }
}
