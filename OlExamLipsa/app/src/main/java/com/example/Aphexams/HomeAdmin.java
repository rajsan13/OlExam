package com.example.Aphexams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

public class HomeAdmin extends Activity {
	private SharedPreferences Settings;
	Button viewstudentbutton;
	Button viewquebutton;
	Button setquebutton;
	Button changepwordbutton;
	Button hacancelbutton;
	Button setlimit;
	Button queslimit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_admin);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

	/*	queslimit=(Button)findViewById(R.id.bqueslimit);
		queslimit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i= new Intent(HomeAdmin.this,AdminSetQuesLimit.class);
				startActivity(i);
			}
		});*/

		viewstudentbutton = (Button)findViewById(R.id.viewstudent);
		viewstudentbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeAdmin.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating to student details.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeAdmin.this,AdminShowStudentId.class);
					startActivity(indexIntent);	
			}
		});
		
		viewquebutton = (Button)findViewById(R.id.viewque);
		viewquebutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeAdmin.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating to questions.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeAdmin.this,ViewV.class);
					startActivity(indexIntent);	
			}
		});
		
		setquebutton = (Button)findViewById(R.id.setque);
		setquebutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeAdmin.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating to set questions.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeAdmin.this,SetVerb.class);
					startActivity(indexIntent);	
			}
		});
		
		changepwordbutton = (Button)findViewById(R.id.changepword);
		changepwordbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeAdmin.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating to change password.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeAdmin.this,AdminChangePword.class);
					startActivity(indexIntent);	
			}
		});
		
		/*hacancelbutton = (Button)findViewById(R.id.hacancel);
		hacancelbutton.setVisibility(View.INVISIBLE);
		hacancelbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent indexIntent=new Intent(HomeAdmin.this,MainActivity.class);
					startActivity(indexIntent);	
			}
		});*/
		
		
		setlimit=(Button)findViewById(R.id.setlimit);
		setlimit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent indexIntent=new Intent(HomeAdmin.this,TimeLimit.class);
				startActivity(indexIntent);
			}
		});
		
}

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*getMenuInflater().inflate(R.menu.main, paramMenu);
		return true;*/
		MenuInflater inflater= new MenuInflater(this);
		inflater.inflate(R.menu.main,paramMenu);
		return super.onCreateOptionsMenu(paramMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(HomeAdmin.this,AdminLogin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			case R.id.logout:
				Toast.makeText(HomeAdmin.this,"Logout",Toast.LENGTH_LONG).show();
				Settings = this.getSharedPreferences("AdminUserName", Context.MODE_PRIVATE);
				Settings.edit().clear().commit();
				Intent intent=new Intent(HomeAdmin.this,AdminLogin.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
	
	

