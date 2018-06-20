package com.example.Aphexams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.match.library.MatchButton;
import com.roger.match.library.MatchTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep on 11-06-2018.
 */
public class Rank1 extends Activity {


    ArrayList<String> arl = new ArrayList<String>();
    public static int i=0;
    private Button b;
    //private Button c;
    private TextView t;
    static int k=0;
    String concat;
    Button ran;
    public String c;
    public MatchTextView rankkk;
    public MatchButton gett;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        // c=(Button)findViewById((R.id.button1)) ;
        //b=(Button)findViewById((R.id.rhome)) ;
      //  t=(TextView)findViewById(R.id.textView14);

       // ran=(Button)findViewById(R.id.button7);
        gett=(MatchButton)findViewById(R.id.button7);
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(Rank1.this);

        c = settings.getString("namer", "");


      //  final String username = settings.getString("username", "");
        // t.setText(username);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("result");
        query1.addDescendingOrder("MARKS");
        query1.orderByDescending("MARKS");
        // List<ParseObject> objects = query1.find();

        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject o : list) {

                        arl.add(o.getString("Studname"));
                        System.out.println(o.getString("Studname"));

                    }

                } else {
                    System.out.println("hi1");
                    t.setText("hiii");
                }

            }

        });



        gett.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //t.setText(arl.get(3).toString());
                for (int i=0;i<arl.size();i++)
                {
                    if(arl.get(i).toString().equals(c))
                    {

                        k=i+1;
                        rankkk= (MatchTextView) findViewById(R.id.textView14);
                        rankkk.setText(Integer.toString(k));
                    }
                }
            }
        });

    }
}
