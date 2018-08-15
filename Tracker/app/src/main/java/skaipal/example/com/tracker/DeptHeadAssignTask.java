package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeptHeadAssignTask extends Activity implements AdapterView.OnItemSelectedListener{
Spinner employee,project;
    Button assign;
    EditText task;
    String EmpID,Projectname;
    SharedPreferences preferences;
    ArrayList<String> employeelist;
    String DeptHeadName,Deptname;
    ArrayAdapter<String> adapter;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_assign_task);

        employee=(Spinner)findViewById(R.id.employee);


        employeelist=new ArrayList<String>();
        employeelist.add("select employee");
        preferences = PreferenceManager.getDefaultSharedPreferences(DeptHeadAssignTask.this);
        // gets the previously created intent
        DeptHeadName = preferences.getString("DeptHeadName","");
        Deptname = preferences.getString("Deptname","");


        ParseQuery<ParseObject> queryNoOfRows = ParseQuery.getQuery("empAuth");
        queryNoOfRows.whereEqualTo("DeptHead",DeptHeadName);
        queryNoOfRows.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("que", "The getFirst request failed.");
                    for(int i=0;i<objects.size();i++)
                    {

                        employeelist.add(objects.get(i).getString("EmpName"));
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

                }

            }





        });

        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,employeelist);
        adapter.notifyDataSetChanged();
        employee.setAdapter(adapter);

        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),(String) parent.getItemAtPosition(position),Toast.LENGTH_LONG).show();
                EmpID=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"No!",Toast.LENGTH_LONG).show();
            }
        });






        /*ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,employeelist);//createFromResource(this,R.array.tasks,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employee.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
       /* employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                EmpID = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"employee id "+EmpID,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

      //  EmpID= employee.getSelectedItem().toString();




        project=(Spinner)findViewById(R.id.projectnames);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.projects,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        project.setAdapter(adapter1);
        project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"hmm okay",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Projectname=project.getSelectedItem().toString();

        task=(EditText)findViewById(R.id.task);

        assign=(Button)findViewById(R.id.assigntask);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(task.getText().toString().equals("") || flag==1)
                {
                    Toast.makeText(getApplicationContext(),"any one of the fields is empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    ParseObject parseObject=new ParseObject("TaskAssign");
                    parseObject.put("EmpID",EmpID);
                    parseObject.put("Task",task.getText().toString().trim());
                    parseObject.put("Project",Projectname);
                    parseObject.put("Department",Deptname);
                    parseObject.saveInBackground();
                    Toast.makeText(getApplicationContext(),"task assigned add new task!",Toast.LENGTH_LONG).show();
                }

            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ;
        // If user change the default selection
        // First item is disable and it is used for hint
        flag=0;
        if (position > 0) {
            // Notify the selected item text
           // Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
            String selectedItemText = (String) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(),selectedItemText+" selectedItemText ",Toast.LENGTH_LONG).show();

        }
        else
        {
            flag=1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
