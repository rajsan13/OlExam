package skaipal.example.com.tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends Activity {
    ImageButton register;
    Button sloginbutton;
    EditText sid;
    EditText spword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_student_login);

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Toast.makeText(MainActivity.this,Integer.toString(Global.check),Toast.LENGTH_LONG).show();
        if(Global.check==0){
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("AEEd95OiRpDIUS00U6KELyA9DQpgBD3WwM2Muti0")
                        .clientKey("2QyWYQiYh04eyc1JWOOlAp0sHvyaDgwkD2CGNpmt")
                        .server("https://parseapi.back4app.com/")
                        .build()
        );

            Global.check=1;}
       // Parse.initialize(this,"sD2HNlnrrDej7Bps3lTXYxjQv77eZXisfSwuy0M7","BYZiA82eedzgqFkEf2sZeSmYhVkGNZWxnRMUTOhf");
        sid = (EditText) findViewById(R.id.studentid);
        spword=(EditText) findViewById(R.id.studentpword);
        sloginbutton = (Button)findViewById(R.id.slbutton1);
        sloginbutton.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

                                                ParseQuery<ParseObject> query = ParseQuery.getQuery("empAuth");
                                                query.whereEqualTo("EmpID",sid.getText().toString().trim());
                                                query.whereEqualTo("EmpPassword",spword.getText().toString().trim());
                                                 Global.s=sid.getText().toString();
                                                query.getFirstInBackground(new GetCallback<ParseObject>() {
                                                    public void done(ParseObject object, ParseException e) {

                                                        if (object != null) {
                                                            final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
                                                            dlg.setTitle("Please wait.");
                                                            dlg.setMessage("Logging in.  Please wait.");
                                                            dlg.show();
                                                            //Storing data in shared preference
                                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                                            //prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
                                                            SharedPreferences.Editor editor= prefs.edit();
                                                            editor.putString("EmpName",object.getString("EmpName"));
                                                            Toast.makeText(getApplicationContext(),object.getString("EmpName"),Toast.LENGTH_LONG).show();
                                                            editor.commit();

                                                            Intent indexIntent=new Intent(MainActivity.this,EmpHome.class);
                                                           // indexIntent.putExtra("studentInvoking",sid.getText().toString());
                                                            startActivity(indexIntent);
                                                        }
                                                        else {
                                                            Toast.makeText(MainActivity.this, "error "+e, Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });

                                            }
                                        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(MainActivity.this,EmployeeReg.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
