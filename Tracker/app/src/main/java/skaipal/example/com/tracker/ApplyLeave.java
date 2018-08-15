package skaipal.example.com.tracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class ApplyLeave extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String reasonSpinner;
    Button apply;
    EditText etfromdate,ettodate;
    int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_leave);
        spinner=(Spinner)findViewById(R.id.spinner_leave);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.reasons,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        reasonSpinner=spinner.getSelectedItem().toString();

        etfromdate=(EditText)findViewById(R.id.fromdate);
        etfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                 mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ApplyLeave.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                      //  Toast.makeText(getApplicationContext(),""+selectedday+""+selectedmonth+""+selectedyear,Toast.LENGTH_LONG).show();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                        Date date= new Date(selectedyear-1900,selectedmonth,selectedday);
                        String fromdate=simpleDateFormat.format(date);
                        Toast.makeText(getApplicationContext(),fromdate,Toast.LENGTH_LONG).show();
                        etfromdate.setText(fromdate);

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });
        ettodate=(EditText)findViewById(R.id.todate);
        ettodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ApplyLeave.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        //  Toast.makeText(getApplicationContext(),""+selectedday+""+selectedmonth+""+selectedyear,Toast.LENGTH_LONG).show();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                        Date date= new Date(selectedyear-1900,selectedmonth,selectedday);
                        String fromdate=simpleDateFormat.format(date);
                        Toast.makeText(getApplicationContext(),fromdate,Toast.LENGTH_LONG).show();
                        ettodate.setText(fromdate);

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });


       // adminID=(EditText)findViewById(R.id.admID);
        apply=(Button)findViewById(R.id.apLeave);
        apply.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                reasonSpinner=spinner.getSelectedItem().toString();
                ParseObject leave=new ParseObject("leaveDetails");
                leave.put("EmpID",Global.s);
                leave.put("FromDate",etfromdate.getText().toString());
                leave.put("ToDate",ettodate.getText().toString());
                leave.put("LeaveReason",reasonSpinner);
                leave.put("LeaveStatus","Not Reviewed");
                leave.saveInBackground();
                Toast.makeText(getApplicationContext(),"leave application has been sent to your dept head",Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),EmpHome.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
