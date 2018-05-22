package com.example.Aphexams;
import android.app.Application;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;


public class AdminReg extends Activity{
	Button arsubmitbutton;
	Button arcancelbutton;
	EditText aruname1;
	EditText arpword1;
	EditText arconfpword1;
	EditText arphn1;
	String a="Invalid";
	String b="blank password";
	String c="Mismatch";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_admin_registration);
		//Parse.initialize(this,"QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd","IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K");
		//Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
		//ParseInstallation.getCurrentInstallation().saveInBackground();
		/*Parse.initialize(new Parse.Configuration.Builder(AdminReg.this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/

		//Parse.initialize(this,"QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd","IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K");

		aruname1 = (EditText) findViewById(R.id.aruname);
		arpword1 = (EditText) findViewById(R.id.arpword);
		arconfpword1 = (EditText)findViewById(R.id.arconfpword);
		
		 
			 
			 
		
		arsubmitbutton = (Button)findViewById(R.id.arsubmit);
		arsubmitbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				// Validate the sign up data
		        boolean validationError = false;
		        StringBuilder validationErrorMessage =
		            new StringBuilder(a);
		        if (isEmpty(aruname1)) {
		          validationError = true;
		          validationErrorMessage.append(a);
		        }
		        if (isEmpty(arpword1)) {
		          if (validationError) {
		            validationErrorMessage.append(a);
		          }
		          validationError = true;
		          validationErrorMessage.append(b);
		        }
		        if (!isMatching(arpword1, arconfpword1)) {
		          if (validationError) {
		            validationErrorMessage.append(a);
		          }
		          validationError = true;
		          validationErrorMessage.append(c);
		        }
		        validationErrorMessage.append(a);

		        // If there is a validation error, display the error
		        if (validationError) {
		          Toast.makeText(AdminReg.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
		              .show();
		          return;
		        }

		        // Set up a progress dialog
		        final ProgressDialog dlg = new ProgressDialog(AdminReg.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Signing up.  Please wait.");
		        dlg.show();

		        // Set up a new Parse user
		        ParseUser user = new ParseUser();
		        user.setUsername(aruname1.getText().toString());
		        user.setPassword(arpword1.getText().toString());
		        // Call the Parse signup method
		        user.signUpInBackground(new SignUpCallback() {

		          @Override
		          public void done(ParseException e) {
		            dlg.dismiss();
		            if (e != null) {
		              // Show the error message
		              Toast.makeText(AdminReg.this, e.getMessage(), Toast.LENGTH_LONG).show();
		            } else {
		              // Start an intent for the dispatch activity
		              Intent intent = new Intent(AdminReg.this, SucAdminReg.class);
		              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		              startActivity(intent);
		            }
		          }
		        });
		      }
				    
				
				
			
		});

		arcancelbutton = (Button)findViewById(R.id.arcancel);
		arcancelbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(AdminReg.this,MainActivity.class);
					startActivity(indexIntent);	
			}
		});
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

