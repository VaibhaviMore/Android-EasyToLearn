package com.example.sam.easytolearn_finalproject.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sam.easytolearn_finalproject.R;
import com.example.sam.easytolearn_finalproject.model.Answer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AnsListFragment extends Fragment {
    private RecyclerView rView;
    private Query query;
    FirebaseRecyclerAdapter adapter;
    private String ansString;

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
        View v = inflater.inflate(R.layout.ans_list_fragment, container, false);
        rView = v.findViewById(R.id.rvAnswers);
        query = FirebaseDatabase.getInstance().getReference().child("answer").orderByChild("questionId").equalTo(getArguments().getString("quesId"));

        FirebaseRecyclerOptions<Answer> options = new FirebaseRecyclerOptions.Builder<Answer>().setQuery(query, Answer.class).build();
        adapter = new FirebaseRecyclerAdapter<Answer, AnswerHolder>(options) {
            @Override
            public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new AnswerHolder(layoutInflater, parent);
            }

            @Override
            protected void onBindViewHolder(AnswerHolder holder, int position, Answer model)
            {
                holder.setIsRecyclable(false);
                holder.bind(model);
            }
        };
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(adapter);

        return v;

    }
    private class AnswerHolder extends RecyclerView.ViewHolder
    {
        TextView tvAnswer;
        public AnswerHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.answer_row_layout, parent, false));
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }
        public void bind(Answer model)
        {
            String ansString=model.getAnswerString();
            tvAnswer.setText(ansString);
        }
    }
}
