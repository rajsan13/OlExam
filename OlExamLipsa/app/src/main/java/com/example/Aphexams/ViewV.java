package com.example.Aphexams;
import com.parse.*;

import java.io.*;
import java.util.*;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;
public class ViewV extends Activity{
	Point p;
	TextView ccoreect,textView1,qquestn;
	Button ddelete,vvqcancel,nnext,pprev;
	TextView qquestion,oop1,oop2,oop3,oop4;
	String objectId,questiondata;
	//Button bback,banother,bsub;
	//TextView qvtop1,qvtop2,qvtop3,qvtop4,qvtque,qvtcorrect;
	public static int  num3=1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_v);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/

		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
		query.whereEqualTo("vqno",num3);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.d("vque", "The getFirst request failed.");
		    } else {
		      Log.d("vque", "Retrieved the object.");
				objectId=object.getObjectId();
				Toast.makeText(getApplicationContext(),"modified objectid "+objectId,Toast.LENGTH_LONG).show();
		      questiondata=object.getString("vque");
				//objectId=object.getString("objectId");
				//Toast.makeText(getApplicationContext(),questiondata,Toast.LENGTH_LONG).show();
		      qquestn = (TextView) findViewById(R.id.questn);
		      qquestn.setText(questiondata);
		      String option1=object.getString("vopt1");
		       oop1 = (TextView) findViewById(R.id.op1);
		      oop1.setText(option1); 
		      String option2=object.getString("vopt2");
		       oop2 = (TextView) findViewById(R.id.op2);
		      oop2.setText(option2); 
		      String option3=object.getString("vopt3");
		       oop3 = (TextView) findViewById(R.id.op3);
		      oop3.setText(option3);
		      String option4=object.getString("vopt4");
		       oop4 = (TextView) findViewById(R.id.op4);
		      oop4.setText(option4);
		      int righta=object.getInt("vrightans");
		      final TextView ccorrect = (TextView) findViewById(R.id.correct);
		     ccorrect.setText(Integer.toString(righta));
		     
		    }
		  }
		});
		
	
		
		vvqcancel = (Button)findViewById(R.id.vqcancel);
		vvqcancel.setVisibility(View.INVISIBLE);
		vvqcancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(ViewV.this,HomeAdmin.class);
					startActivity(indexIntent);	
			}
		});

		
		
		ddelete = (Button)findViewById(R.id.delete);
		ddelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (p != null)
					showPopup(ViewV.this, p);
			}


		});


		nnext = (Button)findViewById(R.id.next);
		nnext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				num3++;
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				query.whereEqualTo("vqno",num3);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("vque", "The getFirst request failed.");
				    } else {
				      Log.d("vque", "Retrieved the object.");
						//objectId=object.getString("objectId");
						objectId=object.getObjectId();
				       questiondata=object.getString("vque");
				      final TextView qquestn = (TextView) findViewById(R.id.questn);
				      qquestn.setText(questiondata);
				      String option1=object.getString("vopt1");
				      final TextView oop1 = (TextView) findViewById(R.id.op1);
				      oop1.setText(option1); 
				      String option2=object.getString("vopt2");
				      final TextView oop2 = (TextView) findViewById(R.id.op2);
				      oop2.setText(option2); 
				      String option3=object.getString("vopt3");
				      final TextView oop3 = (TextView) findViewById(R.id.op3);
				      oop3.setText(option3);
				      String option4=object.getString("vopt4");
				      final TextView oop4 = (TextView) findViewById(R.id.op4);
				      oop4.setText(option4);
				      int righta=object.getInt("vrightans");
				      final TextView ccorrect = (TextView) findViewById(R.id.correct);
				     ccorrect.setText(Integer.toString(righta));
				     
				    }
				  }
				});
				
				
				

			}
		});
		
		
		
		pprev = (Button)findViewById(R.id.prev);
		pprev.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				num3--;
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");

				query.whereEqualTo("vqno",num3);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				  public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.d("vque", "The getFirst request failed.");
				    } else {
				      Log.d("vque", "Retrieved the object.");
						objectId=object.getObjectId();
						questiondata=object.getString("vque");
						//objectId=object.getString("objectId");
				      final TextView qquestn = (TextView) findViewById(R.id.questn);
				      qquestn.setText(questiondata);
				      String option1=object.getString("vopt1");
				      final TextView oop1 = (TextView) findViewById(R.id.op1);
				      oop1.setText(option1); 
				      String option2=object.getString("vopt2");
				      final TextView oop2 = (TextView) findViewById(R.id.op2);
				      oop2.setText(option2); 
				      String option3=object.getString("vopt3");
				      final TextView oop3 = (TextView) findViewById(R.id.op3);
				      oop3.setText(option3);
				      String option4=object.getString("vopt4");
				      final TextView oop4 = (TextView) findViewById(R.id.op4);
				      oop4.setText(option4);
				      int righta=object.getInt("vrightans");
				      final TextView ccorrect = (TextView) findViewById(R.id.correct);
				     ccorrect.setText(Integer.toString(righta));
				     
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
				Intent i=new Intent(ViewV.this,HomeAdmin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, paramMenu);
		return true;
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		int[] location = new int[2];
		Button button = (Button) findViewById(R.id.delete);
		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		button.getLocationOnScreen(location);
		//Initialize the Point with x, and y positions
		p = new Point();
		p.x = 100;
		p.y = 700;
	}


	private void showPopup(final Activity context, Point p) {
		Toast.makeText(getApplicationContext(),"inside showpopup",Toast.LENGTH_LONG).show();
		int popupWidth = 1200;
		int popupHeight = 550;
		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);
		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);
		// Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
		int OFFSET_X = 30;
		int OFFSET_Y = 30;
		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());
		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
		// Getting a reference to Close button, and close the popup when clicked.
		Button close = (Button) layout.findViewById(R.id.bno);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});
		Button yes=(Button)layout.findViewById(R.id.byes);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(),"inside yes!",Toast.LENGTH_LONG).show();

				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");

                Toast.makeText(getApplicationContext(),questiondata,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),objectId,Toast.LENGTH_LONG).show();
				query.whereEqualTo("objectId", objectId);
                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object == null) {
                            Log.d("vque", "The getFirst request failed.");

                        } else {
                            try {
                                object.delete();
                                object.saveInBackground();


                                Toast.makeText(getApplicationContext(), "Deleted Successuly.", Toast.LENGTH_SHORT).show();


                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
				popup.dismiss();
				num3++;
				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vex");
				query1.whereEqualTo("vqno",num3);
				query1.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("vque", "The getFirst request failed.");
						} else {
							Log.d("vque", "Retrieved the object.");
							//objectId=object.getString("objectId");
							objectId=object.getObjectId();
							questiondata=object.getString("vque");
							final TextView qquestn = (TextView) findViewById(R.id.questn);
							qquestn.setText(questiondata);
							String option1=object.getString("vopt1");
							final TextView oop1 = (TextView) findViewById(R.id.op1);
							oop1.setText(option1);
							String option2=object.getString("vopt2");
							final TextView oop2 = (TextView) findViewById(R.id.op2);
							oop2.setText(option2);
							String option3=object.getString("vopt3");
							final TextView oop3 = (TextView) findViewById(R.id.op3);
							oop3.setText(option3);
							String option4=object.getString("vopt4");
							final TextView oop4 = (TextView) findViewById(R.id.op4);
							oop4.setText(option4);
							int righta=object.getInt("vrightans");
							final TextView ccorrect = (TextView) findViewById(R.id.correct);
							ccorrect.setText(Integer.toString(righta));

						}
					}
				});
	}
		});

	
	
	
	

} }
