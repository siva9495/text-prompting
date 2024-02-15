package com.example.promptshowingtext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FileAddActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_add);
    }
}