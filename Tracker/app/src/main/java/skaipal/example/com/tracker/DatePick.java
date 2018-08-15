package skaipal.example.com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Sandeep on 28-06-2018.
 */
public class DatePick extends Activity {
    Bundle ob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_date);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //prefs.edit().putBoolean("isMobile", Boolean.valueOf(mobile)).commit();
                SharedPreferences.Editor editor= prefs.edit();
                editor.putString("year",Integer.toString(year));
                editor.putString("month",Integer.toString(month));
                editor.putString("day",Integer.toString(dayOfMonth));

                editor.commit();

            }
        });
    }
}
