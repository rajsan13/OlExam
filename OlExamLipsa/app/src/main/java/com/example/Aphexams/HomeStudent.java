package com.example.Aphexams;



import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.view.MenuItem;

public class HomeStudent extends Activity {
	Button hsstartbutton;
	Button hseditbutton;
	Button hschangepwordbutton;
	Button hscancelbutton;
	private RadioGroup radioGroup;
	private RadioButton hsqradio1;
	private RadioButton hsvradio2;
     
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_student);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
        radioGroup = (RadioGroup)findViewById(R.id.group1);
        hsqradio1 = (RadioButton)findViewById(R.id.qradio1);
        hsvradio2= (RadioButton)findViewById(R.id.vradio2);
	
        
        Intent intentIndex = getIntent(); // gets the previously created intent
        final String studname = intentIndex.getStringExtra("studentInvoking"); 
        final TextView tw= (TextView)findViewById(R.id.textView2);
        tw.setText("Hello "+studname);
        
        
        hsstartbutton = (Button)findViewById(R.id.hsstart);
        hsstartbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				if(R.id.qradio1==radioGroup.getCheckedRadioButtonId())
				{
					final ProgressDialog dlg = new ProgressDialog(HomeStudent.this);
			        dlg.setTitle("Please wait.");
			        dlg.setMessage("Processing request.  Navigating to quant questions.  Please wait.");
			        dlg.show();
					Intent indexIntent=new Intent(HomeStudent.this,QTestStart.class);
					indexIntent.putExtra("studentInvoking",studname);
					indexIntent.putExtra("tillnow","");
					indexIntent.putExtra("quanto","0");
					indexIntent.putExtra("verbo","0");
					startActivity(indexIntent);
				}
				else
				{
					final ProgressDialog dlg = new ProgressDialog(HomeStudent.this);
			        dlg.setTitle("Please wait.");
			        dlg.setMessage("Processing the request. Navigating to verbal questions. Please wait.");
			        dlg.show();
					Intent indexIntent=new Intent(HomeStudent.this,VTestStart.class);
					indexIntent.putExtra("studentInvoking",studname);
					indexIntent.putExtra("tillnow","");
					indexIntent.putExtra("quanto","0");
					indexIntent.putExtra("verbo","0");
					startActivity(indexIntent);
				}
			}
		});

        hschangepwordbutton = (Button)findViewById(R.id.hschangepword);
        hschangepwordbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeStudent.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating to change password.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeStudent.this,StudentChangePword.class);
					startActivity(indexIntent);	
			}
		});
		
		hscancelbutton = (Button)findViewById(R.id.hscancel);
		hscancelbutton.setVisibility(View.INVISIBLE);
		hscancelbutton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				final ProgressDialog dlg = new ProgressDialog(HomeStudent.this);
		        dlg.setTitle("Please wait.");
		        dlg.setMessage("Processing request.  Navigating back.  Please wait.");
		        dlg.show();
				Intent indexIntent=new Intent(HomeStudent.this,MainActivity.class);
					startActivity(indexIntent);	
			}
		});
		
		
	

}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; go home
				Intent i=new Intent(HomeStudent.this,StudentLogin.class);
				startActivity(i);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
    
