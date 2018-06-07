package com.example.Aphexams;

/**
 * Created by Sandeep on 01-06-2018.
 */
import android.app.Activity;
import android.os.Bundle;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.SignUpCallback;
import android.app.ActionBar;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Result1 extends Activity {
public  static String f="";
    public  ArrayList<String> x=new ArrayList<>();
   /* public static String x="";
    public static String y="";
    public static String z="";
    public static String w="";*/
   public static ArrayList<String> y=new ArrayList<>();
    public static ArrayList<String> z=new ArrayList<>();
    public static ArrayList<String> w=new ArrayList<>();
    public static int p=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result1);
        Global.ar.clear();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("responsedes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
               // Toast.makeText(getApplicationContext(),"sfsdd",Toast.LENGTH_LONG).show();
                if (e == null) {

                    for(int i=0;i<markers.size();i++)
                    {
                        x.add((String)markers.get(i).getString("question"));
                        System.out.println("hi");
                        Toast.makeText(getApplicationContext(),x.get(i),Toast.LENGTH_LONG).show();
                    }
                    System.out.println("hi1");


                    // h="dfdf";
                }else {
                    // handle Parse Exception here
                    Toast.makeText(getApplicationContext(),"s2",Toast.LENGTH_LONG).show();
                    Global.ar.add("gi");
                    Global.ar.add("gi");
                    Global.ar.add("gi");
                    Global.ar.add("gi");
                    System.out.println("hi");
                }

                // f=Global.quest;
            }

        });
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        for(int i=0;i<10000000;i++)
        {
            int k=0;k++;
            //System.out.println(k);
        }
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        //Global.ar.add("gi");
        System.out.println("helloo0");
        /*for(int i=0;i<x.size();i++){
        Global.ar.add(x.get(i));
        Global.ar.add(y.get(i));
        Global.ar.add(z.get(i));
        Global.ar.add(w.get(i));}*/
        Toast.makeText(getApplicationContext(),x.get(0),Toast.LENGTH_LONG).show();
        int p=Global.ar.size();
        String b=Integer.toString(p);
    // Toast.makeText(getApplicationContext(),b,Toast.LENGTH_LONG).show();

        ListView lstview=(ListView)findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, lstview,false);
        // Add header view to the ListView
        lstview.addHeaderView(headerView);
        // Get the string array defined in strings.xml file
        String[] items=getResources().getStringArray(R.array.list_items);
        // Create an adapter to bind data to the ListView
        LstViewAdapter adapter=new LstViewAdapter(this,R.layout.rowlayout,R.id.txtname,items);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }
    public String cc()
    {
        Global.ar.add("helo");

       // Toast.makeText(getApplicationContext(),h, Toast.LENGTH_LONG).show();
        return "den";
    }
}

