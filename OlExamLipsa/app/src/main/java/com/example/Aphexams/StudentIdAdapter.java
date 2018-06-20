package com.example.Aphexams;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 24-05-2018.
 */

public class StudentIdAdapter extends RecyclerView.Adapter<StudentIdAdapter.StudentIdViewHolder>
{
    private List<StudentId> studentIdList;

    public StudentIdAdapter(List<StudentId> studentidarraylist) {
        this.studentIdList=studentidarraylist;
    }


    @Override
    public StudentIdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentid_row,parent,false);
        StudentIdViewHolder studentIdViewHolder=new StudentIdViewHolder(view);
        return studentIdViewHolder;
    }

    @Override
    public void onBindViewHolder(StudentIdViewHolder holder, int position) {

        StudentId studentid= studentIdList.get(position);
        holder.tvStudentId.setText(studentid.getStudentId());
      //  holder.tvanswerdate.setText(answer.getAnswerdate());

    }

    @Override
    public int getItemCount() {
        return studentIdList.size();
    }

    public class StudentIdViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvStudentId;

        public StudentIdViewHolder(View itemView)
        {
            super(itemView);
            tvStudentId=(TextView)itemView.findViewById(R.id.studentidtv);

        }
    }
}
