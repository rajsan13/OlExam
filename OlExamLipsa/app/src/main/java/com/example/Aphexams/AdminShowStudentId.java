package com.example.Aphexams;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.view.MenuItem;
import java.util.Objects;
import java.util.StringTokenizer;

//import app.android.project.com.olexam.R;



public class AdminShowStudentId extends Activity {

    private RecyclerView recycleerviewDetails;
    private RecyclerView.LayoutManager layoutManager;
    private StudentIdAdapter studentidAdapter;
    private ArrayList<StudentId> studentidarraylist;
    private boolean notComplete = true;
    private String studId;
    String temp;
    StudentId studentId;

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
                            //Toast.makeText(getApplicationContext(),Integer.toString(objects.size()),Toast.LENGTH_LONG).show();
                            for(int i=0;i<objects.size();i++)
                            {
                                String studentidstring = (String) objects.get(i).get("StudUserName");
                                studentId=new StudentId();
                                studentId.setStudentId(studentidstring);
                                studentId.setStudentEmailid((String)objects.get(i).get("StudEmId"));

                                studentId.setStudentPhno((String)objects.get(i).get("StudPhnNo"));

                                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("result");
                                query1.whereEqualTo("Studname",studentidstring);

                                query1.getFirstInBackground(new GetCallback<ParseObject>() {
                                    public void done(ParseObject object, ParseException e) {
                                        if (object == null) {
                                            Log.d("vque", "The getFirst request failed.");
                                        } else {
                                            Log.d("vque", "Retrieved the object.");
                                           // Toast.makeText(getApplicationContext(),"marks: "+(String) object.get("MARKS"),Toast.LENGTH_SHORT).show();
                                           temp=(String) object.get("MARKS");
                                            studentId.setStudentMark((String) object.get("MARKS"));

                                        }
                                    }
                                });
                                studentidarraylist.add(studentId);
                               // Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();


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
                                   /* Intent i=new Intent(AdminShowStudentId.this,ViewStudent.class);
                                    i.putExtra("studId",studId);
                                    startActivity(i);*/
                                }
                            }));

                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                notComplete=false;
                //Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();

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
