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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private EditText fullname, email, address, password;
    private TextView register, already_account;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.username_input);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.pass);
        register = (TextView) findViewById(R.id.res_btn);
        already_account = (TextView) findViewById(R.id.already_acc);

        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myemail = email.getText().toString().trim();
                String mypassword = password.getText().toString().trim();
                String myfullName = fullname.getText().toString().trim();
                String myaddress = address.getText().toString().trim();

                if(myfullName.isEmpty()){
                    fullname.setError("Full name is required");
                    fullname.requestFocus();
                    return;
                }

                if(myaddress.isEmpty()){
                    address.setError("Section is required");
                    address.requestFocus();
                    return;
                }

                if(myemail.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()){
                    email.setError("Please provide valid email!");
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

               mAuth.createUserWithEmailAndPassword(myemail, mypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           User user = new User(myfullName, myaddress, myaddress);

                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(register.this, MainActivity.class));
                                   }
                                   else{
                                       Toast.makeText(register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                   }
                               }
                           });
                       }
                       else{
                           Toast.makeText(register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                       }
                   }
               });
            }
        });

    }





}
