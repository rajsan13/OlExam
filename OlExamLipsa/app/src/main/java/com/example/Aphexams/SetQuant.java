package com.example.Aphexams;
import com.parse.*;
import java.io.*;
import java.util.*;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;

public class SetQuant extends Activity{
	
	public static int  num=1;
	int count=5;
	Button bback,banother,bsub;
	TextView top1,top2,top3,top4,tque,tcorrect,editText;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_quant);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		/*Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("QBQyVxRaYxbMEtpU31gZc0jbp7zoXD9WtoqtT1nd")
				.clientKey("IZyyYsHU5dvxaoFNQ6GbTY0by1uFWgUgFI5xiU1K")
				.server("https://parseapi.back4app.com/")
				.build()
		);*/
		tque = (EditText)findViewById(R.id.editText1);
		top1 = (EditText)findViewById(R.id.editText2);
		top2 = (EditText)findViewById(R.id.editText3);
		top3 = (EditText)findViewById(R.id.editText4);
		top4 = (EditText)findViewById(R.id.editText5);
		tcorrect = (EditText)findViewById(R.id.editText6);
		
		bsub = (Button)findViewById(R.id.button1);
		bsub.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				ParseObject exams = new ParseObject("exams");
				exams.put("qno", num);
				exams.put("que", tque.getText().toString());
				exams.put("opt1", top1.getText().toString());
				exams.put("opt2", top2.getText().toString());
				exams.put("opt3", top3.getText().toString());
				exams.put("opt4", top4.getText().toString());
				exams.put("Extra_Option", editText.getText().toString());
				exams.put("rightans",Integer.parseInt(tcorrect.getText().toString()));
				exams.saveInBackground();	
				num++;
				tque.setText("");
				top1.setText("");
				top2.setText("");
				top3.setText("");
				top4.setText("");
				editText.setText("");
				tcorrect.setText("");
				
			}
		});
		
		
		banother = (Button)findViewById(R.id.button2);
		//banother.setVisibility(View.INVISIBLE);
		banother.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if(count==5)
				{
					LinearLayout linearLayout= (LinearLayout) findViewById(R.id.llq);
					editText = new EditText(getApplicationContext());
					editText.setHint("Option "+count);
					editText.setTextColor(Color.BLACK);
					editText.setHintTextColor(Color.GRAY);
					editText.setId(R.id.edit_text_hello);//string id value set in values->ids.xml
					editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					if (linearLayout != null) {
						linearLayout.addView(editText);
					}
					Toast.makeText(getApplicationContext(),""+count,Toast.LENGTH_LONG).show();
					count++;
				}

				
			}
		});
		
		
		
		
		
		
		bback = (Button)findViewById(R.id.button3);
		bback.setVisibility(View.INVISIBLE);
		bback.setVisibility(View.INVISIBLE);
		bback.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent indexIntent=new Intent(SetQuant.this,HomeAdmin.class);
					startActivity(indexIntent);	
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(SetQuant.this,SetQues.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}


