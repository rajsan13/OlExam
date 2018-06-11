package com.example.Aphexams;
import com.parse.*;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

import java.util.List;

public class SetVerb extends Activity{

	public static int  num1=1;
	int count=5,cnt=0;
	static int sequence_gen;
	Button vbback,vbanother,vbsub,vdelete,bsetimage1;
	TextView vtop1,vtop2,vtop3,vtop4,vtque,vtcorrect,editText,invques;
	ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_verb);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
	/*	Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/

		vtque = (EditText)findViewById(R.id.editText1);
		vtop1 = (EditText)findViewById(R.id.editText2);
		vtop2 = (EditText)findViewById(R.id.editText3);
		vtop3 = (EditText)findViewById(R.id.editText4);
		vtop4 = (EditText)findViewById(R.id.editText5);
		vtcorrect = (EditText)findViewById(R.id.editText6);
		//invques=(TextView)findViewById(R.id.invisibleqno);

		vbsub = (Button)findViewById(R.id.button1);
		vbsub.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//fetching the number of rows
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");
				//query.whereEqualTo("rightans",Integer.parseInt(cor));
				query.findInBackground(new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if (e == null) {
							Log.d("que", "The getFirst request failed.");

							sequence_gen=objects.size();

							ParseObject vex = new ParseObject("Vex");
							//Toast.makeText(SetVerb.this,"out side done "+invques.getText().toString(),Toast.LENGTH_LONG).show();
							vex.put("vqno", ++sequence_gen);
							vex.put("vque", vtque.getText().toString());
							vex.put("vopt1", vtop1.getText().toString());
							vex.put("vopt2", vtop2.getText().toString());
							vex.put("vopt3", vtop3.getText().toString());
							vex.put("vopt4", vtop4.getText().toString());
							vex.put("Extra_Option", vtop4.getText().toString());
							vex.put("vrightans", Integer.parseInt(vtcorrect.getText().toString()));
							vex.saveInBackground();

							vtque.setText("");
							vtop1.setText("");
							vtop2.setText("");
							vtop3.setText("");
							vtop4.setText("");
							//	editText.setText("");

							vtcorrect.setText("");


							Toast.makeText(getApplicationContext(),Integer.toString(sequence_gen),Toast.LENGTH_LONG).show();
						} else {
							Log.d("que", "Retrieved the object.");



						}
					}


				});



			}
		});
		bsetimage1 = (Button)findViewById(R.id.button8);
		bsetimage1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SetVerb.this);
				//prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
				SharedPreferences.Editor editor= prefs.edit();
				editor.putString("questno",Integer.toString(num1));
				editor.commit();
				Intent indexIntent=new Intent(SetVerb.this,UploadImage.class);
				startActivity(indexIntent);
			}
		});

		vbanother = (Button)findViewById(R.id.button2);
		vbanother.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(count==5)
				{
					LinearLayout linearLayout= (LinearLayout) findViewById(R.id.llv);
					editText = new EditText(getApplicationContext());
					editText.setHint("Option "+count);
					editText.setTextColor(Color.GRAY);
					editText.setHintTextColor(Color.GRAY);
					editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					if (linearLayout != null) {
						linearLayout.addView(editText);
					}
					Toast.makeText(getApplicationContext(),""+count,Toast.LENGTH_LONG).show();
					count++;
				/*	String c=PreferenceManager.getDefaultSharedPreferences(SetVerb.this).getString("image", "defaultStringIfNothingFound");
					mImageView=(ImageView)findViewById(R.id.image_view);
					mImageView.setImageBitmap(StringToBitMap(c));*/
				}

			}
		});

		/*vdelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});*/






		vbback = (Button)findViewById(R.id.button3);
		vbback.setVisibility(View.INVISIBLE);
		vbback.setVisibility(View.INVISIBLE);
		vbback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent indexIntent=new Intent(SetVerb.this,HomeAdmin.class);
				startActivity(indexIntent);
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(SetVerb.this,HomeAdmin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	public Bitmap StringToBitMap(String encodedString){
		try {
			byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		} catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
}





