package com.example.Aphexams;
import com.parse.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VTestStart extends Activity implements View.OnClickListener{

	Button bvsubmit,bvnext,bvexit,reset,bvprev;
	TextView oop1,oop2,oop3,oop4,textView1,qquestn;
	//EditText ccorrect;
	public static  int correct=0;
	public static int  skipped=0;
	public static int  incorrect=0;
	public static int  num5=1;
	public static int counter1=0;
	public static String s;
	private RadioGroup radioGroup;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioButton radio4;
	private  TextView mTextField1;
	private static final String FORMAT = "%02d:%02d:%02d";
	private static int flag1=0;
	private int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vtest_start);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
		Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking"); 
        final TextView tw= (TextView)findViewById(R.id.textView9);
        tw.setText("Hello "+studname);
		s=studname;
        final String tillNow = intentIndex.getStringExtra("tillnow");
        final String quanto = intentIndex.getStringExtra("quanto"); 
        final String verbo = intentIndex.getStringExtra("verbo");
        num5=1;
        counter1=0;
		radioGroup = (RadioGroup)findViewById(R.id.group1);
		radio1 = (RadioButton)findViewById(R.id.radio1);
		radio2= (RadioButton)findViewById(R.id.radio2);
		radio3= (RadioButton)findViewById(R.id.radio3);
		radio4= (RadioButton)findViewById(R.id.radio4);
		mTextField1=(TextView)findViewById(R.id.textView);
		bvexit = (Button)findViewById(R.id.vexit);
		new CountDownTimer(Global.time3, Global.time4) {

			public void onTick(long millisUntilFinished) {
				//mTextField.setText("Time remaining: " + millisUntilFinished / 1000);
				//here you can have your logic to set text to edittext
				mTextField1.setText("Time Remaining: "+String.format(FORMAT,
						TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
						TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
								TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
						TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
								TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
			}

			public void onFinish() {
				mTextField1.setText("done!");
				//time=1;
				//if(flag1==0)
				ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
				query.orderByDescending("updatedAt");
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
						} else {
							skipped=object.getNumber("Quesno").intValue()-(correct+incorrect);
							//Toast.makeText(VTestStart.this,Integer.toString(skipped)+" "+Integer.toString(correct)+" "+Integer.toString(incorrect),Toast.LENGTH_LONG).show();
							//Toast.makeText(VTestStart.this,Integer.toString(correct),Toast.LENGTH_LONG).show();
							//Toast.makeText(VTestStart.this,Integer.toString(skipped),Toast.LENGTH_LONG).show();
							SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(VTestStart.this);
							//prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
							SharedPreferences.Editor editor= prefs.edit();
							editor.putString("correct",Integer.toString(correct));
							editor.putString("incorrect",Integer.toString(incorrect));
							editor.putString("skipped",Integer.toString(skipped));
							editor.commit();
							Intent i2=new Intent(VTestStart.this,Result2.class);
							startActivity(i2);
						}
					}
				});


			}

		}.start();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
		query.whereEqualTo("vqno",num5);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.d("vque", "The getFirst request failed.");
		    } else {
		      Log.d("vque", "Retrieved the object.");
		      String questiondata=object.getString("vque");
		      final TextView qquestn = (TextView) findViewById(R.id.textView2);
		      qquestn.setText(questiondata);
		    /*  String option1=object.getString("vopt1");
		      final TextView oop1 = (TextView) findViewById(R.id.textView3);
		      oop1.setText(option1); 
		      String option2=object.getString("vopt2");
		      final TextView oop2 = (TextView) findViewById(R.id.textView4);
		      oop2.setText(option2); 
		      String option3=object.getString("vopt3");
		      final TextView oop3 = (TextView) findViewById(R.id.textView5);
		      oop3.setText(option3);
		      String option4=object.getString("vopt4");
		      final TextView oop4 = (TextView) findViewById(R.id.textView6);
		      oop4.setText(option4);*/
				radio1.setText(object.getString("vopt1"));
				radio2.setText(object.getString("vopt2"));
				radio3.setText(object.getString("vopt3"));
				radio4.setText(object.getString("vopt4"));

				
		      
		     
		    }
		  }
		});
		
		bvsubmit = (Button)findViewById(R.id.vsubmit);
		bvsubmit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				
		if(num5==0){
			final ProgressDialog dlg = new ProgressDialog(VTestStart.this);
	        dlg.setTitle("Please wait.");
	        dlg.setMessage("Processing request.  Navigating to result evaluation.  Please wait.");
	        dlg.show();
				Intent indexIntent=new Intent(VTestStart.this,Result.class);
				indexIntent.putExtra("studentInvoking",studname);
				
				ParseObject res = new ParseObject("results");
			        res.put("Studname",studname);
			        res.put("verbmarks",counter1);
			        res.saveInBackground();
					startActivity(indexIntent);	}
		else{
						 
						
					    // final EditText ccorrect = (EditText) findViewById(R.id.editText1);
					   //  String cor=ccorrect.getText().toString();
					     //ccorrect.setText("");
					    /* ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
							query.whereEqualTo("vqno",num5);
							query.whereEqualTo("vrightans",Integer.parseInt(cor));
							query.getFirstInBackground(new GetCallback<ParseObject>() {
							  public void done(ParseObject object, ParseException e) {
							    if (object == null) {
							      Log.d("vque", "The getFirst request failed.");
							      counter1=counter1-1;
							    //  ccorrect.setEnabled(false);
							    } else {
							      Log.d("vque", "Retrieved the object.");
							      counter1=counter1+5;
							   //   ccorrect.setEnabled(false);
							     
							    }
							  }
							});*/

			if(R.id.radio1==radioGroup.getCheckedRadioButtonId()) {

				//String cor=radio1.getText().toString();
				//ccorrect.setText("");
				flag=1;

				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vex");
				query1.whereEqualTo("vqno",num5);
				query1.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
                                bvsubmit.setText("Null");
						} else {

							Global.corr=object.getNumber("vrightans");
							Global.quest=object.getString("vque");


							//co.setText(corr.toString());
							//qu.setText(quest);

						}
						Number n=Global.corr;
						String s=Global.quest;
						ParseObject response = new ParseObject("response"+studname);
						response.put("questionNumber",num5);
						response.put("question",s);
						response.put("submittedAnswer",1);
						response.put("correctAnswer",n);
						response.saveInBackground();
					}
				});





				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num5);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("vrightans",1);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							counter1=counter1-1;
							incorrect++;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							counter1=counter1+5;
							correct++;
							//ccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
			else if(R.id.radio2==radioGroup.getCheckedRadioButtonId()) {

				//String cor=radio2.getText().toString();
				//ccorrect.setText("");
				flag=2;

				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vex");
				query1.whereEqualTo("vqno",num5);
				query1.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							bvsubmit.setText("Null");
						} else {

							Global.corr=object.getNumber("vrightans");
							Global.quest=object.getString("vque");


							//co.setText(corr.toString());
							//qu.setText(quest);

						}
						Number n=Global.corr;
						String s=Global.quest;
						ParseObject response = new ParseObject("response"+studname);
						response.put("questionNumber",num5);
						response.put("question",s);
						response.put("submittedAnswer",2);
						response.put("correctAnswer",n);
						response.saveInBackground();
					}
				});


				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num5);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("vrightans",2);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							counter1=counter1-1;
							incorrect++;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							counter1=counter1+5;
							correct++;
							//ccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
			else if(R.id.radio3==radioGroup.getCheckedRadioButtonId()) {

				flag=3;

				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vex");
				query1.whereEqualTo("vqno",num5);
				query1.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							bvsubmit.setText("Null");
						} else {

							Global.corr=object.getNumber("vrightans");
							Global.quest=object.getString("vque");


							//co.setText(corr.toString());
							//qu.setText(quest);

						}
						Number n=Global.corr;
						String s=Global.quest;
						ParseObject response = new ParseObject("response"+studname);
						response.put("questionNumber",num5);
						response.put("question",s);
						response.put("submittedAnswer",3);
						response.put("correctAnswer",n);
						response.saveInBackground();
					}
				});


				String cor=radio3.getText().toString();
				//ccorrect.setText("");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num5);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("vrightans",3);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							counter1=counter1-1;
							incorrect++;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							counter1=counter1+5;
							correct++;
							//ccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
			else if(R.id.radio4==radioGroup.getCheckedRadioButtonId()) {
				flag=4;

				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vex");
				query1.whereEqualTo("vqno",num5);
				query1.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							bvsubmit.setText("Null");
						} else {

							Global.corr=object.getNumber("vrightans");
							Global.quest=object.getString("vque");


							//co.setText(corr.toString());
							//qu.setText(quest);

						}
						Number n=Global.corr;
						String s=Global.quest;
						ParseObject response = new ParseObject("response"+studname);
						response.put("questionNumber",num5);
						response.put("question",s);
						response.put("submittedAnswer",4);
						response.put("correctAnswer",n);
						response.saveInBackground();
					}
				});


				String cor=radio4.getText().toString();
				//ccorrect.setText("");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num5);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("vrightans",4);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							counter1=counter1-1;
							incorrect++;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							counter1=counter1+5;
							correct++;
							//cccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}


					     
					     
					     
					}
			}
		});
		
		
		
		bvnext = (Button)findViewById(R.id.vnext);
		bvnext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				num5++;
				//EditText ccorrect = (EditText) findViewById(R.id.editText1);
				//.setEnabled(true);
			   //  ccorrect.setText("");
				radio1.performClick();
				radio2.performClick();
				radio3.performClick();
				radio4.performClick();
				radio1.setChecked(false);
				radio2.setChecked(false);
				radio3.setChecked(false);
				radio4.setChecked(false);
				radioGroup.clearCheck();

				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num5);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("vque", "The getFirst request failed.");
				    } else {
				      Log.d("vque", "Retrieved the object.");
				      String questiondata=object.getString("vque");
				      final TextView qquestn = (TextView) findViewById(R.id.textView2);
				      qquestn.setText(questiondata);
				     /* String option1=object.getString("vopt1");
				      final TextView oop1 = (TextView) findViewById(R.id.textView3);
				      oop1.setText(option1); 
				      String option2=object.getString("vopt2");
				      final TextView oop2 = (TextView) findViewById(R.id.textView4);
				      oop2.setText(option2); 
				      String option3=object.getString("vopt3");
				      final TextView oop3 = (TextView) findViewById(R.id.textView5);
				      oop3.setText(option3);
				      String option4=object.getString("vopt4");
				      final TextView oop4 = (TextView) findViewById(R.id.textView6);
				      oop4.setText(option4);*/

						radio1.setText(object.getString("vopt1"));
						radio2.setText(object.getString("vopt2"));
						radio3.setText(object.getString("vopt3"));
						radio4.setText(object.getString("vopt4"));

		                skipped++;
				     
				    }
				  }
				});
			}
		});

		bvprev = (Button)findViewById(R.id.vprev);
		bvprev.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				num5--;
				switch(flag)
				{
					case 1:
						radio4.setChecked(false);
						radio1.setChecked(true);
						radio2.setChecked(false);
						radio3.setChecked(false);
						break;
					case 2:
						radio4.setChecked(false);
						radio1.setChecked(false);
						radio3.setChecked(false);
						radio2.setChecked(true);
						break;
					case 3:
						radio4.setChecked(false);
						radio2.setChecked(false);
						radio1.setChecked(false);
						radio3.setChecked(true);
						break;
					case 4:
						radio1.setChecked(false);
						radio2.setChecked(false);
						radio3.setChecked(false);
						radio4.setChecked(true);
						break;

				}


				//final EditText ccorrect = (EditText) findViewById(R.id.editText1);
				//ccorrect.setEnabled(true);
				// ccorrect.setText("");

				ParseQuery<ParseObject> query = ParseQuery.getQuery("exams");
				query.whereEqualTo("qno",num5);
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
		
		
		bvexit = (Button)findViewById(R.id.vexit);
		bvexit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(VTestStart.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request. Exiting the test.  Please wait.");
		        dlg.show();





				
				/*ParseQuery<ParseObject> query = ParseQuery.getQuery("results");
				query.whereEqualTo("Studname",studname);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("Studname", "The getFirst request failed.");
				      ParseObject res = new ParseObject("results");
				        res.put("Studname",studname);
				        res.put("verbmarks",counter1);
				        res.saveInBackground();
				    } else {
				      Log.d("Studname", "Retrieved the object.");
				      object.put("verbmarks",counter1);
				    }
				  }
				});*/
		        flag1=1;
		      /*Intent indexIntent=new Intent(VTestStart.this,Result.class);
				//indexIntent.putExtra("studentInvoking",studname);
				//indexIntent.putExtra("quanto",quanto);
				//indexIntent.putExtra("verbo",Integer.toString(counter1));
				//indexIntent.putExtra("which","quant");
				//if(tillNow.equals("")){indexIntent.putExtra("tillnow","v");}
				//else if(tillNow.equals("q")){indexIntent.putExtra("tillnow","qv");}
				startActivity(indexIntent);*/
			}
		});

		findViewById(R.id.vexit).setOnClickListener(this);
}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.vexit:
				new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Are you sure?")
						.setContentText("You won't be able to continue the Test")
						.setCancelText("No,cancel please!")
						.setConfirmText("Yes,Exit the Test!")
						.showCancelButton(true)
						.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								// reuse previous dialog instance, keep widget user state, reset them if you need
								sDialog.setTitleText("Cancelled!")
										.setContentText("Continue with the test :)")
										.setConfirmText("OK")
										.showCancelButton(false)
										.setCancelClickListener(null)
										.setConfirmClickListener(null)
										.changeAlertType(SweetAlertDialog.ERROR_TYPE);

								// or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
							}
						})
						.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.setTitleText("Exiting the Test!")
										.setContentText("Your Response is stored!")
										.setConfirmText("OK")
										.showCancelButton(false)
										.setCancelClickListener(null)
										.setConfirmClickListener(null)
										.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

								ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
								query.orderByDescending("updatedAt");
								query.getFirstInBackground(new GetCallback<ParseObject>() {
									public void done(ParseObject object, ParseException e) {
										if (object == null) {
											Toast.makeText(VTestStart.this,"Failure",Toast.LENGTH_LONG).show();
										} else {
											skipped=object.getNumber("Quesno").intValue()-(correct+incorrect);
											//Toast.makeText(VTestStart.this,"Success",Toast.LENGTH_LONG).show();
											Toast.makeText(VTestStart.this,Integer.toString(skipped)+" "+Integer.toString(correct)+" "+Integer.toString(incorrect),Toast.LENGTH_LONG).show();
											SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(VTestStart.this);
											SharedPreferences.Editor editor= prefs.edit();
											editor.putString("mark",Integer.toString(counter1));
											editor.putString("name",s);
											editor.commit();
											Global.skippeda=skipped;
											Global.correcta=correct;
											Global.incorrecta=incorrect;
											Intent i2=new Intent(VTestStart.this,Result2.class);
											startActivity(i2);
										}
									}
								});
								/*Intent i=new Intent(VTestStart.this,Result2.class);
								startActivity(i);*/
							}
						})
						.show();


				break;
		}
	}
}
