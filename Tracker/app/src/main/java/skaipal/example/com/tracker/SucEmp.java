package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sandeep on 25-06-2018.
 */
public class SucEmp extends Activity {
    Button sucloginbutton1;
    Button succancelbutton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_student_login);

        sucloginbutton1 = (Button)findViewById(R.id.suclogin1);
        sucloginbutton1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent indexIntent=new Intent(SucEmp.this,MainActivity.class);
                startActivity(indexIntent);
            }
        });

        succancelbutton1 = (Button)findViewById(R.id.succancel1);
        succancelbutton1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent indexIntent=new Intent(SucEmp.this,MainActivity.class);
                startActivity(indexIntent);
            }
        });
    }
}
