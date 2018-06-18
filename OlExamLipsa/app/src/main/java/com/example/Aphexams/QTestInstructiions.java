package com.example.Aphexams;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;
/**
 * Created by Sandeep on 25-05-2018.
 */


public class QTestInstructiions extends Activity {
    private Button bStart;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qtest_instructions);
        bStart=(Button)findViewById(R.id.button);
        Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking");
        final TextView tw= (TextView)findViewById(R.id.textView9);
        //tw.setText("Hello "+studname);
        final String tillNow = intentIndex.getStringExtra("tillnow");
        final String verbo = intentIndex.getStringExtra("verbo");
        final String quanto = intentIndex.getStringExtra("quanto");


        bStart.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(QTestInstructiions.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Processing request.  Navigating to Quantitative Test.  Please wait.");
                dlg.show();
                Intent indexIntent=new Intent(QTestInstructiions.this,QTestStart.class);
                indexIntent.putExtra("studentInvoking",studname);
                indexIntent.putExtra("tillnow","");
                indexIntent.putExtra("quanto","0");
                indexIntent.putExtra("verbo","0");
                startActivity(indexIntent);
            }
        });
    }
}
