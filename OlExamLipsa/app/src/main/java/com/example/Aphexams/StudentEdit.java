package com.example.Aphexams;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.*;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StudentEdit extends Activity{
	Button srsubmitbutton;
	Button srcancelbutton;
	EditText sruname1;
	EditText srpword1;
	EditText sremail;
	EditText srphn1;
	EditText srname;
	String a="Invalid";
	String b="blank password";
	String c="Mismatch";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_edit);
		

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		sruname1 = (EditText) findViewById(R.id.sruname);
		srpword1 = (EditText) findViewById(R.id.srpword);
		sremail = (EditText)findViewById(R.id.sremail1);
		srphn1 = (EditText)findViewById(R.id.srphn);
		srname=(EditText)findViewById(R.id.srname1);
		SharedPreferences settings;
		settings = PreferenceManager.getDefaultSharedPreferences(StudentEdit.this);

		String username = settings.getString("username", "");
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
					sruname1.setText(UserName);
					String Password = object.getString("StudPassword");
					srpword1.setText(Password);
					String PhoneNumber=object.getString("StudPhnNo");
					srphn1.setText(PhoneNumber);
					String Email = object.getString("StudEmId");
					sremail.setText(Email);
					String name=object.getString("StudName");
					srname.setText(name);

					//String PhoneNumber=object.getString("StudPhnNo");
					// srphn1.setText(PhoneNumber);
					//k++;
					//cp();
				}
			}
		});



		srsubmitbutton = (Button)findViewById(R.id.srsubmit);
		srsubmitbutton.setOnClickListener(new OnClickListener() {

											  public void onClick(View v){

												  // Validate the sign up data
											/*	  boolean validationError = false;
												  StringBuilder validationErrorMessage =
														  new StringBuilder(a);
												  if (isEmpty(sruname1)) {
													  validationError = true;
													  validationErrorMessage.append(a);
												  }
												  if (isEmpty(srpword1)||isEmpty(srphn1)) {
													  if (validationError) {
														  validationErrorMessage.append(a);
													  }
													  validationError = true;
													  validationErrorMessage.append(b);
												  }
												  if (!isMatching(srpword1, srconfpword1)) {
													  if (validationError) {
														  validationErrorMessage.append(a);
													  }
													  validationError = true;
													  validationErrorMessage.append(c);
												  }
												  validationErrorMessage.append(a);

												  // If there is a validation error, display the error
												  if (validationError) {
													  Toast.makeText(StudentEdit.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
															  .show();
													  return;
												  }*/


												 /* ParseObject studAuth = new ParseObject("studAuth");
												  studAuth.put("StudUserName",sruname1.getText().toString());
												  studAuth.put("StudPassword",srpword1.getText().toString());
												  studAuth.put("StudPhnNo",srphn1.getText().toString());
												  studAuth.saveInBackground();
												  // Set up a progress dialog
												  final ProgressDialog dlg = new ProgressDialog(StudentEdit.this);
												  dlg.setTitle("Please wait.");
												  dlg.setMessage("Signing up.  Please wait.");
												  dlg.show();





												  // Start an intent for the dispatch activity
												  Intent intent = new Intent(StudentEdit.this, SucStudReg.class);
												  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
												  startActivity(intent);*/
												  ParseQuery<ParseObject> query1 = ParseQuery.getQuery("studAuth");
												  query1.whereEqualTo("StudUserName",sruname1.getText().toString());
												  query1.getFirstInBackground(new GetCallback<ParseObject>() {
													  public void done(ParseObject object, ParseException e) {
														  if (object == null) {
															  //Log.d("StudPhnNo", "The getFirst request failed.");
															  // PhoneeNumber="NULL";
														  } else {
															  //Log.d("StudPhnNo", "Retrieved the object.");
															  object.deleteInBackground();

														  }
													  }
												  });

												  ParseObject studAuth = new ParseObject("studAuth");
												  studAuth.put("StudUserName",sruname1.getText().toString());
												  studAuth.put("StudPassword",srpword1.getText().toString());
												  //studAuth.put("StudPassword",l);
												  studAuth.put("StudPhnNo",srphn1.getText().toString());
												  studAuth.put("StudEmId",sremail.getText().toString());
												  studAuth.put("StudName",srname.getText().toString());

												  studAuth.saveInBackground();
												  Toast.makeText(StudentEdit.this, "Edited Student details are successfully updated", Toast.LENGTH_SHORT).show();
											  }
										  }
		);





		srcancelbutton = (Button)findViewById(R.id.srcancel);
		srcancelbutton.setVisibility(View.INVISIBLE);
		srcancelbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent indexIntent=new Intent(StudentEdit.this,MainActivity.class);
				startActivity(indexIntent);
			}
		});


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(StudentEdit.this,MainActivity.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private boolean isEmpty(EditText etText) {
		if (etText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isMatching(EditText etText1, EditText etText2) {
		if (etText1.getText().toString().equals(etText2.getText().toString())) {
			return true;
		} else {
			return false;
		}
	}

}





