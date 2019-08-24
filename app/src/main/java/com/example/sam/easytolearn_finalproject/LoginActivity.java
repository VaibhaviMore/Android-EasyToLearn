package com.example.sam.easytolearn_finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private Button btnRegister;
    private EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.eUserName);
        etPassword = findViewById(R.id.ePassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnJoin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                     String current_user_id = mAuth.getCurrentUser().getUid();

                     Intent mainIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                     startActivity(mainIntent);
                     finish();


                }
                else {
                    String task_result = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "Error : " + task_result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Successful sign-in! Welcome, " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
