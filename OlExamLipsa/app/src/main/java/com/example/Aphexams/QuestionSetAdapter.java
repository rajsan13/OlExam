package com.example.Aphexams;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 28-06-2018.
 */

public class QuestionSetAdapter extends RecyclerView.Adapter<QuestionSetAdapter.QuestionSetViewHolder>{
    private List<QuestionSet> questionSetList;

    public QuestionSetAdapter(List<QuestionSet> questionSetList) {
        this.questionSetList = questionSetList;
    }

    @Override
    public QuestionSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row,parent,false);
        QuestionSetAdapter.QuestionSetViewHolder questionSetViewHolder=new QuestionSetAdapter.QuestionSetViewHolder(view);
        return questionSetViewHolder;

    }

    @Override
    public void onBindViewHolder(QuestionSetViewHolder holder, int position) {
            QuestionSet questionSet=questionSetList.get(position);
            holder.tvquestion.setText(questionSet.getQuestion()+". ");
            holder.tvquesno.setText(questionSet.getQuestionNo());

    }

    @Override
    public int getItemCount() {
        return questionSetList.size();
    }

    public class QuestionSetViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvquestion,tvquesno;
        public QuestionSetViewHolder(View itemView) {
            super(itemView);
            tvquestion=(TextView)itemView.findViewById(R.id.question);
            tvquesno=(TextView)itemView.findViewById(R.id.quesno);

        }
    }
}
