package com.csis3175.fleamart.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.User;

public class LandingPage extends AppCompatActivity {
    private Scene scene1, scene2;
    private Transition slideUpTransition,slideDownTransition;
    Button btnForgotPassword,btnRegister;
    EditText username,password,firstName,
            lastName,confirmPassword,email;
    boolean isRootShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        transitionConfig();
        isRootShowing = true;

    }

    public void transitionConfig() {
        listenerConfig();
        ViewGroup viewRoot = findViewById(android.R.id.content);
        scene1 = Scene.getSceneForLayout(viewRoot, R.layout.login, this);
        scene2 = Scene.getSceneForLayout(viewRoot, R.layout.sign_up, this);
        slideUpTransition = new Slide(Gravity.BOTTOM);
        slideDownTransition = new Slide(Gravity.TOP);
        slideUpTransition.setDuration(500);


        // Force listenerConfig() after transition to fix delayed response
        scene1.setEnterAction(new Runnable() {
            @Override
            public void run() {
                // Listener will be invoked when scene1 transition completes
                listenerConfig();
            }
        });
        scene2.setEnterAction(new Runnable() {
            @Override
            public void run() {
                registerUser();
            }
        });
    }

    //Load listeners for buttons
    public void listenerConfig(){
        onClickSignup();
        onClickLogin();
    }

    public void onClickSignup() {
        Button btnSignup = findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene2, slideUpTransition);
                isRootShowing = false;
            }
        });
    }
    public void registerUser(){

        firstName = findViewById(R.id.etFirstNameRegister);
        lastName = findViewById(R.id.etLastNameRegister);
        username = findViewById(R.id.etUsernameRegister);
        email = findViewById(R.id.etEmailRegister);
        password = findViewById(R.id.etPasswordRegister);
        confirmPassword = findViewById(R.id.etConfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
            // Set up click listener for registration button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = firstName.getText().toString();
                String ln = lastName.getText().toString();
                String un = username.getText().toString();
                String em = email.getText().toString();
                String pw = password.getText().toString();
                addUser(fn, ln, un, em, pw);
                TransitionManager.go(scene1, slideDownTransition);
            }
        });
    }
    private void addUser(String firstName, String lastName, String username, String email, String password){
        ///Needs Try Catch
        DatabaseHelper user = new DatabaseHelper(this);
        user.insertUser(firstName, lastName, username, email, password);
    }

    public void onClickLogin() {
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRootShowing) {
                    // If already in the login scene, handle login action
                    try {
                        validateCredential();
                    } catch (Exception e) {
                        Log.d("Error", "onClick: " + e);
                    }
                    return;
                }
                // Transition to the login scene
                TransitionManager.go(scene1, slideUpTransition);
                isRootShowing = false;
            }
        });
    }

    public void showHomePage(User user){
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();

    }
    public void validateCredential(){
        EditText username, password;

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        DatabaseHelper databaseHelperDB = new DatabaseHelper(this);

        String un = username.getText().toString();
        String pw = password.getText().toString();

        User user = databaseHelperDB.getUserDetails(un,pw);
        boolean isValidUser = databaseHelperDB.isValidUser(un, pw);
        databaseHelperDB.close();

        if (isValidUser) {
            showHomePage(user);
        } else {
            // Invalid credentials
            // Display an error message
            Toast.makeText(this, "Not valid", Toast.LENGTH_SHORT).show();
        }
    }
}