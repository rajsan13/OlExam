package skaipal.example.com.tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class DeptHeadLogin extends Activity {
    EditText deptID;
    EditText deptPassword;
    Button deptLogin;
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

                ParseQuery<ParseObject> query = ParseQuery.getQuery("deptAuth");
                query.whereEqualTo("deptID",deptID.getText().toString());
                query.whereEqualTo("deptPassword",deptPassword.getText().toString());

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            final ProgressDialog dlg = new ProgressDialog(DeptHeadLogin.this);
                            dlg.setTitle("Please wait.");
                            dlg.setMessage("Logging in.  Please wait.");
                            dlg.show();
                            //Storing data in shared preference

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
}
