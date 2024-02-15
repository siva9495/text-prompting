package com.example.promptshowingtext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAddActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_add);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnSave = findViewById(R.id.btnSaveFile);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
    }

    private void saveFile(){
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if(!title.isEmpty() && !description.isEmpty()) {
            String fileName = title + ".txt";
            String content = description;


            try {
                File file = new File(getFilesDir(), fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
                Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();

                //logging the where file was saving
                String filePath = file.getAbsolutePath();
                Log.d("FileAddActivity", "File saved at: " +filePath);

                // Notify MainActivity to update the list
//                Intent intent = new Intent();
//                intent.setAction("com.example.promptshowingtext.FILE_SAVED");
//                sendBroadcast(intent);

                finish();
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(this, "Failed to save file", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please fill in both title and description", Toast.LENGTH_SHORT).show();
        }
    }

}