package com.example.Aphexams;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class StudentChangePword extends Activity{
	Button bscpsubmit,bscpcancel;
	EditText sruname1;
	EditText srpword1;
	EditText srconfpword1;
	EditText pno;
	EditText em;
	EditText na;
	String a="Invalid";
	String b="blank password";
	String c="Mismatch";
	String PhoneeNumber;
	int k=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_c_pword);
		sruname1 = (EditText) findViewById(R.id.editText1);

		srpword1 = (EditText) findViewById(R.id.editText2);
		srconfpword1 = (EditText)findViewById(R.id.editText3);
		pno=(EditText)findViewById((R.id.editText));
        em=(EditText)findViewById(R.id.editText4);
		na=(EditText)findViewById(R.id.editText5);
		bscpcancel = (Button)findViewById(R.id.scpcancel);
		bscpcancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent indexIntent=new Intent(StudentChangePword.this,HomeStudent.class);
				startActivity(indexIntent);
			}
		});


		bscpsubmit = (Button)findViewById(R.id.scpsubmit);
		bscpsubmit.setOnClickListener(new OnClickListener() {

										  public void onClick(View v){

											  // Validate the sign up data
											  boolean validationError = false;
											  StringBuilder validationErrorMessage =
													  new StringBuilder(a);
											  if (isEmpty(sruname1)) {
												  validationError = true;
												  validationErrorMessage.append(a);
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
												  Toast.makeText(StudentChangePword.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
														  .show();
												  return;
											  }

											  ParseQuery<ParseObject> query1 = ParseQuery.getQuery("studAuth");
											  query1.whereEqualTo("StudUserName",sruname1.getText().toString());
											  //query1.whereEqualTo("obj","gQX8gKIyYv");
											  //query1.
											  query1.getFirstInBackground(new GetCallback<ParseObject>() {
												  public void done(ParseObject object, ParseException e) {
													  if (object == null) {
														  //Log.d("StudPhnNo", "The getFirst request failed.");
														  // PhoneeNumber="NULL";
													  } else {
														  //Log.d("StudPhnNo", "Retrieved the object.");
														  String PhoneNumber=object.getString("StudPhnNo");
														  String Email=object.getString("StudEmId");
														  String Name=object.getString("StudName");
														  pno.setText(PhoneNumber);
														  em.setText(Email);
														  na.setText(Name);
														  k++;
														  cp();
													  }
												  }
											  });
											  //String l=pno.getText().toString();
											  // pno=(EditText)findViewById((R.id.editText));
											  //Toast.makeText(StudentChangePword.this,pno.getText().toString(),Toast.LENGTH_LONG).show();

										  }
									  }
		);

		//if(k>0){
//			Thread.sleep(1000)






	}
	public void cp()
	{
		//ParseObject x = ParseObject.deleteAll();
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
		studAuth.put("StudPhnNo",pno.getText().toString());
		studAuth.put("StudEmId",em.getText().toString());
		studAuth.put("StudName",na.getText().toString());
		studAuth.saveInBackground();
		// Set up a progress dialog
		final ProgressDialog dlg = new ProgressDialog(StudentChangePword.this);
		dlg.setTitle("Please wait.");
		dlg.setMessage("Signing up.  Please wait.");
		dlg.show();





		//Start an intent for the dispatch activity
		Intent intent = new Intent(StudentChangePword.this, SucStudReg.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
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




