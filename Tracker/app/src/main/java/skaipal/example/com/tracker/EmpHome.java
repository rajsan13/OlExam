package skaipal.example.com.tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
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
    ArrayList<String> tasklist;
    ArrayAdapter<String> adapter;
    SharedPreferences preferences;
    String EmpName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_emp);

        ActionBar ab = getActionBar();
       // ab.setDisplayHomeAsUpEnabled(true);
        tasklist=new ArrayList<String>();



       // taskSpinner=spinner.getSelectedItem().toString();
        //Toast.makeText(EmpHome.this,taskSpinner,Toast.LENGTH_LONG).show();
        preferences = PreferenceManager.getDefaultSharedPreferences(EmpHome.this);
        // gets the previously created intent
        EmpName = preferences.getString("EmpName","");
      //  Toast.makeText(getApplicationContext(),"empid "+EmpName,Toast.LENGTH_LONG).show();


        spinner1=(Spinner)findViewById(R.id.spinner_project);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.projects,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        projectSpinner=spinner1.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(),"project "+projectSpinner,Toast.LENGTH_LONG).show();





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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(EmpHome.this,MainActivity.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;
            case R.id.logout:
                Toast.makeText(EmpHome.this,"Logged out!",Toast.LENGTH_LONG).show();
                SharedPreferences Settings = this.getSharedPreferences("EmpID", Context.MODE_PRIVATE);
                Settings.edit().clear().commit();
                Intent intent=new Intent(EmpHome.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        //Toast.makeText(EmpHome.this,text,Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> queryNoOfRows = ParseQuery.getQuery("TaskAssign");
        queryNoOfRows.whereEqualTo("Project",text);
        queryNoOfRows.whereEqualTo("EmpID",EmpName);
        queryNoOfRows.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("que", "The getFirst request failed.");
                    Toast.makeText(getApplicationContext(),"TASK LIST: "+objects.size(),Toast.LENGTH_LONG).show();
                    for(int i=0;i<objects.size();i++)
                    {

                        tasklist.add(objects.get(i).getString("Task"));

                        // Toast.makeText(getApplicationContext(),employeelist.get(i),Toast.LENGTH_LONG).show();
                     /*   adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,employeelist);
                        adapter.notifyDataSetChanged();
                        employee.setAdapter(adapter);

                        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getApplicationContext(),"yeah!",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(getApplicationContext(),"No!",Toast.LENGTH_LONG).show();
                            }
                        });*/
                    }
                }
                else {
                    Log.d("que", "Retrieved the object.");
                    Toast.makeText(getApplicationContext(),"error "+e,Toast.LENGTH_LONG).show();


                }

            }
        });

        spinner=(Spinner)findViewById(R.id.spinner_task);
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tasklist);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),(String) parent.getItemAtPosition(position),Toast.LENGTH_LONG).show();
                taskSpinner=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}