package com.example.Aphexams;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collections;

/**
 * Created by Sandeep on 25-05-2018.
 */
public class VTestInstructions extends Activity {

    private Button startbutton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vtest_instructions);
        startbutton=(Button)findViewById(R.id.vbutton);
        Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking");
        final TextView tw= (TextView)findViewById(R.id.textView9);
        //tw.setText("Hello "+studname);
        final String tillNow = intentIndex.getStringExtra("tillnow");
        final String verbo = intentIndex.getStringExtra("verbo");
        final String quanto = intentIndex.getStringExtra("quanto");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("vque", "The getFirst request failed.");
                } else {
                    Log.d("vque", "Retrieved the object.");
                  //  Toast.makeText(getApplicationContext(),object.getInt("Quesno")+"",Toast.LENGTH_LONG).show();
                    TextView tvques=(TextView)findViewById(R.id.tvquestion);
                    tvques.setText(object.getInt("Quesno")+" questions");

                    }


                }

        });
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("TimeLimit");
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("vque", "The getFirst request failed.");
                } else {
                    Log.d("vque", "Retrieved the object.");
                  //  Toast.makeText(getApplicationContext(),(object.getInt("Time3")/(object.getInt("Time4")*60))+"",Toast.LENGTH_LONG).show();
                    TextView tvtime=(TextView)findViewById(R.id.tvtimelimit);
                    tvtime.setText((object.getInt("Time3")/(object.getInt("Time4")*6))+" minutes");

                }


            }

        });




        startbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(VTestInstructions.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Processing request.  Navigating to Quantitative Test.  Please wait.");
                dlg.show();
                Intent indexIntent=new Intent(VTestInstructions.this,VTestStart.class);
                indexIntent.putExtra("studentInvoking",studname);
                indexIntent.putExtra("tillnow","");
                indexIntent.putExtra("quanto","0");
                indexIntent.putExtra("verbo","0");
                startActivity(indexIntent);
            }
        });
    }
}
