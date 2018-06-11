package com.example.Aphexams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.match.library.MatchButton;
import com.roger.match.library.MatchTextView;

import org.w3c.dom.Text;

/**
 * Created by Sandeep on 10-06-2018.
 */
public class Result2 extends Activity {
    private MatchTextView mMatchTextView,mButton;
    private SeekBar mSeekBar;
public static String marks,names;
    TextView mar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result2);
       SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(Result2.this);
         marks= settings.getString("mark", "");
         names=settings.getString("name","");
        mar=(TextView)findViewById(R.id.res);
        mMatchTextView = (MatchTextView) findViewById(R.id.mMatchTextView);

        mMatchTextView.setText(names);
        mButton = (MatchButton) findViewById(R.id.mButton);
        mButton.setText(marks);
        mMatchTextView.setProgress(0);
        mButton.setProgress(1);
        mButton.setProgress(0);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("result");
        query.whereEqualTo("Studname",names);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("Studname", "The getFirst request failed.");
                    ParseObject res = new ParseObject("result");
                    res.put("Studname",names);
                    res.put("MARKS",marks);
                    res.saveInBackground();
                } else {
                    Log.d("Studname", "Retrieved the object.");
                    object.put("MARKS",marks);
                    object.saveInBackground();
                }
            }
        });

       mar.setText(marks);
    //   mMatchTextView.setText(names);


       /* mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //mMatchTextView.setProgress(progress * 1f / 100);
                //mButton.setProgress(1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,HomeStudent
                .class);
        startActivity(intent);
    }
}
