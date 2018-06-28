package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class DeptHeadHome extends Activity {

    Button Daily;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_head_home);
       Daily=(Button)findViewById(R.id.daily);

        Daily.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent indexIntent=new Intent(DeptHeadHome.this,DatePick.class);
                startActivity(indexIntent);
            }
        });

    }
}
