package com.example.recipeappcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    private EditText username_input;
    private TextView res_btn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        username_input = (EditText) findViewById(R.id.username_input);
        res_btn = (TextView) findViewById(R.id.forgot_btn);

        auth = FirebaseAuth.getInstance();

        res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

            private void resetPassword() {
                String email = username_input.getText().toString().trim();

                if(email.isEmpty()){
                    username_input.setError("Email is Required!");
                    username_input.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username_input.setError("Please provide valid email!");
                    username_input.requestFocus();
                    return;
                }



                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgot_password.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(forgot_password.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(forgot_password.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}