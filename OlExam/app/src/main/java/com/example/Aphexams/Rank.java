package com.example.Aphexams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep on 23-05-2018.
 */

public class Rank extends Activity {


    ArrayList<String> arl = new ArrayList<String>();
    public static int i=0;
    private Button b;
    private Button c;
    private TextView t;
    static int k=0;
    String concat;
    Button ran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
       // c=(Button)findViewById((R.id.button1)) ;
        //b=(Button)findViewById((R.id.rhome)) ;
        t=(TextView)findViewById(R.id.textView14);
        ran=(Button)findViewById(R.id.button7);
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(Rank.this);


        final String username = settings.getString("username", "");
       // t.setText(username);
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("results");
                query1.addDescendingOrder("totalmarks");
                query1.orderByDescending("totalmarks");
       // List<ParseObject> objects = query1.find();

                query1.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for (ParseObject o : list) {
                                //results s = new Student();
                                arl.add(o.getString("Studname"));

                                //t.setText(o.getString("Studname"));
                                //k++;

                               // t.setText(username);
                          /*  if(t.getText().toString()==username)
                                {
                                    t.setText(o.getNumber("totalmarks").toString());
                                    //t.setText("hello");

                                }*/


                                    System.out.println(o.getString("Studname"));
                                //b.setText(o.getNumber("totalmarks").toString());
                                //arl.add(o.getNumber("totalmarks").intValue());


                               // i++;
                                //System.out.println(arr[i]);
                                //System.out.println(arr[i]);
                            }
                            // studentList is full here
                        } else {
                            System.out.println("hi1");
                            t.setText("hiii");
                        }
                        // studentList is full here too
                    }

                });


        // studentList is empty here
        //return studentList;

        ran.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //t.setText(arl.get(3).toString());
                for (int i=0;i<arl.size();i++)
                {
                    if(arl.get(i).toString().equals(username))
                    {

                       k=i;
                        t.setText(Integer.toString(k));
                    }
                }
            }
        });

    }
}
