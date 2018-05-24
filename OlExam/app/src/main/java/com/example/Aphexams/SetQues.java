package com.example.Aphexams;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

public class SetQues extends Activity{
	
	Button bgo;
	public static int i;
	String selectx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_ques);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		int id = ((RadioGroup)findViewById( R.id.question_set_type)).getCheckedRadioButtonId();
		if(id==R.id.radioQuant){i=1;}else if(id==R.id.radioVerb){i=2;}
		

		bgo = (Button)findViewById(R.id.go);
		bgo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int id = ((RadioGroup)findViewById( R.id.question_set_type)).getCheckedRadioButtonId();
				if(id==R.id.radioQuant){i=1;}else if(id==R.id.radioVerb){i=2;}
				if(i==1)
				{
				Intent indexIntent=new Intent(SetQues.this,SetQuant.class);
					startActivity(indexIntent);
				}else if(i==2)
				{
					Intent intent=new Intent(SetQues.this,SetVerb.class);
				startActivity(intent);
				}
				}
				
			
		});
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(SetQues.this,HomeAdmin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}


