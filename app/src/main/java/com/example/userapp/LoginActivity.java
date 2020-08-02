package com.example.userapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private TextView genderTextView;
    private RadioGroup radioGender;
    private RadioButton femaleRadioButton;
    private RadioButton maleRadioButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox photoAddedCheckBox;
    private Button addPhotoButton;
    private Button saveButton;
    private String currentPath;
    private String returnCurrentPath;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);

        genderTextView = findViewById(R.id.genderTextView);
        radioGender = findViewById(R.id.radioGroup);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        maleRadioButton = findViewById(R.id.maleRadioButton);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        saveButton = findViewById(R.id.saveButton);
        addPhotoButton = findViewById(R.id.addPhotoButton);
        photoAddedCheckBox = findViewById(R.id.checkbox);


        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        saveButton.setEnabled(false);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enableSaveButton();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        firstNameEditText.addTextChangedListener(watcher);
        lastNameEditText.addTextChangedListener(watcher);

        genderTextView.addTextChangedListener(watcher);
        femaleRadioButton.addTextChangedListener(watcher);
        maleRadioButton.addTextChangedListener(watcher);

        emailEditText.addTextChangedListener(watcher);
        passwordEditText.addTextChangedListener(watcher);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoAddedCheckBox.isChecked() && isValid()) {
                    saveButton.setEnabled(true);
                }
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                int childCount = radioGender.getChildCount();
                String gender = null;
                for (int i = 0; i < childCount; i++) {
                    RadioButton button = (RadioButton) radioGender.getChildAt(i);
                    if (button.getId() == R.id.femaleRadioButton) {
                        button.setText("Female");
                    } else {
                        button.setText("Male");
                    }
                    if (button.isChecked()) {
                        gender = button.getText().toString();
                    }
                }

                user = new User(firstName, lastName, gender, email, returnCurrentPath);

                returnIntent(user);
            }
        });


    }

    private void returnIntent(User u) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", u);
        setResult(LoginActivity.RESULT_OK, returnIntent);
        finish();
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.example.userapp", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoAddedCheckBox.setChecked(true);
            enableSaveButton();
            returnCurrentPath = currentPath;
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPath = image.getAbsolutePath();

        return image;

    }

    private boolean isEmpty(EditText text) {
        String str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isEmail(EditText text) {
        String email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValid() {
        if (isEmpty(firstNameEditText)) {
            firstNameEditText.setError("Username is required");
            return false;
        }
        if (!isEmail(emailEditText)) {
            emailEditText.setError("Email isn't valid");
            return false;
        }
        if (!(femaleRadioButton.isChecked() || maleRadioButton.isChecked())) {
            genderTextView.setError("Select gender");
            return false;
        }
        if (isEmpty(passwordEditText)) {
            passwordEditText.setError("Password is required");
            return false;
        }

        return true;
    }

    private void enableSaveButton() {
        if (photoAddedCheckBox.isChecked() && isValid()) {
            saveButton.setEnabled(true);
        } else saveButton.setEnabled(false);
    }

}

