package com.csis3175.fleamart.features;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.csis3175.fleamart.model.Encrypt;
import com.csis3175.fleamart.model.User;

public class LandingPage extends AppCompatActivity {
    private Scene scene1, scene2;
    private Transition slideUpTransition,slideDownTransition;
    Button btnLogin,btnRegister;

    EditText username,password,firstName,
            lastName,confirmPassword,email;
    boolean isRootShowing=true;
    private SharedPreferences sharedPreferences;
    private static final String PREF_USERID_KEY = "userId";
    DatabaseHelper databaseHelperDB = new DatabaseHelper(LandingPage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        btnLogin = findViewById(R.id.btnLogin);

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
        DatabaseHelper check = new DatabaseHelper(this);
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


                String fn = firstName.getText().toString().trim();
                String ln = lastName.getText().toString().trim();
                String un = username.getText().toString().trim();
                String em = email.getText().toString().trim();
                String pw = password.getText().toString().trim();
                String cpw = confirmPassword.getText().toString().trim();
                String hashePw = null;

                // Check if any field is empty
                if(fn.isEmpty() || ln.isEmpty() || un.isEmpty() || em.isEmpty() || pw.isEmpty() || cpw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if passwords match
                if(!pw.equals(cpw)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                } else{
                    hashePw = Encrypt.hashPassword(cpw);
                }

                // Check if username already exists
                if(check.doesUsernameExist(un)) {
                    Toast.makeText(getApplicationContext(), "Username already exists. Please choose another.", Toast.LENGTH_LONG).show();
                } else {
                    // Proceed with user registration since username is unique
                    addUser(fn, ln, un, em, hashePw);
                    // Assuming `TransitionManager.go(scene1, slideDownTransition);` is a valid transition you've set up
                    TransitionManager.go(scene1, slideDownTransition);
                }


            }
        });
    }
    private void addUser(String firstName, String lastName, String username, String email, String hashedPassword){
        ///Needs Try Catch
        DatabaseHelper user = new DatabaseHelper(this);
        user.insertUser(firstName, lastName, username, email, hashedPassword);
    }

    public void onClickLogin() {
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transition to the login scene
                if (!isRootShowing){
                    validateCredential();

                }
                else {
                    TransitionManager.go(scene1, slideUpTransition);
                    isRootShowing = false;
                    scene1.setEnterAction(new Runnable() {
                        @Override
                        public void run() {
                            validateCredential();
                        }
                    });
                }

            }
        });
    }

    public void showHomePage(){
        startActivity(new Intent(LandingPage.this, HomePage.class));
        finish();

    }
    public void validateCredential(){
        EditText username, password;
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);


        if (username == null || password == null) {

            return;
        }

        String un = username.getText().toString();
        String pw = password.getText().toString();

        if(un.isEmpty() || pw.isEmpty()){
            Toast.makeText(this, "Must enter a username and password", Toast.LENGTH_SHORT).show();
        }
        else {
            String hash = databaseHelperDB.getHashedPassword(un);
            if(hash == null || hash.isEmpty()){
                Toast.makeText(this, "Not valid", Toast.LENGTH_SHORT).show();
            }
            else {
                boolean isValid = Encrypt.validatePassword(pw, hash);
                if (isValid){
                    int userId = databaseHelperDB.getUserId(un, hash);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(PREF_USERID_KEY, userId);
                    editor.apply();
                    showHomePage();
                }
            }
        }
    }
}