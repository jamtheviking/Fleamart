package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.User;

public class UpdatePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);
    }

    public void updateUserInfo(View view) {

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");



        EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        EditText editTextLastName = findViewById(R.id.editTextLastName);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        int UserId = user.getId();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.updateUser(UserId,firstName,lastName,email,username,password);


        Toast.makeText(this, "User info updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}