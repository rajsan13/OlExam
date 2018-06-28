package skaipal.example.com.tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Sandeep on 25-06-2018.
 */
public class EmployeeReg extends Activity{
    Button empLogin;
    Button deptheadLogin;
    Button superadminLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empLogin=(Button)findViewById(R.id.empLog);
        deptheadLogin=(Button)findViewById(R.id.deptLog);
        superadminLogin=(Button)findViewById(R.id.superLog);
        empLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        deptheadLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),DeptHeadLogin.class);
                startActivity(i);
            }
        });

        superadminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),SuperAdmin.class);
                startActivity(i);
            }
        });




    }

}
