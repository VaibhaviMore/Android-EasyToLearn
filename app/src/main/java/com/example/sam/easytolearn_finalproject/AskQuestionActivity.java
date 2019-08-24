package com.example.sam.easytolearn_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam.easytolearn_finalproject.model.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AskQuestionActivity extends AppCompatActivity {

    EditText etAskQues;
    Button btnAskQues;
    private DatabaseReference databaseQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        databaseQuestions = FirebaseDatabase.getInstance().getReference("question");

        etAskQues=(EditText)findViewById(R.id.etAskQuestion);
        btnAskQues=(Button)findViewById(R.id.btnAskQues);
        btnAskQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
                Intent i = new Intent(AskQuestionActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });


    }
    private void addQuestion(){
        String ques=etAskQues.getText().toString();
        String noOfAnswers = "0";
        if(!TextUtils.isEmpty(ques)) {
           String questionId= databaseQuestions.push().getKey();
            Question question = new Question(questionId,ques, noOfAnswers);
            databaseQuestions.child(questionId).setValue(question);
            Toast.makeText(this,"Question Added",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"You should enter your question",Toast.LENGTH_LONG).show();
        }
}}
