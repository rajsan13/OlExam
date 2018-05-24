package com.example.Aphexams;

import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.SignUpCallback;
import android.app.ActionBar;
import android.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;

public class StudentReg extends Activity {
	Button srsubmitbutton;
	Button srcancelbutton;
	EditText sruname1;
	EditText srpword1;
	EditText srconfpword1;
	EditText srphn1;
	EditText sremail;
	EditText srname;
	String a="Invalid";
	String b="blank password";
	String c="Mismatch";
	
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
				
				// Validate the sign up data
		        boolean validationError = false;
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
		          Toast.makeText(StudentReg.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
		              .show();
		          return;
		        }

		        
		        
		        ParseObject studAuth = new ParseObject("studAuth");
		        studAuth.put("StudUserName",sruname1.getText().toString());
		        studAuth.put("StudPassword",srpword1.getText().toString());
		        studAuth.put("StudPhnNo",srphn1.getText().toString());
				studAuth.put("StudEmId",sremail.getText().toString());
				studAuth.put("StudName",srname.getText().toString());
		        studAuth.saveInBackground();
		        // Set up a progress dialog
		        final ProgressDialog dlg = new ProgressDialog(StudentReg.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Signing up.  Please wait.");
		        dlg.show();
		        
		       
		        

	
		              // Start an intent for the dispatch activity
		              Intent intent = new Intent(StudentReg.this, SucStudReg.class);
		              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		              startActivity(intent);
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
		
		}



