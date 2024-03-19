package com.csis3175.fleamart.features;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

public class SignUpPage extends AppCompatActivity {

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

        //TODO: Implement an escape for the user

    }

    public void btnRegisterOnClick(View view){

        //Need Validation for fields

        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        String em = email.getText().toString();
        String pw = password.getText().toString();


        addUser(fn, ln, un, em, pw);

        finish();

    }

    private void addUser(String firstName, String lastName, String username, String email, String password){

        DatabaseHelper user = new DatabaseHelper(this);

        user.insertUser(firstName, lastName, username, email, password);


    }

}