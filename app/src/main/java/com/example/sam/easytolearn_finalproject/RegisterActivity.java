package com.example.sam.easytolearn_finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam.easytolearn_finalproject.DashboardActivity;
import com.example.sam.easytolearn_finalproject.model.Question;
import com.example.sam.easytolearn_finalproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private FirebaseAuth mAuth;
    private EditText etUsername,etPassword,etEmail;
    private DatabaseReference dAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
       mAuth = FirebaseAuth.getInstance();
        dAddUser = FirebaseDatabase.getInstance().getReference("user");

       etEmail=findViewById(R.id.etEmail);
       etUsername=findViewById(R.id.etUserName);
       etPassword=findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(userName,email,password);


            }
        });
    }

    private void registerUser(final String userName, final String email, String password) {

        //create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Done", Toast.LENGTH_SHORT).show();
                            addUser(userName,email);
                            //change if necessayr
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void addUser(String username,String email){
            String userId= dAddUser.push().getKey();
        User user =new User(userId,username,email);

            dAddUser.child(userId).setValue(user);
     }
}
