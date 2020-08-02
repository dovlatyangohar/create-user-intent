package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class UserPageActivity extends AppCompatActivity {
    private ImageView coverImgDetailed;
    private TextView firstNameDetailed;
    private TextView lastNameDetailed;
    private TextView genderDetailed;
    private TextView emailDetailed;
    private Gson gson;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);


        gson = new Gson();

        Intent receiveIntent = getIntent();
//        User user = receiveIntent.getParcelableExtra("user");
        String userJson = receiveIntent.getStringExtra("userJson");
        User user = gson.fromJson(userJson, User.class);

        coverImgDetailed = findViewById(R.id.coverImgDetailed);
        firstNameDetailed = findViewById(R.id.firstNameTextViewDetailed);
        lastNameDetailed = findViewById(R.id.lastNameTextViewDetailed);
        genderDetailed = findViewById(R.id.genderTextViewDetailed);
        emailDetailed = findViewById(R.id.emailDetailed);

        if (user != null) {


            coverImgDetailed.setImageBitmap(BitmapFactory.decodeFile(user.getImgPath()));
            firstNameDetailed.setText(user.getFirstName());
            lastNameDetailed.setText(user.getLastName());
            genderDetailed.setText(user.getGender());
            emailDetailed.setText(user.getEmail());
        }

    }
}
