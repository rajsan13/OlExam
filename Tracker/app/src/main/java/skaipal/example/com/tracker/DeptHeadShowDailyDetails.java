package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import skaipal.example.com.tracker.adapter.EmployeeTaskAdapter;
import skaipal.example.com.tracker.model.EmployeeTaskDetails;

public class DeptHeadShowDailyDetails extends Activity {
    private RecyclerView recycleerviewDetails;
    private RecyclerView.LayoutManager layoutManager;
    private EmployeeTaskAdapter employeeTaskAdapter;
    private ArrayList<EmployeeTaskDetails> employeeTaskDetailsArrayList;
    private boolean notComplete = true;
    EmployeeTaskDetails employeeTaskDetails;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_show_daily_details);
        Bundle ob=getIntent().getExtras();
        date=ob.getString("date");
       // Toast.makeText(getApplicationContext(),ob.getString("date"),Toast.LENGTH_LONG).show();

        recycleerviewDetails=(RecyclerView)findViewById(R.id.rvemployeetask);
        if (recycleerviewDetails != null)
        {
            recycleerviewDetails.setHasFixedSize(true);
        }
        layoutManager=new GridLayoutManager(this,1);
        recycleerviewDetails.setLayoutManager(layoutManager);

        employeeTaskDetailsArrayList=new ArrayList<EmployeeTaskDetails>();

        if(notComplete) {

            SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String deptheadid=settings.getString("DeptHeadName","");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("empAuth");
            query.whereEqualTo("DeptHead",deptheadid);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //Toast.makeText(getApplicationContext(),Integer.toString(objects.size()),Toast.LENGTH_LONG).show();
                        for(int i=0;i<objects.size();i++)
                        {
                            String empid=objects.get(i).getString("EmpID");
                            employeeTaskDetails=new EmployeeTaskDetails();
                            employeeTaskDetails.setEmpId(empid);


                            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("empDetails");
                            query1.whereEqualTo("EmployeeId",empid);
                            query1.whereEqualTo("StartDate",date);

                           query1.findInBackground(new FindCallback<ParseObject>() {
                               @Override
                               public void done(List<ParseObject> objects, ParseException e) {
                                   for(int i=0;i<objects.size();i++)
                                   {
                                       String task=objects.get(i).getString("Task");
                                       String endtime=objects.get(i).getString("EndTime");
                                       String starttime=objects.get(i).getString("StartTime");
                                       String project=objects.get(i).getString("Project");
                                       Toast.makeText(getApplicationContext(),task+" "+endtime+" "+starttime+" "+project,Toast.LENGTH_LONG).show();
                                       employeeTaskDetails.setProject(project);
                                       employeeTaskDetails.setTask(task);
                                       employeeTaskDetails.setFromtime(starttime);
                                       employeeTaskDetails.setTotime(endtime);
                                       employeeTaskDetailsArrayList.add(employeeTaskDetails);
                                       // Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();
                                       employeeTaskAdapter=new EmployeeTaskAdapter(employeeTaskDetailsArrayList);
                                       recycleerviewDetails.setAdapter(employeeTaskAdapter);
                                       employeeTaskAdapter.notifyDataSetChanged();


                                   }
                               }
                           });


                        }
                        //Now the arraylist is populated!
                          /*  for(int i=0;i<studentidarraylist.size();i++)
                            {
                                Toast.makeText(AdminShowStudentId.this,studentidarraylist.get(i).getStudentId() + "  "+i, Toast.LENGTH_SHORT).show();

                            }*/


                       recycleerviewDetails.addOnItemTouchListener(new RecyclerViewListener(getApplicationContext(), new RecyclerViewListener.OnItemClickListener() {
                           @Override
                           public void onItemClick(View view, int position) {

                           }
                       }));

                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            notComplete=false;
            //Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();

        }



        //fetch all employees of that particular date and under the supervision of the particular department head


    }
}
