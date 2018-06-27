package com.example.Aphexams;
import com.parse.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.content.SharedPreferences;
import android.graphics.Color;
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
	public static  int correct=0;
	public static int  skipped=0;
	public static int  incorrect=0;
	public static String s;
	//EditText ccorrect;
	static int noOfRows,randint;
	static ArrayList<Integer> list;
	public static int  num5;
	int temp;
	 int cnt=0;
	public static int counter1=0;
	private RadioGroup radioGroup;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioButton radio4;
	private  TextView mTextField1;
	private static final String FORMAT = "%02d:%02d:%02d";
	private static int flag1=0;
	private int flag=1;
	Random rand;
	int quesno;
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
		//fetching no of rows in the questions!

		rand=new Random();
		num5=(int) rand.nextInt((10 - 1) + 1) + 1;
		Toast.makeText(getApplicationContext(),"first value "+num5,Toast.LENGTH_SHORT).show();
		temp=num5;

		ParseQuery<ParseObject> queryNoOfRows = ParseQuery.getQuery("Vex");
		//query.whereEqualTo("rightans",Integer.parseInt(cor));
		queryNoOfRows.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					Log.d("que", "The getFirst request failed.");

					noOfRows=objects.size();
					 list = new ArrayList<Integer>();


					ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
					query.getFirstInBackground(new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
							if (object == null) {
								Log.d("vque", "The getFirst request failed.");
							} else {
								Log.d("vque", "Retrieved the object.");
								randint = (int) rand.nextInt((noOfRows - 1) + 1) + 1;
								quesno = (object.getInt("Quesno"));

								//Toast.makeText(getApplicationContext(), "random no is" + randint, Toast.LENGTH_LONG).show();
								for (int i = 0; i <= quesno+1; i++) {
									int add=((randint++) % (noOfRows + 1));
									if (add==0)
										continue;
									list.add(add);

								}

								Collections.shuffle(list);

								for (int i=0;i<=quesno;i++)
									Toast.makeText(getApplicationContext(), "list values are" + list.get(i), Toast.LENGTH_LONG).show();
							}
						}
					});


					/*for (int i=0; i<3; i++) {
						System.out.println(list.get(i));
					}*/

					//Toast.makeText(getApplicationContext(),Integer.toString(noOfRows),Toast.LENGTH_LONG).show();
				}
					else {
					Log.d("que", "Retrieved the object.");



				}
			}


		});


		//Java unique random number generator code

		Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking"); 
        final TextView tw= (TextView)findViewById(R.id.textView9);
        tw.setText("Hello "+studname);
        final String tillNow = intentIndex.getStringExtra("tillnow");
        final String quanto = intentIndex.getStringExtra("quanto"); 
        final String verbo = intentIndex.getStringExtra("verbo");
       // num5=1;
        counter1=0;
		radioGroup = (RadioGroup)findViewById(R.id.group1);
		radio1 = (RadioButton)findViewById(R.id.radio1);
		radio2= (RadioButton)findViewById(R.id.radio2);
		radio3= (RadioButton)findViewById(R.id.radio3);
		radio4= (RadioButton)findViewById(R.id.radio4);
		mTextField1=(TextView)findViewById(R.id.textView);
		bvexit = (Button)findViewById(R.id.vexit);
		/*test*/

		ParseQuery<ParseObject> query = ParseQuery.getQuery("TimeLimit");
		query.orderByDescending("updatedAt");
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Toast.makeText(VTestStart.this,"Failed",Toast.LENGTH_LONG).show();
				} else {
                       Global.time3=object.getNumber("Time3").intValue();
					   Global.time4=object.getNumber("Time4").intValue();

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

							ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
							query.orderByDescending("updatedAt");
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (object == null) {
									} else {
										skipped = object.getNumber("Quesno").intValue() - (correct + incorrect);
										//Toast.makeText(VTestStart.this,Integer.toString(skipped)+" "+Integer.toString(correct)+" "+Integer.toString(incorrect),Toast.LENGTH_LONG).show();
										//Toast.makeText(VTestStart.this,Integer.toString(correct),Toast.LENGTH_LONG).show();
										//Toast.makeText(VTestStart.this,Integer.toString(skipped),Toast.LENGTH_LONG).show();
										SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(VTestStart.this);
										//prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
										SharedPreferences.Editor editor = prefs.edit();
										editor.putString("correct", Integer.toString(correct));
										editor.putString("incorrect", Integer.toString(incorrect));
										editor.putString("skipped", Integer.toString(skipped));
										editor.commit();
										Intent i2 = new Intent(VTestStart.this, Result2.class);
										startActivity(i2);
									}
								}
							});
						}
					}.start();

				}
			}
		});

		/*new CountDownTimer(30000, 1000) {

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

				ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionNo");
				query.orderByDescending("updatedAt");
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
						} else {
							skipped = object.getNumber("Quesno").intValue() - (correct + incorrect);
							//Toast.makeText(VTestStart.this,Integer.toString(skipped)+" "+Integer.toString(correct)+" "+Integer.toString(incorrect),Toast.LENGTH_LONG).show();
							//Toast.makeText(VTestStart.this,Integer.toString(correct),Toast.LENGTH_LONG).show();
							//Toast.makeText(VTestStart.this,Integer.toString(skipped),Toast.LENGTH_LONG).show();
							SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(VTestStart.this);
							//prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
							SharedPreferences.Editor editor = prefs.edit();
							editor.putString("correct", Integer.toString(correct));
							editor.putString("incorrect", Integer.toString(incorrect));
							editor.putString("skipped", Integer.toString(skipped));
							editor.commit();
							Intent i2 = new Intent(VTestStart.this, Result2.class);
							startActivity(i2);
						}
					}
				});
			}
		}.start();*/



		//code to generate required number of unique random numbers

		//for setting the question and the options in the intent
		ParseQuery<ParseObject> qry = ParseQuery.getQuery("Vex");
		//Toast.makeText(getApplicationContext(),"kab ayega list value"+randint,Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(),"num5 "+num5,Toast.LENGTH_LONG).show();
		qry.whereEqualTo("vqno",temp);
		qry.getFirstInBackground(new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.d("vque", "The getFirst request failed.");
		    } else {
		      Log.d("vque", "Retrieved the object.");
		      String questiondata=object.getString("vque");
		      final TextView qquestn = (TextView) findViewById(R.id.textView2);
		      qquestn.setText(questiondata);

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

	/*	if(num5==0){
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
		else{*/


						
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
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("qno",temp);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("rightans",1);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							incorrect++;
							counter1=counter1-1;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							correct++;
							counter1=counter1+5;
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
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("qno",temp);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("rightans",2);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							incorrect++;
							counter1=counter1-1;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							correct++;
							counter1=counter1+5;
							//ccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
			else if(R.id.radio3==radioGroup.getCheckedRadioButtonId()) {

				flag=3;
				String cor=radio3.getText().toString();
				//ccorrect.setText("");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("qno",temp);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("rightans",3);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							incorrect++;
							counter1=counter1-1;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							correct++;
							counter1=counter1+5;
							//ccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
			else if(R.id.radio4==radioGroup.getCheckedRadioButtonId()) {
				flag=4;
				String cor=radio4.getText().toString();
				//ccorrect.setText("");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("qno",temp);
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.whereEqualTo("rightans",4);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("que", "The getFirst request failed.");
							//ccorrect.setEnabled(false);
							incorrect++;
							counter1=counter1-1;
							Log.d("MYINT", "value: " + counter1);
						} else {
							Log.d("que", "Retrieved the object.");
							correct++;
							counter1=counter1+5;
							//cccorrect.setEnabled(false);
							Log.d("MYINT", "value: " + counter1);

						}
					}
				});
			}
				bvsubmit.setBackgroundColor(Color.GREEN);
		}
		});
		
		
		
		bvnext = (Button)findViewById(R.id.vnext);
		bvnext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				bvsubmit.setBackgroundColor(Color.GRAY);
				radioGroup.clearCheck();
				if (num5==list.get(cnt))
					cnt++;

				if (cnt<quesno){
					temp=list.get(cnt++);
					//Toast.makeText(getApplicationContext(),"qno "+temp+" cnt "+cnt,Toast.LENGTH_LONG).show();
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
					query.whereEqualTo("vqno",temp);
					query.getFirstInBackground(new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
							if (object == null) {
								Log.d("vque", "The getFirst request failed.");
							} else {
								Log.d("vque", "Retrieved the object.");
								String questiondata=object.getString("vque");
								final TextView qquestn = (TextView) findViewById(R.id.textView2);

								radio1.setText(object.getString("vopt1"));
								radio2.setText(object.getString("vopt2"));
								radio3.setText(object.getString("vopt3"));
								radio4.setText(object.getString("vopt4"));

							}
						}
					});
				}
				else
				{
					cnt--;
					Toast.makeText(getApplicationContext(),"last question ...Do you want to exit the test?!!!",Toast.LENGTH_LONG).show();
				}


			}
		});

		bvprev = (Button)findViewById(R.id.vprev);
		bvprev.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				bvsubmit.setBackgroundColor(Color.GRAY);
				radioGroup.clearCheck();
				if (cnt==0)
				{
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
					query.whereEqualTo("qno",num5);
					Toast.makeText(getApplicationContext(),"num5 "+num5+" cnt "+cnt,Toast.LENGTH_LONG).show();
					query.getFirstInBackground(new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
							if (object == null) {
								Log.d("que", "The getFirst request failed.");
							} else {
								Log.d("que", "Retrieved the object.");
								String questiondata=object.getString("que");
								final TextView qquestn = (TextView) findViewById(R.id.textView2);
								qquestn.setText(questiondata);
								radio1.setText(object.getString("opt1"));
								radio2.setText(object.getString("opt2"));
								radio3.setText(object.getString("opt3"));
								radio4.setText(object.getString("opt4"));
							}
						}
					});

				}
				else if (cnt==-1)
				{
					Toast.makeText(getApplicationContext(),"this is the very first question!!!",Toast.LENGTH_LONG).show();
				}
				else
				{
					if (num5==list.get(--cnt))
						cnt--;
					else
					{
						temp=list.get(cnt);
						Toast.makeText(getApplicationContext(),"qno "+temp+" cnt "+cnt,Toast.LENGTH_LONG).show();
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
						query.whereEqualTo("qno",temp);
						query.getFirstInBackground(new GetCallback<ParseObject>() {
							public void done(ParseObject object, ParseException e) {
								if (object == null) {
									Log.d("que", "The getFirst request failed.");
								} else {
									Log.d("que", "Retrieved the object.");
									String questiondata=object.getString("que");
									final TextView qquestn = (TextView) findViewById(R.id.textView2);
									qquestn.setText(questiondata);

									radio1.setText(object.getString("opt1"));
									radio2.setText(object.getString("opt2"));
									radio3.setText(object.getString("opt3"));
									radio4.setText(object.getString("opt4"));
								}
							}
						});
					}

				}

			}
		});


		reset = (Button)findViewById(R.id.reset);
		reset.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				radioGroup.clearCheck();
			}
		});
		
		
		bvexit = (Button)findViewById(R.id.vexit);
		bvexit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(VTestStart.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request. Exiting the test.  Please wait.");
		        dlg.show();

				//num5 1 cnt 0 ::  qno =4 cnt 1:: qno=8 cnt =2: 10 8 7 2 9 6 3
				
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
		      /*  Intent indexIntent=new Intent(VTestStart.this,Result.class);
				indexIntent.putExtra("studentInvoking",studname);
				indexIntent.putExtra("quanto",quanto);
				indexIntent.putExtra("verbo",Integer.toString(counter1));
				indexIntent.putExtra("which","quant");
				if(tillNow.equals("")){indexIntent.putExtra("tillnow","v");}
				else if(tillNow.equals("q")){indexIntent.putExtra("tillnow","qv");}
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

