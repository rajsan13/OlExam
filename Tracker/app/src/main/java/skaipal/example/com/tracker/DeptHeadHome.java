package skaipal.example.com.tracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class DeptHeadHome extends Activity {

    Button assigntask,btdaily,grantleave;
    EditText Daily;
    int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_head_home);

        Daily=(EditText)findViewById(R.id.daily);
        Daily.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(DeptHeadHome.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        //  Toast.makeText(getApplicationContext(),""+selectedday+""+selectedmonth+""+selectedyear,Toast.LENGTH_LONG).show();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                        Date date= new Date(selectedyear-1900,selectedmonth,selectedday);
                        String fromdate=simpleDateFormat.format(date);
                        Toast.makeText(getApplicationContext(),fromdate,Toast.LENGTH_LONG).show();
                        Daily.setText(fromdate);

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        btdaily=(Button)findViewById(R.id.btdaily);
        btdaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),DeptHeadShowDailyDetails.class);
                i.putExtra("date",Daily.getText().toString());
                startActivity(i);
            }
        });
        grantleave=(Button)findViewById(R.id.grant_leave);
        grantleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),GrantLeave.class);
                startActivity(i);
            }
        });
        assigntask=(Button)findViewById(R.id.assigntask);
        assigntask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i=new Intent((getApplicationContext(),))
                Intent i=new Intent(getApplicationContext(),DeptHeadAssignTask.class);
                startActivity(i);
            }
        });


    }
}
