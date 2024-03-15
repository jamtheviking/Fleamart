package com.csis3175.fleamart;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;



public class Landing extends AppCompatActivity {

    private Scene scene1, scene2;
    private Transition slideUpTransition;
    Button btnLogin,btnSignup,btnForgotPassword,btnRegister;
    EditText username,password,firstName,
            lastName,confirmPassword,email;
    ViewGroup viewRoot;
    boolean isRootShowing;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        TransitionConfig();
        isRootShowing = true;


        //root defined as android.R.id.content (topmost view of this activity)

    }

    public void TransitionConfig(){
        viewRoot = findViewById(android.R.id.content);
//        sceneRoot = Scene.getSceneForLayout(viewRoot, R.layout.activity_landing, this);
        scene1 = Scene.getSceneForLayout(viewRoot,R.layout.login,this);
        scene2 = Scene.getSceneForLayout(viewRoot,R.layout.sign_up,this);
        slideUpTransition = new Slide(Gravity.BOTTOM);
        slideUpTransition.setDuration(500);

    }

    public void onClickSignup(View v) {
        btnSignup = findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.go(scene2, slideUpTransition);
                isRootShowing = false;

            }
        });
    }

    public void registerUser(View v){

        firstName = findViewById(R.id.etFirstNameRegister);
        lastName = findViewById(R.id.etLastNameRegister);
        username = findViewById(R.id.etUsernameRegister);
        email = findViewById(R.id.etEmailRegister);
        password = findViewById(R.id.etPasswordRegister);
        confirmPassword = findViewById(R.id.etConfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);

        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        String em = email.getText().toString();
        String pw = password.getText().toString();

        addUser(fn, ln, un, em, pw);
        TransitionManager.go(scene1, slideUpTransition);

    }

    private void addUser(String firstName, String lastName, String username, String email, String password){
        Users user = new Users(this);
        user.insertUser(firstName, lastName, username, email, password);
    }



    public void onClickLogin(View v) {
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRootShowing) {
                    TransitionManager.go(scene1, slideUpTransition);
                    isRootShowing = false;

                } else {
                    //collect and validate credential
                    try {
                        validateCredential();

                    }
                    catch (Exception e){
                        Log.d("Error", "onClick: "+ e);
                    }

                }
            }
        });
    }

    public void showHomePage(User user){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();

    }

    public void validateCredential(){
        EditText username, password;

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        Users usersDB = new Users(this);

        String un = username.getText().toString();
        String pw = password.getText().toString();

        User user = usersDB.getUserDetails(un,pw);
        boolean isValidUser = usersDB.isValidUser(un, pw);
        usersDB.close();

        if (isValidUser) {
            showHomePage(user);
        } else {
            // Invalid credentials
            // Display an error message
            Toast.makeText(this, "Not valid", Toast.LENGTH_SHORT).show();
        }
    }




}