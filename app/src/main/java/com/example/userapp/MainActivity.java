package com.example.userapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private Button createUserButton;
    private List<User> usersList;
    private Gson gson;
    String firstName;
    String lastName;
    String gender;
    String email;
    String coverImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUserButton = findViewById(R.id.createUserButton);
        usersList = new ArrayList<>();

        gson = new Gson();

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
                    User user = data.getParcelableExtra("user");
                    if (user != null) {
                        firstName = user.getFirstName();
                        lastName = user.getLastName();
                        coverImgPath = user.getImgPath();
                        gender = user.getGender();
                        email = user.getEmail();

                        usersList.add(new User(firstName, lastName, gender, email, coverImgPath));

                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        final UserAdapter adapter = new UserAdapter(usersList);

                        adapter.setListener(new UserAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(User user) {
                                openUserDetailsPage(user);
                            }

                            @Override
                            public void onItemLongClickListener(final User user) {
                                openDeleteDialog(user, adapter);
                            }
                        });

                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);
                        createUserButton.setText(R.string.create_new_user);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openUserDetailsPage(User user) {

        String userJson = gson.toJson(user);

        Intent i = new Intent(MainActivity.this, UserPageActivity.class);
        i.putExtra("user", user);
        i.putExtra("userJson", userJson);
        startActivity(i);
    }

    private void openDeleteDialog(final User user, final UserAdapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setSelected(true);
                usersList.remove(user);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Delete user");
        dialog.setMessage("Are you sure you want to delete this user?");
        dialog.show();

    }
}
