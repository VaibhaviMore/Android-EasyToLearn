package com.example.sam.easytolearn_finalproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.easytolearn_finalproject.AskQuestionActivity;
import com.example.sam.easytolearn_finalproject.DashboardActivity;
import com.example.sam.easytolearn_finalproject.R;
import com.example.sam.easytolearn_finalproject.model.Question;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListFragment extends Fragment {

    Button btnNewQuestion;
    private RecyclerView rView;
    private Query query;
    FirebaseRecyclerAdapter adapter;
    private String quesId,quesString,noOfans;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);
        rView = v.findViewById(R.id.recyclerView);
        btnNewQuestion=v.findViewById(R.id.btnAskQues);
        btnNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent for ask ques activity
                Intent i = new Intent(getActivity(), AskQuestionActivity.class);
                startActivity(i);
            }
        });
        query = FirebaseDatabase.getInstance().getReference().child("question");
        FirebaseRecyclerOptions<Question> options = new FirebaseRecyclerOptions.Builder<Question>().setQuery(query, Question.class).build();
        adapter = new FirebaseRecyclerAdapter<Question, QuestionHolder>(options) {
            @Override
            public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new QuestionHolder(layoutInflater, parent);
            }

            @Override
            protected void onBindViewHolder(QuestionHolder holder, int position, Question model)
            {
                holder.setIsRecyclable(false);
                holder.bind(model);
            }
        };
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(adapter);

        return v;

    }

    private class QuestionHolder extends RecyclerView.ViewHolder
    {
        TextView tvQuestion;
        TextView tvNoOfAnswers;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.row_layout, parent, false));
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            //tvNoOfAnswers = itemView.findViewById(R.id.tv_number_of_answers);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i = new Intent(getActivity(),AnswersActivity.class);
                   i.putExtra("questionId",quesId);
                   i.putExtra("questionString",quesString);
                   startActivity(i);
                }
            });
        }
        public void bind(Question model)
        {
            quesString=model.getQuesString();
            quesId=model.getQuestionId();
            //noOfans=model.getNoOfAnswers();
            tvQuestion.setText(quesString);
            //tvNoOfAnswers.setText(noOfans);
        }
    }

}
