package com.example.Aphexams;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

public class AdminChangePword extends Activity{
	Button acpsubmit,acpcancel;
	EditText et1,et2,et3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_c_pword);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
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