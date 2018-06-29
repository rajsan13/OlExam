package com.example.Aphexams;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AdminChangePword extends Activity{
	Button acpsubmit,acpcancel;
	SharedPreferences settings;
	EditText et1,et2,et3;
	String userid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_c_pword);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		settings= PreferenceManager.getDefaultSharedPreferences(AdminChangePword.this);
		userid=settings.getString("AdminUserName","");
		acpsubmit=(Button)findViewById(R.id.acp1);
		acpsubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ParseQuery<ParseObject> qry = ParseQuery.getQuery("user");
				//Toast.makeText(getApplicationContext(),"kab ayega list value"+randint,Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(),"num5 "+num5,Toast.LENGTH_LONG).show();
				qry.whereEqualTo("username",userid);
				qry.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (object == null) {
							Log.d("vque", "The getFirst request failed.");
						} else {
							Log.d("vque", "Retrieved the object.");
							String questiondata=object.getString("vque");
							final TextView qquestn = (TextView) findViewById(R.id.textView2);
							qquestn.setText(questiondata);

						}
					}

				});

			}
		});
		
		acpcancel = (Button)findViewById(R.id.acp2);
		acpcancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(AdminChangePword.this, com.example.Aphexams.HomeAdmin.class);
					startActivity(indexIntent);	
			}
		});
		
		
		
		
}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(AdminChangePword.this,HomeAdmin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}