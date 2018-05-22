package com.example.Aphexams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Sandeep on 17-05-2018.
 */


public class test_qtest_start extends Activity {

    Button bqtssubmit,bqtsnext,bqtsexit;
    TextView oop1,oop2,oop3,oop4,textView1,qquestn;
    EditText ccorrect;
    public static int  num4=1;
    public static int counter=0;
    public static String i="";
    public RadioButton opb;
    String k="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_qtest);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
        counter=0;
        num4=1;


        // Testing purpose

        /*if(id==R.id.textview3){i="";}else if(id==R.id.textview4){i="";}
        else if(id==R.id.textview5){i="";}else if(id==R.id.textview6){i="";}*/

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.question_set_type);

        Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking");
        final TextView tw= (TextView)findViewById(R.id.textView9);
        tw.setText("Hello "+studname);
        final String tillNow = intentIndex.getStringExtra("tillnow");
        final String verbo = intentIndex.getStringExtra("verbo");
        final String quanto = intentIndex.getStringExtra("quanto");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
        query.whereEqualTo("qno",num4);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("que", "The getFirst request failed.");
                } else {
                    Log.d("que", "Retrieved the object.");
                    String questiondata=object.getString("que");
                    final TextView qquestn = (TextView) findViewById(R.id.textView2);
                    qquestn.setText(questiondata);
                    String option1=object.getString("opt1");
                    final RadioButton oop1 = ((RadioButton) findViewById(R.id.r3));
                    oop1.setText(option1);
                    String option2=object.getString("opt2");
                    final RadioButton oop2 = ((RadioButton) findViewById(R.id.r4));
                    oop2.setText(option2);
                    String option3=object.getString("opt3");
                    final RadioButton oop3 = ((RadioButton) findViewById(R.id.r5));
                    oop3.setText(option3);
                    String option4=object.getString("opt4");
                    final RadioButton oop4 = ((RadioButton) findViewById(R.id.r6));
                    oop4.setText(option4);


                }
            }
        });



        bqtssubmit = (Button)findViewById(R.id.qtssubmit);
        bqtssubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if(num4==5){
                    final ProgressDialog dlg = new ProgressDialog(test_qtest_start.this);
                    dlg.setTitle("Please wait.");
                    dlg.setMessage("Processing request.  Navigating to result evaluation.  Please wait.");
                    dlg.show();

                    Intent indexIntent=new Intent(test_qtest_start.this,Result.class);
                    indexIntent.putExtra("studentInvoking",studname);

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("results");
                    query.whereEqualTo("Studname",studname);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object == null) {
                                Log.d("Studname", "The getFirst request failed.");
                                ParseObject res = new ParseObject("results");
                                res.put("Studname",studname);
                                res.put("quantmarks",counter);
                                res.saveInBackground();
                            } else {
                                Log.d("Studname", "Retrieved the object.");
                                object.put("quantmarks",counter);
                            }
                        }
                    });







                    // Integer.toString(counter)



                    startActivity(indexIntent);	}else{


                    /*final EditText ccorrect = (EditText) findViewById(R.id.editText1);
                    String cor=ccorrect.getText().toString();*/
                    int id = ((RadioGroup)findViewById( R.id.question_set_type)).getCheckedRadioButtonId();

                    if(id==R.id.r3){k="1";}
                    else if(id==R.id.r4){k="2";}
                    else if(id==R.id.r5){k="3";}
                    else if(id==R.id.r6){k="4";}
                 /*   opb=(RadioButton)findViewById(id);
                    i=opb.getText().toString();
                    String cor=i;*/
                    //ccorrect.setText("");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
                    query.whereEqualTo("qno",num4);
                    query.whereEqualTo("rightans",Integer.parseInt(k));
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object == null) {
                                Log.d("que", "The getFirst request failed.");
                               // ccorrect.setEnabled(false);
                                RadioButton b1=(RadioButton)findViewById((R.id.r3));
                                RadioButton b2=(RadioButton)findViewById((R.id.r4));
                                RadioButton b3=(RadioButton)findViewById((R.id.r5));
                                RadioButton b4=(RadioButton)findViewById((R.id.r6));
                                //b1.setChecked(false);
                                b1.setEnabled(false);
                                //b2.setChecked(false);
                                b2.setEnabled(false);
                                //b3.setChecked(false);
                                b3.setEnabled(false);
                                //b4.setChecked(false);
                                b4.setEnabled(false);
                                counter=counter-1;
                                Log.d("MYINT", "value: " + counter);
                            } else {
                                Log.d("que", "Retrieved the object.");
                                counter=counter+5;
                                //ccorrect.setEnabled(false);
                                RadioButton b1=(RadioButton)findViewById((R.id.r3));
                                RadioButton b2=(RadioButton)findViewById((R.id.r4));
                                RadioButton b3=(RadioButton)findViewById((R.id.r5));
                                RadioButton b4=(RadioButton)findViewById((R.id.r6));
                                b1.setChecked(false);
                                b1.setEnabled(false);
                                b2.setChecked(false);
                                b2.setEnabled(false);
                                b3.setChecked(false);
                                b3.setEnabled(false);
                                b4.setChecked(false);
                                b4.setEnabled(false);

                                Log.d("MYINT", "value: " + counter);

                            }
                        }
                    });





                }
            }
        });





        bqtsnext = (Button)findViewById(R.id.qtsnext);
        bqtsnext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                RadioButton b1=(RadioButton)findViewById((R.id.r3));
                RadioButton b2=(RadioButton)findViewById((R.id.r4));
                RadioButton b3=(RadioButton)findViewById((R.id.r5));
                RadioButton b4=(RadioButton)findViewById((R.id.r6));
                b1.setChecked(false);
                b1.setEnabled(true);
                b2.setChecked(false);
                b2.setEnabled(true);
                b3.setChecked(false);
                b3.setEnabled(true);
                b4.setChecked(false);
                b4.setEnabled(true);
                num4++;
              /*  final EditText ccorrect = (EditText) findViewById(R.id.editText1);
                ccorrect.setEnabled(true);
                ccorrect.setText("");*/

                ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
                query.whereEqualTo("qno",num4);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            Log.d("que", "The getFirst request failed.");
                        } else {
                            Log.d("que", "Retrieved the object.");
                            String questiondata=object.getString("que");
                            final TextView qquestn = (TextView) findViewById(R.id.textView2);
                            qquestn.setText(questiondata);
                            String option1=object.getString("opt1");
                            final RadioButton oop1 = ((RadioButton) findViewById(R.id.r3));
                            oop1.setText(option1);
                            String option2=object.getString("opt2");
                            final RadioButton oop2 = ((RadioButton) findViewById(R.id.r4));
                            oop2.setText(option2);
                            String option3=object.getString("opt3");
                            final RadioButton oop3 = ((RadioButton) findViewById(R.id.r5));
                            oop3.setText(option3);
                            String option4=object.getString("opt4");
                            final RadioButton oop4 = ((RadioButton) findViewById(R.id.r6));
                            oop4.setText(option4);


                        }
                    }
                });





            }
        });


        final int fverbo=0;
        bqtsexit = (Button)findViewById(R.id.qtsexit);
        bqtsexit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(test_qtest_start.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Processing request.  Exiting the test.  Please wait.");
                dlg.show();
		        /* ParseQuery<ParseObject> query = ParseQuery.getQuery("results");
				query.whereEqualTo("Studname",studname);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("Studname", "The getFirst request failed.");
				      ParseObject res = new ParseObject("results");
				        res.put("Studname",studname);
				        res.put("quantmarks",counter);
				        res.saveInBackground();
				    } else {
				      Log.d("Studname", "Retrieved the object.");
				      int fverbo=object.getInt("verbmarks");
				      object.put("quantmarks",counter);
				    }
				  }
				}); */



                Intent indexIntent=new Intent(test_qtest_start.this,Result.class);
                indexIntent.putExtra("studentInvoking",studname);
                indexIntent.putExtra("quanto",Integer.toString(counter));
                indexIntent.putExtra("verbo",verbo);
                indexIntent.putExtra("which","verbal");
                if(tillNow.equals("")){indexIntent.putExtra("tillnow","q");}
                else if(tillNow.equals("v")){indexIntent.putExtra("tillnow","vq");}

                //indexIntent.putExtra("verbo",Integer.toString(fverbo));

                startActivity(indexIntent);





            }
        });


    }
}
