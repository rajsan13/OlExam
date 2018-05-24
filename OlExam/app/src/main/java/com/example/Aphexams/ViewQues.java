package com.example.Aphexams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

public class ViewQues extends Activity{
	
	Button bgo,bso;
	public static int i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_ques);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		
		
		bgo = (Button)findViewById(R.id.button1);
		bgo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ViewQues.this,ViewQ.class);
				startActivity(intent);
				}
				
			
		});
		bso = (Button)findViewById(R.id.button2);
		bso.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ViewQues.this,ViewV.class);
				startActivity(intent);
				}
				
			
		});
		
		
		
		
		
		
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(ViewQues.this,HomeAdmin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}


