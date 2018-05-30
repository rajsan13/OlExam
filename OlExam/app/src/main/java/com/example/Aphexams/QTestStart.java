package com.example.Aphexams;

import com.parse.*;

import java.io.*;
import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.widget.*;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.example.Aphexams.Global;
public class QTestStart extends Activity{

	Button bqtssubmit,bqtsnext,bqtsexit,bqprev,reset;
	TextView oop1,oop2,oop3,oop4,textView1,qquestn;
	EditText ccorrect;
	private static final String FORMAT = "%02d:%02d:%02d";
	public static int  num4=1;
	public static int counter=0;
	private RadioGroup radioGroup;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioButton radio4;
	private TextView mTextField;
	private static int time=0;
	private static int flag2=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qtest_start);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
		counter=0;
		num4=1;
		
		Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking"); 
        final TextView tw= (TextView)findViewById(R.id.textView9);
        tw.setText("Hello "+studname);
        final String tillNow = intentIndex.getStringExtra("tillnow");
       final String verbo = intentIndex.getStringExtra("verbo");
       final String quanto = intentIndex.getStringExtra("quanto");

		radioGroup = (RadioGroup)findViewById(R.id.group1);
		radio1 = (RadioButton)findViewById(R.id.radio1);
		radio2= (RadioButton)findViewById(R.id.radio2);
		radio3= (RadioButton)findViewById(R.id.radio3);
		radio4= (RadioButton)findViewById(R.id.radio4);
		mTextField=(TextView)findViewById(R.id.textView11);
		bqtsexit = (Button)findViewById(R.id.qtsexit);
		new CountDownTimer(Global.time1, Global.time2) {

			public void onTick(long millisUntilFinished) {
				//mTextField.setText("Time remaining: " + millisUntilFinished / 1000);
				//here you can have your logic to set text to edittext
				mTextField.setText("Time Remaining: "+String.format(FORMAT,
						TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
						TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
								TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
						TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
								TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
			}

			public void onFinish() {
				mTextField.setText("done!");
				time=1;
				if(flag2==0)
				bqtsexit.performClick();
			}

		}.start();
		
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
		     /* String option1=object.getString("opt1");
		      final TextView oop1 = (TextView) findViewById(R.id.textView3);
		      oop1.setText(option1); 
		      String option2=object.getString("opt2");
		      final TextView oop2 = (TextView) findViewById(R.id.textView4);
		      oop2.setText(option2); 
		      String option3=object.getString("opt3");
		      final TextView oop3 = (TextView) findViewById(R.id.textView5);
		      oop3.setText(option3);
		      String option4=object.getString("opt4");
		      final TextView oop4 = (TextView) findViewById(R.id.textView6);
		      oop4.setText(option4);*/
			  radio1.setText(object.getString("opt1"));
			  radio2.setText(object.getString("opt2"));
			  radio3.setText(object.getString("opt3"));
			  radio4.setText(object.getString("opt4"));

		     
		    }
		  }
		});
		
		
		
		bqtssubmit = (Button)findViewById(R.id.qtssubmit);
		bqtssubmit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
		// in if condition num4==5
		if(time==1){
			time=0;
			final ProgressDialog dlg = new ProgressDialog(QTestStart.this);
	        dlg.setTitle("Please wait.");
	        dlg.setMessage("Processing request.  Navigating to result evaluation.  Please wait.");
	        dlg.show();
			
				Intent indexIntent=new Intent(QTestStart.this,Result.class);
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
				
				
				
					startActivity(indexIntent);	}
		else{
						 
						
					   // final EditText ccorrect = (EditText) findViewById(R.id.editText1);
						if(R.id.radio1==radioGroup.getCheckedRadioButtonId()) {

							//String cor=radio1.getText().toString();
							//ccorrect.setText("");
							ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
							query.whereEqualTo("qno",num4);
							//query.whereEqualTo("rightans",Integer.parseInt(cor));
							query.whereEqualTo("rightans",1);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (object == null) {
										Log.d("que", "The getFirst request failed.");
										//ccorrect.setEnabled(false);
										counter=counter-1;
										Log.d("MYINT", "value: " + counter);
									} else {
										Log.d("que", "Retrieved the object.");
										counter=counter+5;
										//ccorrect.setEnabled(false);
										Log.d("MYINT", "value: " + counter);

									}
								}
							});
						}
						else if(R.id.radio2==radioGroup.getCheckedRadioButtonId()) {

							//String cor=radio2.getText().toString();
							//ccorrect.setText("");
							ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
							query.whereEqualTo("qno",num4);
							//query.whereEqualTo("rightans",Integer.parseInt(cor));
							query.whereEqualTo("rightans",2);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (object == null) {
										Log.d("que", "The getFirst request failed.");
										//ccorrect.setEnabled(false);
										counter=counter-1;
										Log.d("MYINT", "value: " + counter);
									} else {
										Log.d("que", "Retrieved the object.");
										counter=counter+5;
										//ccorrect.setEnabled(false);
										Log.d("MYINT", "value: " + counter);

									}
								}
							});
						}
						else if(R.id.radio3==radioGroup.getCheckedRadioButtonId()) {

							String cor=radio3.getText().toString();
							//ccorrect.setText("");
							ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
							query.whereEqualTo("qno",num4);
							//query.whereEqualTo("rightans",Integer.parseInt(cor));
							query.whereEqualTo("rightans",3);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (object == null) {
										Log.d("que", "The getFirst request failed.");
										//ccorrect.setEnabled(false);
										counter=counter-1;
										Log.d("MYINT", "value: " + counter);
									} else {
										Log.d("que", "Retrieved the object.");
										counter=counter+5;
										//ccorrect.setEnabled(false);
										Log.d("MYINT", "value: " + counter);

									}
								}
							});
						}
						else if(R.id.radio4==radioGroup.getCheckedRadioButtonId()) {

							String cor=radio4.getText().toString();
							//ccorrect.setText("");
							ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
							query.whereEqualTo("qno",num4);
							//query.whereEqualTo("rightans",Integer.parseInt(cor));
							query.whereEqualTo("rightans",4);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (object == null) {
										Log.d("que", "The getFirst request failed.");
										//ccorrect.setEnabled(false);
										counter=counter-1;
										Log.d("MYINT", "value: " + counter);
									} else {
										Log.d("que", "Retrieved the object.");
										counter=counter+5;
										//cccorrect.setEnabled(false);
										Log.d("MYINT", "value: " + counter);

									}
								}
							});
						}



					     
					     
					     
					     
					}
			}
		});
		
		
		
		
		
		bqtsnext = (Button)findViewById(R.id.qtsnext);
		bqtsnext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				num4++;
				//final EditText ccorrect = (EditText) findViewById(R.id.editText1);
				//ccorrect.setEnabled(true);
			    // ccorrect.setText("");
				radio1.setChecked(false);
				radio2.setChecked(false);
				radio3.setChecked(false);
				radio4.setChecked(false);

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
				    /*  String option1=object.getString("opt1");
				      final TextView oop1 = (TextView) findViewById(R.id.textView3);
				      oop1.setText(option1); 
				      String option2=object.getString("opt2");
				      final TextView oop2 = (TextView) findViewById(R.id.textView4);
				      oop2.setText(option2); 
				      String option3=object.getString("opt3");
				      final TextView oop3 = (TextView) findViewById(R.id.textView5);
				      oop3.setText(option3);
				      String option4=object.getString("opt4");
				      final TextView oop4 = (TextView) findViewById(R.id.textView6);
				      oop4.setText(option4);*/

						radio1.setText(object.getString("opt1"));
						radio2.setText(object.getString("opt2"));
						radio3.setText(object.getString("opt3"));
						radio4.setText(object.getString("opt4"));
				      
				     
				    }
				  }
				});
					
					
					
					
					
			}
		});

		bqprev = (Button)findViewById(R.id.qtsprev);
		bqprev.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				num4--;
				//final EditText ccorrect = (EditText) findViewById(R.id.editText1);
				//ccorrect.setEnabled(true);
				// ccorrect.setText("");

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
				    /*  String option1=object.getString("opt1");
				      final TextView oop1 = (TextView) findViewById(R.id.textView3);
				      oop1.setText(option1);
				      String option2=object.getString("opt2");
				      final TextView oop2 = (TextView) findViewById(R.id.textView4);
				      oop2.setText(option2);
				      String option3=object.getString("opt3");
				      final TextView oop3 = (TextView) findViewById(R.id.textView5);
				      oop3.setText(option3);
				      String option4=object.getString("opt4");
				      final TextView oop4 = (TextView) findViewById(R.id.textView6);
				      oop4.setText(option4);*/

							radio1.setText(object.getString("opt1"));
							radio2.setText(object.getString("opt2"));
							radio3.setText(object.getString("opt3"));
							radio4.setText(object.getString("opt4"));


						}
					}
				});





			}
		});


		reset = (Button)findViewById(R.id.reset);
		reset.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				radio1.setChecked(false);
				radio2.setChecked(false);
				radio3.setChecked(false);
				radio4.setChecked(false);
			}
		});

		final int fverbo=0;
		bqtsexit = (Button)findViewById(R.id.qtsexit);
		bqtsexit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(QTestStart.this);
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
		        
		        
		        flag2=1;
				Intent indexIntent=new Intent(QTestStart.this,Result.class);
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
