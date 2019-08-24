package com.example.sam.easytolearn_finalproject.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.easytolearn_finalproject.R;
import com.example.sam.easytolearn_finalproject.model.Answer;
import com.example.sam.easytolearn_finalproject.model.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AnswersActivity extends AppCompatActivity {
    Button btnAns;
    String questionId="",questionString,quesNoOfAns;
    String answer;
    TextView tvQuesDisplay;
    final Context context=this;
    private DatabaseReference dbAns,dbQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        dbAns = FirebaseDatabase.getInstance().getReference("answer");
        dbQues=FirebaseDatabase.getInstance().getReference("question");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            questionId = bundle.getString("questionId");
            questionString=bundle.getString("questionString");
            quesNoOfAns=bundle.getString("questionnoOfans");
        }
        tvQuesDisplay=findViewById(R.id.tvDisplayQues);
        tvQuesDisplay.setText(questionString);


        Bundle addQuestId = new Bundle();
        addQuestId.putString("quesId",questionId);
        //addQuestId.putString("questionnoOfans",quesNoOfAns);
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.ans_listHolder);

        if(frag == null)
        {
            frag = new AnsListFragment();
            frag.setArguments(addQuestId);
            fm.beginTransaction().add(R.id.ans_listHolder, frag).commit();
        }
        //creating a dialog box
        btnAns=findViewById(R.id.btnAnswer);
        btnAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.post_answer_dialog);
                dialog.setTitle("Add your Answer");

                // set the custom dialog components - text, image and button
                final EditText etAns=(EditText)dialog.findViewById(R.id.etAnswer);
                Button dialogButtonPost= (Button) dialog.findViewById(R.id.dialogButtonPost);

                dialogButtonPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        answer=etAns.getText().toString();
                        if(TextUtils.isEmpty(answer)) {
                            Toast.makeText(AnswersActivity.this,"You should enter your answer",Toast.LENGTH_LONG).show();
                        }
                        else {
                            postAnswer(questionId, answer);
                            Toast.makeText(context, "Answer posted", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });

                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
     }
    private void postAnswer(String questionId,String ansString){
        String answerId= dbAns.push().getKey();
        Answer answer = new Answer(answerId,ansString,questionId);
        dbAns.child(answerId).setValue(answer);
    }

}
