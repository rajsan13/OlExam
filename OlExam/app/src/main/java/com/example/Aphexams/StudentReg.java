package com.example.Aphexams;

import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.SignUpCallback;
import android.app.ActionBar;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentReg extends Activity {
	Button srsubmitbutton;
	Button srcancelbutton;
	EditText sruname1;
	EditText srpword1;
	EditText srconfpword1;
	EditText srphn1;
	EditText sremail;
	EditText srname;
	String a="Invalid Username  \n";

	public static int h=0;
	 public static boolean validationError=false;
	 public static StringBuilder validationErrorMessage=new StringBuilder("");
	String b="blank password \n";
	String c="Mismatch in password and confirm password \n";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_student_registration);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		sruname1 = (EditText) findViewById(R.id.sruname);
		srpword1 = (EditText) findViewById(R.id.srpword);
		srconfpword1 = (EditText)findViewById(R.id.srconfpword);
		srphn1 = (EditText)findViewById(R.id.srphn);
		sremail=(EditText)findViewById(R.id.sremail) ;
		srname=(EditText) findViewById(R.id.srname);
		
		
		
		srsubmitbutton = (Button)findViewById(R.id.srsubmit);
		srsubmitbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v){
				validationError=false;
				h=0;
				validationErrorMessage=new StringBuilder("");
				final ProgressDialog dlg = new ProgressDialog(StudentReg.this);
				dlg.setTitle("Please wait.");
				dlg.setMessage("Signing up.  Please wait.");
				dlg.show();
				// Validate the sign up data
		        //boolean validationError = false;
		        //StringBuilder validationErrorMessage =
		          //  new StringBuilder(a);
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
				if(!isEmailValid(sremail.getText().toString()))
				{
					validationError=true;
					validationErrorMessage.append("invalid Email \n");
				}
				if (!PhoneNumberUtils.isGlobalPhoneNumber(srphn1.getText().toString()))
				{
					validationError=true;
					validationErrorMessage.append("Invalid PhoneNumber \n");
				}
				if(srpword1.getText().toString().length()<8 &&!isValidPassword(srpword1.getText().toString())){
					validationError=true;
					validationErrorMessage.append("Invalid Password \n");

				}


					//mofification
				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("studAuth");
				query1.addDescendingOrder("StudUserName");
				query1.orderByDescending("StudUserName");
				// List<ParseObject> objects = query1.find();
                //validationError=true;
				query1.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> list, ParseException e) {
						if (e == null) {
							outer: for (ParseObject o : list) {
								//results s = new Student();
                                if(!validationError){
								if(o.getString("StudUserName").equals(sruname1.getText().toString()))
								{
									h=1;
									validationError=true;
									//validationErrorMessage.append("inavlid Username");
									//srsubmitbutton.setText("hi");

									break outer;
								}}
								if(!validationError){
								if(o.getString("StudEmId").equals(sremail.getText().toString()))
								{
									validationError=true;
									h=2;
									break outer;
								}}
								if(!validationError) {
									if (o.getString("StudPhnNo").equals(srphn1.getText().toString())) {
										h = 3;
										validationError=true;
										break outer;
									}
								}
                            //  loop: System.out.println("hi");


							}
							// studentList is full here
							if(h==1) {validationError=true; validationErrorMessage.append("Username Already Exists \n");}
							if(h==2) {validationError=true; validationErrorMessage.append("Email Already Exists \n");}
							if(h==3) {validationError=true; validationErrorMessage.append("PhoneNumber Already Exists \n");}
							h=0;
						} else {
							System.out.println("hi1");
							//t.setText("hiii");
						}
						//validationErrorMessage.append(a);

						// If there is a validation error, display the error
						if (validationError) {
							Toast.makeText(StudentReg.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
									.show();


						}
						else{

						ParseObject studAuth = new ParseObject("studAuth");
						studAuth.put("StudUserName",sruname1.getText().toString());
						studAuth.put("StudPassword",srpword1.getText().toString());
						studAuth.put("StudPhnNo",srphn1.getText().toString());
						studAuth.put("StudEmId",sremail.getText().toString());
						studAuth.put("StudName",srname.getText().toString());
						studAuth.saveInBackground();
						// Set up a progress dialog
						/*final ProgressDialog dlg = new ProgressDialog(StudentReg.this);
						dlg.setTitle("Please wait.");
						dlg.setMessage("Signing up.  Please wait.");
						dlg.show();*/





						// Start an intent for the dispatch activity
						Intent intent = new Intent(StudentReg.this, SucStudReg.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);}
					}

				});

                h=0;
				//if(h==4) {validationError=true; validationErrorMessage.append("Username Already Exists");}



























		        
		        

			}  
		      }
				);



		
		
		srcancelbutton = (Button)findViewById(R.id.srcancel);
		srcancelbutton.setVisibility(View.INVISIBLE);
		srcancelbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(StudentReg.this,MainActivity.class);
					startActivity(indexIntent);	
			}
		});
		
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(StudentReg.this,MainActivity.class);
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
	public void cp(Boolean validationError){
		if (validationError) {
			Toast.makeText(StudentReg.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
					.show();
			return;
		}
	}

	public static boolean isEmailValid(String email) {

		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	public static boolean isValidPassword(final String password) {

		Pattern pattern;
		Matcher matcher;
		final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);

		return matcher.matches();

	}
		}



