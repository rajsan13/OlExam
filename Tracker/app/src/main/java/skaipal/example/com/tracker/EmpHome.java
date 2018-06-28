package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class EmpHome extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Spinner spinner1;
    String taskSpinner;
    String projectSpinner;
    Button mStart;
    Button mEnd;
    CheckBox mCheckBox;
    Button leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_emp);
        spinner=(Spinner)findViewById(R.id.spinner_task);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.tasks,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        taskSpinner=spinner.getSelectedItem().toString();
        //Toast.makeText(EmpHome.this,taskSpinner,Toast.LENGTH_LONG).show();

        spinner1=(Spinner)findViewById(R.id.spinner_project);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.projects,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        projectSpinner=spinner1.getSelectedItem().toString();

        mStart=(Button)findViewById(R.id.Sbut);
        mEnd=(Button)findViewById(R.id.Ebut);
        mCheckBox=(CheckBox)findViewById(R.id.checkboxer);
        mStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Boolean a=mCheckBox.isChecked();
                taskSpinner=spinner.getSelectedItem().toString();
                projectSpinner=spinner1.getSelectedItem().toString();
                //String check=a.toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                String startDate=currentDateandTime.substring(0,10);
                String startTime=currentDateandTime.substring(11,19);
                Long startTimeMilli = Calendar.getInstance().getTimeInMillis();
               // Toast.makeText(EmpHome.this,startTime,Toast.LENGTH_LONG).show();
                ParseObject empDet=new ParseObject("empDetails");
                empDet.put("Task",taskSpinner);
                empDet.put("Project",projectSpinner);
                empDet.put("EmployeeId",Global.s);
                empDet.put("EnterOffice",a);
                empDet.put("StartDate",startDate);
                empDet.put("StartTime",startTime);
                empDet.saveInBackground();
               spinner.setEnabled(false);
                spinner1.setEnabled(false);
                mCheckBox.setEnabled(false);
                mStart.setEnabled(false);


            }
        });
        mEnd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                final String endDate=currentDateandTime.substring(0,10);
                final String endTime=currentDateandTime.substring(11,19);
                Long startTimeMilli = Calendar.getInstance().getTimeInMillis();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("empDetails");
                query.whereEqualTo("Task",taskSpinner);
                query.whereEqualTo("Project",projectSpinner);
                query.whereEqualTo("EmployeeId",Global.s);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                         /*   Log.d("Studname", "The getFirst request failed.");
                            ParseObject res = new ParseObject("result");
                            res.put("Studname",names);
                            res.put("MARKS",marks);
                            res.saveInBackground();*/
                            Toast.makeText(EmpHome.this,"failed",Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("Studname", "Retrieved the object.");
                            object.put("EndDate",endDate);
                            object.put("EndTime",endTime);
                            object.saveInBackground();
                        }
                    }
                });
                //Toast.makeText(EmpHome.this,"Details submitted successfully",Toast.LENGTH_LONG).show();
                Intent indexIntent=new Intent(EmpHome.this,EmployeeReg.class);
                startActivity(indexIntent);

            }
        });
        leave=(Button)findViewById(R.id.applyLeave);
        leave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Toast.makeText(EmpHome.this,"Details submitted successfully",Toast.LENGTH_LONG).show();
                Intent indexIntent=new Intent(EmpHome.this,ApplyLeave.class);
                startActivity(indexIntent);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        //Toast.makeText(EmpHome.this,text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}