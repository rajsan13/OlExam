package com.example.Aphexams;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AdminViewQuestionSet extends Activity {
    private RecyclerView recycleerviewDetails;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<QuestionSet> questionarraylist;
    private boolean notComplete = true;
    QuestionSet questionSet;
    private String quesno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_question_set);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        recycleerviewDetails=(RecyclerView)findViewById(R.id.studentid);
        if (recycleerviewDetails != null)
        {
            recycleerviewDetails.setHasFixedSize(true);
        }
        layoutManager=new GridLayoutManager(this,1);
        recycleerviewDetails.setLayoutManager(layoutManager);
        questionarraylist=new ArrayList<QuestionSet>();

        if(notComplete) {


            ParseQuery<ParseObject> query = ParseQuery.getQuery("Vex");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                       Toast.makeText(getApplicationContext(),Integer.toString(objects.size()),Toast.LENGTH_LONG).show();
                        for(int i=0;i<objects.size();i++)
                        {

                            questionSet=new QuestionSet();
                            questionSet.setQuestion((String)objects.get(i).get("vque"));
                            quesno=Integer.toString(objects.get(i).getInt("vqno"));
                            questionSet.setQuestionNo(quesno);
                            questionarraylist.add(questionSet);
                            // Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();
                            quesno=questionarraylist.get(i).getQuestionNo();
                            //Toast.makeText(getApplicationContext(),"quesno :"+quesno,Toast.LENGTH_LONG).show();
                          //  Toast.makeText(getApplicationContext(),"quesno :"+quesno,Toast.LENGTH_LONG).show();
                            QuestionSetAdapter questionSetAdapter=new QuestionSetAdapter(questionarraylist);
                            recycleerviewDetails.setAdapter(questionSetAdapter);
                            questionSetAdapter.notifyDataSetChanged();

                            recycleerviewDetails.addOnItemTouchListener(new RecyclerViewListener(AdminViewQuestionSet.this,new RecyclerViewListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    quesno=questionarraylist.get(position).getQuestionNo();
                                    Toast.makeText(getApplicationContext(),"quesno :"+quesno,Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(AdminViewQuestionSet.this,ViewV.class);
                                    i.putExtra("quesno",quesno);
                                    startActivity(i);
                                }
                            }));

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            notComplete=false;
            //Toast.makeText(getApplicationContext(),"marks :"+temp,Toast.LENGTH_SHORT).show();

        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i=new Intent(AdminViewQuestionSet.this,HomeAdmin.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //If the Intent resolves to an Activity in the current task the Activities above it on the stack are destroyed so that it is at the top of the stack, and it is re-used.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
