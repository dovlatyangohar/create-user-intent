package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private RadioGroup radioGroup;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox photoAddedCheckBox;
    private Button addPhotoButton;
    private Button saveButton;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                user = new User(firstName, lastName, true, email, password);

                returnIntent();
            }
        });

    }

    private void returnIntent() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("firstName", user.getFirstName());
        returnIntent.putExtra("lastName", user.getLastName());
        setResult(LoginActivity.RESULT_OK, returnIntent);
        finish();
    }



}
