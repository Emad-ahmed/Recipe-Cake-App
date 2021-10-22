package com.example.recipeappcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password;
    private TextView login, register_new, forgotPasswordID;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        email = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.pass);
        forgotPasswordID = (TextView) findViewById(R.id.forgotPasswordID);
        forgotPasswordID.setOnClickListener(this);
        login = (TextView) findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        register_new = (TextView) findViewById(R.id.register_btn);
        register_new.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                startActivity(new Intent(this, register.class));
                break;

            case R.id.login_btn:
                userLogin();
                break;

            case R.id.forgotPasswordID:
                startActivity(new Intent(this, forgot_password.class));
                break;
        }
    }

    private void userLogin() {
        String myemail = email.getText().toString().trim();
        String mypassword = password.getText().toString().trim();

        if(myemail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if(mypassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(mypassword.length() < 6){
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(myemail, mypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, Home.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to Login! PLease Check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // logged in Profile Activity
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && user.isEmailVerified()) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
    }

}