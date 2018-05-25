package com.example.Aphexams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Sandeep on 23-05-2018.
 */

public class Rank extends Activity {
    String arr[];
    int i=0;
    private Button b;
    private Button c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        c=(Button)findViewById((R.id.button1)) ;
        b=(Button)findViewById((R.id.rhome)) ;
        c.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("results");
                query1.addDescendingOrder("totalmarks");
                query1.orderByDescending("totalmarks");

                query1.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for (ParseObject o : list) {
                                //results s = new Student();
                                arr[i]=o.getNumber("totalmarks").toString();
                                b.setText(o.getNumber("totalmarks").toString());
                                i++;
                                System.out.println("hi");
                                System.out.println(arr[i]);
                            }
                            // studentList is full here
                        } else {
                            System.out.println("hi1");
                        }
                        // studentList is full here too
                    }

                });
            }
        });

        // studentList is empty here
        //return studentList;


    }
}
