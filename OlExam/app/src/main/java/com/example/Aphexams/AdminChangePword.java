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

public class AdminChangePword extends Activity{
	Button bscpsubmit,bscpcancel;
	EditText sruname1;
	EditText srpword1;
	EditText srconfpword1;
	EditText pno;
	String a="Invalid";
	String b="blank password";
	String c="Mismatch";
	String PhoneeNumber;
	int k=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_c_pword);
		sruname1 = (EditText) findViewById(R.id.ediText1);

		srpword1 = (EditText) findViewById(R.id.ediText2);
		srconfpword1 = (EditText)findViewById(R.id.ediText3);
		//pno=(EditText)findViewById((R.id.editText));

		bscpcancel = (Button)findViewById(R.id.acp2);
		bscpcancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent indexIntent=new Intent(AdminChangePword.this,HomeStudent.class);
				startActivity(indexIntent);
			}
		});


		bscpsubmit = (Button)findViewById(R.id.acp1);
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
												  Toast.makeText(AdminChangePword.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
														  .show();
												  return;
											  }

											  ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
											  query.whereEqualTo("username",sruname1.getText().toString());
											  query.getFirstInBackground(new GetCallback<ParseObject>() {
												  public void done(ParseObject object, ParseException e) {
													  if (object == null) {
														 // Log.d("Studname", "The getFirst request failed.");
														 /* ParseObject res = new ParseObject("User");
														  res.put("Studname",names);
														  res.put("MARKS",marks);
														  res.saveInBackground();*/
													  } else {
														  //Log.d("Studname", "Retrieved the object.");
														  object.put("password",srpword1.getText().toString());
														  Toast.makeText(AdminChangePword.this,"Success",Toast.LENGTH_LONG).show();
														  object.saveInBackground();
													  }
												  }
											  });

											  //Start an intent for the dispatch activity
											 /* Intent intent = new Intent(AdminChangePword.this, SucAdminReg.class);
											  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
											  startActivity(intent);*/
										  }
									  }
		);

		//if(k>0){
//			Thread.sleep(1000)






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




