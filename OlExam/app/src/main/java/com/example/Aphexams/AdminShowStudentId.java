package com.example.Aphexams;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

//import app.android.project.com.olexam.R;


public class AdminShowStudentId extends Activity {

    private RecyclerView recycleerviewDetails;
    private RecyclerView.LayoutManager layoutManager;
    private StudentIdAdapter studentidAdapter;
    private ArrayList<StudentId> studentidarraylist;
    private boolean notComplete = true;
    private String studId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_student_id);

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        recycleerviewDetails=(RecyclerView)findViewById(R.id.studentid);
        if (recycleerviewDetails != null)
        {
            recycleerviewDetails.setHasFixedSize(true);
        }
        layoutManager=new GridLayoutManager(this,1);
        recycleerviewDetails.setLayoutManager(layoutManager);

        studentidarraylist=new ArrayList<StudentId>();

            if(notComplete) {


                ParseQuery<ParseObject> query = ParseQuery.getQuery("studAuth");

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(),Integer.toString(objects.size()),Toast.LENGTH_LONG).show();
                            for(int i=0;i<objects.size();i++)
                            {
                                String studentidstring = (String) objects.get(i).get("StudUserName");
                                StudentId studentId=new StudentId();
                                studentId.setStudentId(studentidstring);
                                studentidarraylist.add(studentId);
                            }
                            //Now the arraylist is populated!
                          /*  for(int i=0;i<studentidarraylist.size();i++)
                            {
                                Toast.makeText(AdminShowStudentId.this,studentidarraylist.get(i).getStudentId() + "  "+i, Toast.LENGTH_SHORT).show();

                            }*/
                            studentidAdapter=new StudentIdAdapter(studentidarraylist);
                            recycleerviewDetails.setAdapter(studentidAdapter);
                            studentidAdapter.notifyDataSetChanged();

                            recycleerviewDetails.addOnItemTouchListener(new RecyclerViewListener(AdminShowStudentId.this,new RecyclerViewListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    studId=studentidarraylist.get(position).getStudentId();
                                    Intent i=new Intent(AdminShowStudentId.this,ViewStudent.class);
                                    i.putExtra("studId",studId);
                                    startActivity(i);
                                }
                            }));

                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                notComplete=false;

            }




        /*fetching data from the database*/













    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(AdminShowStudentId.this,HomeAdmin.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
