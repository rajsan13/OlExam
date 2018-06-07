package com.example.Aphexams;

/**
 * Created by Sandeep on 01-06-2018.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

public class LstViewAdapter extends ArrayAdapter<String> {
    public static String str;
    public static int v=0;
    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    ArrayList<String> arl = new ArrayList<String>();

    ArrayList<String> b=new ArrayList<String>();
    ArrayList<String> c=new ArrayList<String>();
    ArrayList<String> d=new ArrayList<String>();
    Context context;
    public LstViewAdapter(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textname;
        public TextView textprice;
        public TextView submitt;
        public TextView correctt;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textname= (TextView) rowView.findViewById(R.id.txtname);
            viewHolder.textprice= (TextView) rowView.findViewById(R.id.txtprice);
            viewHolder.submitt=(TextView)rowView.findViewById(R.id.submitted);
            viewHolder.correctt=(TextView)rowView.findViewById(R.id.correct);
            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final String[] items=item_list[position].split("__");
        ViewHolder holder = (ViewHolder) rowView.getTag();

        //here we have to keep items



        for(int i=0;i<1000;i++)
        {
            int k=0;
            k++;
            //System.out.println(k);
        }
        for(int i=0;i<10000;i++)
        {
            int k=0;k++;
            //System.out.println(k);
        }
       // System.out.println("hello");
        holder.textname.setText(Global.ar.get(0));
        holder.textprice.setText(Global.ar.get(1));
        holder.submitt.setText(Global.ar.get(2));
        holder.correctt.setText(Global.ar.get(3));
        v=v+4;
        return rowView;
    }
public ArrayList<String> cc(){

    ParseQuery<ParseObject> query1 = ParseQuery.getQuery("responsesan");
    query1.addAscendingOrder("questionNumber");
    query1.orderByAscending("questionNumber");
    // List<ParseObject> objects = query1.find();
    //validationError=true;
    query1.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> list, ParseException e) {
            if (e == null) {
                int k=0;
                outer: for (ParseObject o : list) {
                    //results s = new Student();

                        arl.add(o.getNumber("questionNumber").toString());
                        arl.add(o.getString("question").toString());
                        arl.add(o.getNumber("submittedAnswer").toString());
                        arl.add(o.getString("correctAnswer").toString());

                    //  loop: System.out.println("hi");


                }
                // studentList is full here

            } else {

            }


        }

    });

    return arl;
}
}