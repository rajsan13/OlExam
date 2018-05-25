package com.example.Aphexams;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;

import com.parse.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;


public class StudentLogin extends Activity {
	Button sloginbutton;
	Button scancelbutton,register;
	EditText sid;
	EditText spword;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_student_login);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		sid = (EditText) findViewById(R.id.studentid);
		spword = (EditText) findViewById(R.id.studentpword);


		sloginbutton = (Button)findViewById(R.id.slbutton1);
		sloginbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				ParseQuery<ParseObject> query = ParseQuery.getQuery("studAuth");
				query.whereEqualTo("StudUserName",sid.getText().toString());
				query.whereEqualTo("StudPassword",spword.getText().toString());

				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object != null) {
							final ProgressDialog dlg = new ProgressDialog(StudentLogin.this);
							dlg.setTitle("Please wait.");
							dlg.setMessage("Logging in.  Please wait.");
							dlg.show();
							Intent indexIntent=new Intent(StudentLogin.this,HomeStudent.class);
							indexIntent.putExtra("studentInvoking",sid.getText().toString());
							startActivity(indexIntent);
						}
						else {
							Toast.makeText(StudentLogin.this, "Error: Check out data!!!", Toast.LENGTH_SHORT).show();
						}

					}
				});

















			}
		});

		register=(Button)findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i =new Intent(getApplicationContext(),StudentReg.class);
				startActivity(i);
			}
		});

		scancelbutton = (Button)findViewById(R.id.slbutton2);
		scancelbutton.setVisibility(View.INVISIBLE);
		scancelbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent indexIntent=new Intent(StudentLogin.this,MainActivity.class);
				startActivity(indexIntent);
			}
		});



	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(StudentLogin.this,MainActivity.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private ParseObject ParseObject(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
