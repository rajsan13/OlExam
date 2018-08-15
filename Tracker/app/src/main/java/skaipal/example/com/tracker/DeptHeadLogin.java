package skaipal.example.com.tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class DeptHeadLogin extends Activity implements AdapterView.OnItemSelectedListener{
    EditText deptID;
    EditText deptPassword;
    Button deptLogin;
    Spinner dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_head_login);
        //Toast.makeText(MainActivity.this,Integer.toString(Global.check),Toast.LENGTH_LONG).show();
        if (Global.check == 0) {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("AEEd95OiRpDIUS00U6KELyA9DQpgBD3WwM2Muti0")
                    .clientKey("2QyWYQiYh04eyc1JWOOlAp0sHvyaDgwkD2CGNpmt")
                    .server("https://parseapi.back4app.com/")
                    .build()
            );
            Global.check=1;
            
        }


        deptID=(EditText)findViewById(R.id.deptid);
        deptPassword=(EditText)findViewById(R.id.deptpword);
        deptLogin=(Button)findViewById(R.id.deptLogBut);
        deptLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("empAuth");
                query.whereEqualTo("EmpID",deptID.getText().toString());
                query.whereEqualTo("EmpPassword",deptPassword.getText().toString());
                query.whereEqualTo("DeptHead","SA");

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            final ProgressDialog dlg = new ProgressDialog(DeptHeadLogin.this);
                            dlg.setTitle("Please wait.");
                            dlg.setMessage("Logging in.  Please wait.");
                            dlg.show();
                            //Storing data in shared preference
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DeptHeadLogin.this);
                            //prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
                            SharedPreferences.Editor editor= prefs.edit();
                            editor.putString("DeptHeadName",deptID.getText().toString());
                            editor.putString("Deptname",object.getString("Dept"));
                            editor.commit();

                            Intent indexIntent=new Intent(DeptHeadLogin.this,DeptHeadHome.class);
                            //indexIntent.putExtra("studentInvoking",.getText().toString());
                            startActivity(indexIntent);
                        }
                        else {
                            Toast.makeText(DeptHeadLogin.this, "Error: Check out data!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
