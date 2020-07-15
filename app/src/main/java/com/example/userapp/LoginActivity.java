package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private RadioButton genderButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox photoAddedCheckBox;
    private Button addPhotoButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveIntent();

            }
        });

    }

    private void receiveIntent(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("firstName",firstNameEditText.getText().toString());
        returnIntent.putExtra("lastName",lastNameEditText.getText().toString());
        setResult(LoginActivity.RESULT_OK,returnIntent);
        finish();
    }
}
