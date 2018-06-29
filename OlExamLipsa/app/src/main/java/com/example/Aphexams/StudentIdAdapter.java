package com.example.Aphexams;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    public void onBindViewHolder(final StudentIdViewHolder holder, int position) {

        StudentId studentid= studentIdList.get(position);
        holder.tvStudentId.setText(studentid.getStudentId());
        holder.tvstudemailid.setText(studentid.getStudentEmailid());
        holder.tvstudmark.setText(studentid.getStudentMark());
        holder.tvstudphno.setText(studentid.getStudentPhno());

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("result");
        query1.whereEqualTo("Studname",studentid.getStudentId());

        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("vque", "The getFirst request failed.");
                    holder.tvstudmark.setText("Score : ");
                } else {
                    Log.d("vque", "Retrieved the object.");
                    // Toast.makeText(getApplicationContext(),"marks: "+(String) object.get("MARKS"),Toast.LENGTH_SHORT).show();

                    holder.tvstudmark.setText("Score : "+object.getString("MARKS"));

                }
            }
        });
      //  holder.tvanswerdate.setText(answer.getAnswerdate());

    }

    @Override
    public int getItemCount() {
        return studentIdList.size();
    }

    public class StudentIdViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvStudentId,tvstudmark,tvstudemailid,tvstudphno;

        public StudentIdViewHolder(View itemView)
        {
            super(itemView);
            tvStudentId=(TextView)itemView.findViewById(R.id.studentidtv);
            tvstudemailid=(TextView)itemView.findViewById(R.id.stdemail);
            tvstudmark=(TextView)itemView.findViewById(R.id.stdmark);
            tvstudphno=(TextView)itemView.findViewById(R.id.stdphno);


        }
    }
}
