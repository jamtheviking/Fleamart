package com.csis3175.fleamart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SignUp extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText password;
    EditText confirmPassword;
    EditText email;
    EditText username;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        firstName = findViewById(R.id.etFirstNameRegister);
        lastName = findViewById(R.id.etLastNameRegister);
        username = findViewById(R.id.etUsernameRegister);
        email = findViewById(R.id.etEmailRegister);
        password = findViewById(R.id.etPasswordRegister);
        confirmPassword = findViewById(R.id.etConfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);

        //Modified Content below here by Josh
     //   progressBar = findViewById(R.id.progressBar);

        //Modified Content above here by Josh
    }

    public void btnRegisterOnClick(View view){

        //Need Validation for fields

        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        String em = email.getText().toString();
        String pw = password.getText().toString();
        String cp = confirmPassword.getText().toString();

       // progressBar.setVisibility(View.VISIBLE);
//Modified Content below here by Josh

        // validation for empty input

        if(TextUtils.isEmpty(un))
        {username.setError("Please Enter Username");
            return;
        }

        if(TextUtils.isEmpty(fn))
        {firstName.setError("Please Enter FirstName");
            return;
        }
        if(TextUtils.isEmpty(ln))
        {lastName.setError("Please Enter LastName");
            return;
        }


        if(TextUtils.isEmpty(em))
        {email.setError("Please Enter Username");
            return;
        }
        if(TextUtils.isEmpty(pw))
        {password.setError("Please Enter Username");
            return;
        }


        if (!isValidEmail(em)) {
            email.setError("Invalid email format");
            return;
        }
        if(!isValidPassword(pw)) {
            password.setError("Please Include at least 1 uppercase and 1 digit to your password");
            return;
        }
        if(!cp.matches(pw)) {
            confirmPassword.setError("Confirm password does not match password, please try again");
            return;
        }
//Modified Content above here by Josh

        addUser(fn, ln, un, em, pw);

        finish();

    }

    private void addUser(String firstName, String lastName, String username, String email, String password){

        Users user = new Users(this);

        user.insertUser(firstName, lastName, username, email, password);


    }
    //Modified Content below here by Josh
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+$";
        return password.matches(passwordRegex);
    }
//Modified Content above here by Josh
}