package com.example.Aphexams;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.*;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import android.app.ActionBar;
import android.view.MenuItem;


public class AdminLogin extends Activity {
	Button aloginbutton;
	Button acancelbutton;
	EditText aid;
	EditText apword;
	String a="error";
	String b="  Blank user name field";
	String c="   Blank password field";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_admin_login);
		//Parse.initialize(this, "eX31t72OF1l2SfT72YlLNkBiCiMwRGCf6S8QGMHB", "r70qpWRJ6IMNgTn7YW2dLZUFMmvTn5GQRfNQcwsd");


		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		aid = (EditText) findViewById(R.id.adminid);
		apword = (EditText) findViewById(R.id.adminpword);
		
		
		
		
		
		aloginbutton = (Button)findViewById(R.id.albutton1);
		aloginbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				// Validate the log in data
		        boolean validationError = false;
		        StringBuilder validationErrorMessage =
		            new StringBuilder(a);
		        if (isEmpty(aid)) {
		          validationError = true;
		          validationErrorMessage.append(b);
		        }
		        if (isEmpty(apword)) {
		          if (validationError) {
		            validationErrorMessage.append(a);
		          }
		          validationError = true;
		          validationErrorMessage.append(c);
		        }
		        validationErrorMessage.append(a);

		        // If there is a validation error, display the error
		        if (validationError) {
		          Toast.makeText(AdminLogin.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
		              .show();
		          return;
		        }

		        // Set up a progress dialog
		        final ProgressDialog dlg = new ProgressDialog(AdminLogin.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Logging in.  Please wait.");
		        dlg.show();
		        // Call the Parse login method
		        ParseUser.logInInBackground(aid.getText().toString(), apword.getText()
		            .toString(), new LogInCallback() {

		          @Override
		          public void done(ParseUser user, ParseException e) {
		            dlg.dismiss();
		            if (e != null) {
		              // Show the error message
		              Toast.makeText(AdminLogin.this, e.getMessage(), Toast.LENGTH_LONG).show();
		            } else {
		              // Start an intent for the dispatch activity
		              Intent intent = new Intent(AdminLogin.this, HomeAdmin.class);
		              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		              startActivity(intent);
		            }
		          }
		        });
				
			}
		});
		
		acancelbutton = (Button)findViewById(R.id.albutton2);
		acancelbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(AdminLogin.this,MainActivity.class);
					startActivity(indexIntent);	
			}
		});
		
		
		
		
}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(AdminLogin.this,MainActivity.class);
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
}