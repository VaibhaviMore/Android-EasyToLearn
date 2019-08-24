package com.example.sam.easytolearn_finalproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sam.easytolearn_finalproject.view.ListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth=FirebaseAuth.getInstance();

        mToolbar=(Toolbar)findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.listHolder);

        if(frag == null)
        {
            frag = new ListFragment();
            fm.beginTransaction().add(R.id.listHolder, frag).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            sendToLogin();
        }

        return true;
    }
    private void sendToLogin(){
        Intent intent=new Intent(DashboardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendToLogin();
        }
    }
}
