package com.example.Aphexams;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

public class AdminSetQuesLimit extends Activity {

    Button btnsetqueslimit;
    TextView tvsetqueslimit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_set_ques_limit);

        tvsetqueslimit=(EditText)findViewById(R.id.tvsetqueslimit);

        btnsetqueslimit=(Button)findViewById(R.id.bsetqueslimit);
        btnsetqueslimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject QuestionNo = new ParseObject("QuestionNo");

                int a=Integer.parseInt(tvsetqueslimit.getText().toString())+1;
                Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
                QuestionNo.put("Quesno",Integer.parseInt(tvsetqueslimit.getText().toString()));
                Toast.makeText(getApplicationContext(),"the no of questions appearing in the text is set!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),HomeAdmin.class);
                startActivity(i);

            }
        });
    }
}
