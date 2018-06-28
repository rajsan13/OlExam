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
public class SuperAdmin extends Activity {
    EditText SadminID;
    EditText SadminPassword;
    Button SadminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_admin_login);
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
      SadminID=(EditText)findViewById(R.id.Sadminid);
        SadminPassword=(EditText)findViewById(R.id.Sadminpword);
        SadminLogin=(Button)findViewById(R.id.SadminLogBut);
        SadminLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("SadminAuth");
                query.whereEqualTo("SadminID",SadminID.getText().toString());
                query.whereEqualTo("SadminPassword",SadminPassword.getText().toString());

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            final ProgressDialog dlg = new ProgressDialog(SuperAdmin.this);
                            dlg.setTitle("Please wait.");
                            dlg.setMessage("Logging in.  Please wait.");
                            dlg.show();
                            //Storing data in shared preference

                            Intent indexIntent=new Intent(SuperAdmin.this,SuperAdminHome.class);
                            //indexIntent.putExtra("studentInvoking",.getText().toString());
                            startActivity(indexIntent);
                        }
                        else {
                            Toast.makeText(SuperAdmin.this, "Error: Check out data!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}
