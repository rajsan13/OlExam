package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseObject;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class ApplyLeave extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String reasonSpinner;
    Button apply;
    EditText adminID;
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
        adminID=(EditText)findViewById(R.id.admID);
        apply=(Button)findViewById(R.id.apLeave);
        apply.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                reasonSpinner=spinner.getSelectedItem().toString();
                ParseObject leave=new ParseObject("leaveDetails");
                leave.put("EmpID",Global.s);
                leave.put("AdminID",adminID.getText().toString());
                leave.put("LeaveReason",reasonSpinner);
                leave.saveInBackground();
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
