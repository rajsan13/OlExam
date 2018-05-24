package com.example.Aphexams;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Sandeep on 23-05-2018.
 */
public class Rank extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("results");
        query1.addDescendingOrder("totalmarks");
        query1.orderByDescending("totalmarks");
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (object == null) {
                    //Log.d("StudPhnNo", "The getFirst request failed.");
                    // PhoneeNumber="NULL";
                } else {
                    //Log.d("StudPhnNo", "Retrieved the object.");
                    Number k = object.getNumber("totalmarks");
                    Button b= (Button)findViewById(R.id.rhome);
                    b.setText(k.toString());

                }
            }

        });
    }
}
