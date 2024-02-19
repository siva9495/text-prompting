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
import java.util.ArrayList;
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
                startActivityForResult(intent, 1);
            }
        });

        // Initialize list for files
        fileList = new ArrayList<>();

        // Populate fileList with existing files
        populateFileList();

        // Display the combined list of .txt files in a ListView
        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = fileList.get(position);

                // Start DisplayTextActivity to display the content of the selected .txt file
                Intent intent = new Intent(MainActivity.this, DisplayTextActivity.class);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
            }
        });
    }

    // Method to populate fileList with existing files
    private void populateFileList() {
        File dir = new File(getExternalFilesDir(null), "my_files");
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        fileList.add(file.getName());
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve the new file name from FileAddActivity
            String newFileName = data.getStringExtra("newFileName");
            if (newFileName != null) {
                // Add the new file name to the list and notify the adapter
                fileList.add(newFileName);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
