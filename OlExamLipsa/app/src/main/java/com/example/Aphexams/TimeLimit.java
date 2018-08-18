package com.example.Aphexams;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.view.MenuItem;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Sandeep on 28-05-2018.
 */
public class TimeLimit extends Activity {
    EditText Timer;
    EditText Timer1;
    Button ok;
    Button setqueslimit;
    TextView tvsetqueslimit;
    int sequence_gen;
    ParseObject QuestionNo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_limit);
       // Timer=(EditText) findViewById(R.id.timer);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Timer1=(EditText)findViewById(R.id.timer1) ;
        ok=(Button)findViewById(R.id.button6);
        tvsetqueslimit=(TextView)findViewById(R.id.questionlimit);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              /*  String tim = Timer.getText().toString();
                int value=Integer.parseInt(tim);*/
                String tim1=Timer1.getText().toString();
                int value1=Integer.parseInt(tim1);
               /* Global.time1=value*1000;
                Global.time2=1000;*/
                Global.time3=value1*1000;
                Global.time4=1000;


                ParseObject res = new ParseObject("TimeLimit");
                res.put("Time3",value1*1000);
                res.put("Time4",1000);
                res.saveInBackground();



                Toast.makeText(TimeLimit.this,"Time Limit is set Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        setqueslimit=(Button)findViewById(R.id.bquestionlimit);
        setqueslimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               QuestionNo = new ParseObject("QuestionNo");
                Toast.makeText(getApplicationContext(),QuestionNo.getObjectId(),Toast.LENGTH_SHORT).show();
                //deleting all rows in a parse table
                ParseQuery<ParseObject> query=ParseQuery.getQuery("QuestionNo");
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        try {
                            parseObject.delete();
                            parseObject.saveInBackground();
                            Toast.makeText(getApplicationContext(),"deleted successfully",Toast.LENGTH_SHORT).show();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                });


                ParseQuery<ParseObject> queryv = ParseQuery.getQuery("Vex");
                //query.whereEqualTo("rightans",Integer.parseInt(cor));
                queryv.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            Log.d("que", "The getFirst request failed.");
                            int qno=Integer.parseInt(tvsetqueslimit.getText().toString());
                            sequence_gen=objects.size();
                            Toast.makeText(getApplicationContext(),"no of rows in the vex table "+sequence_gen,Toast.LENGTH_SHORT).show();
                            if(sequence_gen >= qno)
                            {
                                Toast.makeText(getApplicationContext(),tvsetqueslimit.getText().toString(),Toast.LENGTH_LONG).show();
                                QuestionNo.put("Quesno",qno);
                                QuestionNo.saveInBackground();
                                Toast.makeText(getApplicationContext(),"the no of questions appearing in the text is set!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"You can't set these many no of questions!",Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Log.d("que", "Retrieved the object.");



                        }
                    }


                });





            }
        });



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(TimeLimit.this,HomeAdmin.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


