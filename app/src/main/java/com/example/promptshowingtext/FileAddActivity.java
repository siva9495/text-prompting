package com.example.promptshowingtext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
                File dir = new File(getExternalFilesDir(null), "my_files");
                if (!dir.exists()) {
                    dir.mkdirs(); // Create the directory if it doesn't exist
                }
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
                Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();

                // Pass the file name back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newFileName", fileName);
                setResult(RESULT_OK, resultIntent);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in both title and description", Toast.LENGTH_SHORT).show();
        }
    }

}
