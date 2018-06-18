package com.example.Aphexams;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;

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
