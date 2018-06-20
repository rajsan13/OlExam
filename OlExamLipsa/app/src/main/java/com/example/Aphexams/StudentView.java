package com.example.Aphexams;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StudentView extends Activity {
    TextView sname,suserid,semailid,sphoneno;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        sname=(TextView)findViewById(R.id.sname);
        suserid=(TextView)findViewById(R.id.suserid);
        semailid=(TextView)findViewById(R.id.semailid);
        sphoneno=(TextView)findViewById(R.id.sphoneno);
        edit=(Button)findViewById(R.id.edit);


        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(StudentView.this);

        String username = settings.getString("StudUserName", "");
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("studAuth");
        query1.whereEqualTo("StudUserName",username);
        //query1.whereEqualTo("StudPassword",srpword1.getText().toString());
        //query1.
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    //Log.d("StudPhnNo", "The getFirst request failed.");
                    // PhoneeNumber="NULL";
                } else {
                    //Log.d("StudPhnNo", "Retrieved the object.");
                    String UserName = object.getString("StudUserName");
                    suserid.setText(UserName);
                    //
                    // Toast.makeText(getApplicationContext(),UserName,Toast.LENGTH_LONG).show();


                    String PhoneNumber=object.getString("StudPhnNo");
                    sphoneno.setText(PhoneNumber);
                    String Email = object.getString("StudEmId");
                    semailid.setText(Email);
                    String name=object.getString("StudName");
                    sname.setText(name);

                    //String PhoneNumber=object.getString("StudPhnNo");
                    // srphn1.setText(PhoneNumber);
                    //k++;
                    //cp();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),StudentEdit.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(StudentView.this,HomeStudent.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
