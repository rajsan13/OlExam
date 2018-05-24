package com.example.Aphexams;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

//import app.android.project.com.olexam.R;

public class AdminShowStudentId extends Activity {

    private RecyclerView recycleerviewDetails;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter studentidAdapter;
    private ArrayList<String> studentidarraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_student_id);

        studentidarraylist=new ArrayList<String>();
        recycleerviewDetails=(RecyclerView)findViewById(R.id.studentid);
        if (recycleerviewDetails != null)
        {
            recycleerviewDetails.setHasFixedSize(true);
        }
        layoutManager=new GridLayoutManager(this,2);
        recycleerviewDetails.setLayoutManager(layoutManager);

        //fetching data from database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("studAuth");//matching table
        query.whereEqualTo("StudUserName",);//matching column



    }
}
