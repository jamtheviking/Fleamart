package com.csis3175.fleamart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin;
    Button btnSignUp;
    Button btnForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.etUsernameLogin);
        password = findViewById(R.id.etPasswordLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
    }

    public void onClickBtnSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void onClickBtnLogin(View view){
        Users usersDB = new Users(this);

        String un = username.getText().toString();
        String pw = password.getText().toString();

//Modified Content below here by Josh
        if(TextUtils.isEmpty(un))
        {username.setError("Please Enter Username");
        return;
        }
        if(TextUtils.isEmpty(pw))
        {password.setError("Please Enter Password");
        return;
        }
// Modified Content above here by Josh

        User user = usersDB.getUserDetails(un, pw);
        boolean isValidUser = usersDB.isValidUser(un, pw);
        usersDB.close();


        if (isValidUser) {
            showHomePage(user);
        } else {
            //Modified Content below here by Josh
            username.setError("Incorrect Username or Password");
          return;
            // Modified Content above here by Josh
        }
    }

    private void showHomePage(User user) {
        // Example: Pass user details to the home page activity
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}