package com.example.Aphexams;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.*;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ViewStudent extends Activity{
	TextView tvqmarks,tvvmarks,tvtotal,tvpno,tvsname,tvemailid;
	String total,pno,sname,emailid,q,v,t,EnterText;
	int qmark,vmark;
	EditText tsid;
	Button vscancelbutton;
	String studentid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_students);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

        Bundle ob=getIntent().getExtras();
		studentid=ob.getString("studId");
		//Toast.makeText(getApplicationContext(),studentid,Toast.LENGTH_LONG).show();

		  tvsname = (TextView) findViewById(R.id.tvdynsname);
		 tvemailid = (TextView) findViewById(R.id.tvdynemailid);
		  tvpno = (TextView) findViewById(R.id.tvdynpno);
		  tvqmarks = (TextView) findViewById(R.id.tvdynqmarks);
		  tvvmarks = (TextView) findViewById(R.id.tvdynvmarks);
		  tvtotal = (TextView) findViewById(R.id.tvdyntotal);


		ParseQuery<ParseObject> query = ParseQuery.getQuery("studAuth");
		query.whereEqualTo("StudUserName",studentid);


		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("vque", "The getFirst request failed.");
				} else {
					Log.d("vque", "Retrieved the object.");
					 sname=object.getString("StudName");
					 pno=object.getString("StudPhnNo");
					 emailid=object.getString("StudEmId");




					/*String qmark=object.getString("vque");
					String vmark=object.getString("vque");*/
					tvsname.setText(sname);
					tvpno.setText(pno);
					tvemailid.setText(emailid);
					//Toast.makeText(getApplicationContext(),sname+"  "+pno+"  "+emailid,Toast.LENGTH_LONG).show();


				}
			}
		});

		/**/

		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("results");
		query1.whereEqualTo("Studname",studentid);

		query1.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("vque", "The getFirst request failed.");
				} else {
					Log.d("vque", "Retrieved the object.");

					 qmark=object.getNumber("quantmarks").intValue();
					 vmark=object.getNumber("verbmarks").intValue();



					tvqmarks.setText(qmark);
					tvvmarks.setText(vmark);

						tvtotal.setText(qmark+vmark);


					/*Toast.makeText(getApplicationContext(),qmark,Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(),vmark,Toast.LENGTH_LONG).show();*/



				}
			}
		});



	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(ViewStudent.this,AdminShowStudentId.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
