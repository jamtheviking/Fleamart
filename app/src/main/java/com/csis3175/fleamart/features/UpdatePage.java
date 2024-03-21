package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.User;

public class UpdatePage extends AppCompatActivity {

    // Define EditText fields as class members so they are accessible in all methods
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // pre-filling the form with user information
        if (user != null) {
            editTextFirstName.setText(user.getFirstName());
            editTextLastName.setText(user.getLastName());
            editTextEmail.setText(user.getEmail());
            // we are not pre-filling the password and username for security reasons
        }
    }

    public void updateUserInfo(View view) {
        // validating user input before updating information
        if (validateInput()) {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String email = editTextEmail.getText().toString();
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            int userId = user.getId();

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateUser(userId, firstName, lastName, email, username, password);

            Toast.makeText(this, "User info updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        if (editTextFirstName.getText().toString().trim().isEmpty() ||
                editTextLastName.getText().toString().trim().isEmpty() ||
                editTextEmail.getText().toString().trim().isEmpty() ||
                editTextUsername.getText().toString().trim().isEmpty() ||
                editTextPassword.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}