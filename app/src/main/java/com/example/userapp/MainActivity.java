package com.example.userapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button createUserButton;
    private List<User> usersList;
    String firstName;
    String lastName;
    String coverImgPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUserButton = findViewById(R.id.createUserButton);
        usersList = new ArrayList<>();

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });

    }

    static final int REQUEST_CODE = 1;

    private void sendIntent() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    firstName = data.getStringExtra("firstName");
                    lastName = data.getStringExtra("lastName");
                     coverImgPath = data.getStringExtra("imgPath");
                    usersList.add(new User(firstName, lastName,coverImgPath));

                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    UserAdapter adapter = new UserAdapter(usersList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(adapter);
                    createUserButton.setText(R.string.create_new_user);

                } else
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
