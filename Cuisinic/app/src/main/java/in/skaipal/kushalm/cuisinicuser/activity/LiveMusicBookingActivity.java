package in.skaipal.kushalm.cuisinicuser.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@SuppressLint({"Registered"})
public class LiveMusicBookingActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    Date Time1;
    Date Time2;
    Button book;
    EditText date;
    EditText eTime;
    Date pick;
    LinearLayout progress;
    EditText sTime;
    private SharedPreferences sp;
    Date today;
    String url = APIUrls.liveMusicBookingUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_book_music);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initializeViews();
        this.date.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LiveMusicBookingActivity.this.datePickerDialogBox();
            }
        });
        this.sTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    LiveMusicBookingActivity.this.timePickerDialogBox("start");
                } catch (View view2) {
                    view2.printStackTrace();
                }
            }
        });
        this.eTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    LiveMusicBookingActivity.this.timePickerDialogBox("end");
                } catch (View view2) {
                    view2.printStackTrace();
                }
            }
        });
        this.book.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (LiveMusicBookingActivity.this.date.getText().toString().isEmpty() != null) {
                    LiveMusicBookingActivity.this.date.setError("Please provide a date");
                    LiveMusicBookingActivity.this.sTime.setError(null);
                    LiveMusicBookingActivity.this.eTime.setError(null);
                } else if (LiveMusicBookingActivity.this.sTime.getText().toString().isEmpty() != null) {
                    LiveMusicBookingActivity.this.sTime.setError("Provide starting Time");
                    LiveMusicBookingActivity.this.date.setError(null);
                    LiveMusicBookingActivity.this.eTime.setError(null);
                } else if (LiveMusicBookingActivity.this.eTime.getText().toString().isEmpty() != null) {
                    LiveMusicBookingActivity.this.eTime.setError("Provide ending Time");
                    LiveMusicBookingActivity.this.date.setError(null);
                    LiveMusicBookingActivity.this.sTime.setError(null);
                } else {
                    LiveMusicBookingActivity.this.date.setError(null);
                    LiveMusicBookingActivity.this.sTime.setError(null);
                    LiveMusicBookingActivity.this.eTime.setError(null);
                    view = new SimpleDateFormat("hh:mm");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar instance = Calendar.getInstance();
                    String format = view.format(instance.getTime());
                    String format2 = simpleDateFormat.format(instance.getTime());
                    try {
                        view = view.parse(format);
                    } catch (View view2) {
                        view2.printStackTrace();
                        view2 = null;
                    }
                    int time = (int) ((LiveMusicBookingActivity.this.Time2.getTime() - LiveMusicBookingActivity.this.Time1.getTime()) / 3600000);
                    view2 = (int) ((view2.getTime() - LiveMusicBookingActivity.this.Time1.getTime()) / 3600000);
                    if (format2.equalsIgnoreCase(LiveMusicBookingActivity.this.date.getText().toString().trim())) {
                        if (view2 < null) {
                            Toast.makeText(LiveMusicBookingActivity.this, "Start time cannot be less than current time", 0).show();
                        } else if (time <= 0) {
                            Toast.makeText(LiveMusicBookingActivity.this, "End time cannot be before start time", 0).show();
                        } else if (time <= 1) {
                            LiveMusicBookingActivity.this.bookLiveMusic();
                        } else {
                            Toast.makeText(LiveMusicBookingActivity.this, "Booking is only allowed for 1 hour", 0).show();
                        }
                    } else if (time <= 0) {
                        Toast.makeText(LiveMusicBookingActivity.this, "End time cannot be before start time", 0).show();
                    } else if (time <= 1) {
                        LiveMusicBookingActivity.this.bookLiveMusic();
                    } else {
                        Toast.makeText(LiveMusicBookingActivity.this, "Booking is only allowed for 1 hour", 0).show();
                    }
                }
            }
        });
    }

    private void bookLiveMusic() {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MUSIC_");
        stringBuilder.append(format);
        networkAction(stringBuilder.toString());
    }

    public void makeProgressInvisible() {
        this.progress.setVisibility(8);
        this.book.setVisibility(0);
    }

    public void makeProgressVisible() {
        this.progress.setVisibility(0);
        this.book.setVisibility(8);
    }

    private void networkAction(String str) {
        makeProgressVisible();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.Email);
        arrayList.add(str);
        arrayList.add(this.date.getText().toString().trim());
        arrayList.add(this.sTime.getText().toString().trim());
        arrayList.add(this.eTime.getText().toString().trim());
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("booking_id");
        arrayList2.add("date");
        arrayList2.add("start_time");
        arrayList2.add("end_time");
        final int[] iArr = new int[1];
        str = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    System.out.println(str);
                    JSONObject jSONObject = new JSONObject(str);
                    iArr[0] = jSONObject.getInt("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (iArr[0] == 1) {
                        LiveMusicBookingActivity.this.makeProgressInvisible();
                        Toast.makeText(LiveMusicBookingActivity.this, str[0], 0).show();
                        LiveMusicBookingActivity.this.startActivity(new Intent(LiveMusicBookingActivity.this, CuisinicMenuActivity.class));
                    } else if (iArr[0] == null) {
                        LiveMusicBookingActivity.this.makeProgressInvisible();
                        Toast.makeText(LiveMusicBookingActivity.this, str[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                LiveMusicBookingActivity.this.makeProgressInvisible();
                Toast.makeText(LiveMusicBookingActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "Book Live Music");
    }

    private void initializeViews() {
        this.date = (EditText) findViewById(R.id.selectDate);
        this.sTime = (EditText) findViewById(R.id.selectStartTime);
        this.eTime = (EditText) findViewById(R.id.selectEndTime);
        this.book = (Button) findViewById(R.id.book);
        this.progress = (LinearLayout) findViewById(R.id.progress);
    }

    private void timePickerDialogBox(String str) throws ParseException {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(11);
        int i2 = instance.get(12);
        TimePickerDialog timePickerDialog;
        if (str.equalsIgnoreCase("start")) {
            str = new SimpleDateFormat("hh:mm");
            timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new OnTimeSetListener() {
                public void onTimeSet(TimePicker timePicker, int i, int i2) {
                    if (i2 < 10) {
                        timePicker = LiveMusicBookingActivity.this.sTime;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(i);
                        stringBuilder.append(":0");
                        stringBuilder.append(i2);
                        timePicker.setText(stringBuilder.toString());
                        try {
                            timePicker = LiveMusicBookingActivity.this;
                            DateFormat dateFormat = str;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(i);
                            stringBuilder2.append(":0");
                            stringBuilder2.append(i2);
                            timePicker.Time1 = dateFormat.parse(stringBuilder2.toString());
                            return;
                        } catch (TimePicker timePicker2) {
                            timePicker2.printStackTrace();
                            return;
                        }
                    }
                    timePicker2 = LiveMusicBookingActivity.this.sTime;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(i);
                    stringBuilder.append(":");
                    stringBuilder.append(i2);
                    timePicker2.setText(stringBuilder.toString());
                    try {
                        timePicker2 = LiveMusicBookingActivity.this;
                        dateFormat = str;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(i);
                        stringBuilder2.append(":");
                        stringBuilder2.append(i2);
                        timePicker2.Time1 = dateFormat.parse(stringBuilder2.toString());
                    } catch (TimePicker timePicker22) {
                        timePicker22.printStackTrace();
                    }
                }
            }, i, i2, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        } else if (str.equalsIgnoreCase("end") != null) {
            str = new SimpleDateFormat("hh:mm");
            timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new OnTimeSetListener() {
                public void onTimeSet(TimePicker timePicker, int i, int i2) {
                    if (i2 < 10) {
                        timePicker = LiveMusicBookingActivity.this.eTime;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(i);
                        stringBuilder.append(":0");
                        stringBuilder.append(i2);
                        timePicker.setText(stringBuilder.toString());
                        try {
                            timePicker = LiveMusicBookingActivity.this;
                            DateFormat dateFormat = str;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(i);
                            stringBuilder2.append(":0");
                            stringBuilder2.append(i2);
                            timePicker.Time2 = dateFormat.parse(stringBuilder2.toString());
                            return;
                        } catch (TimePicker timePicker2) {
                            timePicker2.printStackTrace();
                            return;
                        }
                    }
                    timePicker2 = LiveMusicBookingActivity.this.eTime;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(i);
                    stringBuilder.append(":");
                    stringBuilder.append(i2);
                    timePicker2.setText(stringBuilder.toString());
                    try {
                        timePicker2 = LiveMusicBookingActivity.this;
                        dateFormat = str;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(i);
                        stringBuilder2.append(":");
                        stringBuilder2.append(i2);
                        timePicker2.Time2 = dateFormat.parse(stringBuilder2.toString());
                    } catch (TimePicker timePicker22) {
                        timePicker22.printStackTrace();
                    }
                }
            }, i, i2, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        }
    }

    private void datePickerDialogBox() {
        final Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        this.today = instance.getTime();
        new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                datePicker = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Current_time::::");
                stringBuilder.append(instance.getTime());
                datePicker.print(stringBuilder.toString());
                LiveMusicBookingActivity.this.populateSetDate(instance, i, i2, i3);
            }
        }, i, i2, i3).show();
    }

    public void populateSetDate(Calendar calendar, int i, int i2, int i3) {
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
        this.pick = calendar.getTime();
        calendar = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        if (this.pick.compareTo(this.today) <= 0) {
            if (this.pick.compareTo(this.today) != 0) {
                Toast.makeText(this, "Selected date cannot be previous to today's date", 0).show();
                return;
            }
        }
        this.date.setText(calendar);
    }
}
