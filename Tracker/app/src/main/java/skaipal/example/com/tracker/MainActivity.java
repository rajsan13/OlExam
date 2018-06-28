package skaipal.example.com.tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                                                query.whereEqualTo("EmpID",sid.getText().toString());
                                                query.whereEqualTo("EmpPassword",spword.getText().toString());
                                                 Global.s=sid.getText().toString();
                                                query.getFirstInBackground(new GetCallback<ParseObject>() {
                                                    public void done(ParseObject object, ParseException e) {

                                                        if (object != null) {
                                                            final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
                                                            dlg.setTitle("Please wait.");
                                                            dlg.setMessage("Logging in.  Please wait.");
                                                            dlg.show();
                                                            //Storing data in shared preference

                                                            Intent indexIntent=new Intent(MainActivity.this,EmpHome.class);
                                                            indexIntent.putExtra("studentInvoking",sid.getText().toString());
                                                            startActivity(indexIntent);
                                                        }
                                                        else {
                                                            Toast.makeText(MainActivity.this, "Error: Check out data!!!", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });

                                            }
                                        });


    }
}
