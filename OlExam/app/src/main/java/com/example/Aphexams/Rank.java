package com.example.Aphexams;

import android.app.Activity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("results");
        query1.addDescendingOrder("totalmarks");
        query1.orderByDescending("totalmarks");

    }
}
