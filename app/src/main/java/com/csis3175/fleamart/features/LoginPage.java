package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.User;

public class LoginPage extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin;
    Button btnSignUp;
    Button btnForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);

//        username = findViewById(R.id.etUsernameLogin);
//        password = findViewById(R.id.etPasswordLogin);
//        btnSignUp = findViewById(R.id.btnSignUp);
//        btnLogin = findViewById(R.id.btnLogin);
//        btnForgotPassword = findViewById(R.id.btnForgotPassword1);
    }

//    public void onClickBtnSignUp(View view) {
//        Intent intent = new Intent(this, SignUp.class);
//        startActivity(intent);
//    }

    public void onClickBtnLogin(View view){
        DatabaseHelper databaseHelperDB = new DatabaseHelper(this);

        String un = username.getText().toString();
        String pw = password.getText().toString();

        User user = databaseHelperDB.getUserDetails(un, pw);
        boolean isValidUser = databaseHelperDB.isValidUser(un, pw);
        databaseHelperDB.close();

        if (isValidUser) {
            showHomePage(user);
        } else {
            // Invalid credentials
            // Display an error message
        }
    }

    private void showHomePage(User user) {
        // Example: Pass user details to the home page activity
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}