package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.Encrypt;
import com.csis3175.fleamart.model.User;

public class UpdatePage extends AppCompatActivity {
    EditText editTextFirstName,editTextLastName,editTextUsername,editTextEmail,editTextPassword ;
    TextView usernameText;
    int userId;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);
        DatabaseHelper db = new DatabaseHelper(UpdatePage.this);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        usernameText = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);


        if (userId>0){
            String[] userDetails = db.getUserDetails(userId);
            editTextFirstName.setText(userDetails[0]);
            editTextLastName.setText(userDetails[1]);
            editTextEmail.setText(userDetails[2]);
            editTextUsername.setText(userDetails[3]);
        }

    }



    public void updateUserInfo(View view) {

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();

        String password = Encrypt.hashPassword(editTextPassword.getText().toString());
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.updateUser(userId,firstName,lastName,email,password);
        Toast.makeText(this, "User info updated successfully", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(UpdatePage.this,HomePage.class));
    }
}