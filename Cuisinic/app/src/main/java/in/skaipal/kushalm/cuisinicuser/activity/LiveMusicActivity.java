package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.adapter.LiveBookingAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.LiveMusicOther;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class LiveMusicActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    FloatingActionButton add;
    ArrayList<String> bookingDate;
    ArrayList<String> bookingId;
    ArrayList<String> declineStatus;
    ArrayList<String> endTime;
    LiveBookingAdapter mAdapter;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    private SharedPreferences sp;
    ArrayList<String> startTime;
    ArrayList<String> status;
    String url = APIUrls.liveMusicUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_live_music);
        getSupportActionBar().setTitle((CharSequence) "Live Music Booking");
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initialiseViews();
        networkAction();
        this.add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LiveMusicActivity.this.startActivity(new Intent(LiveMusicActivity.this.getApplicationContext(), LiveMusicBookingActivity.class));
            }
        });
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        this.add = (FloatingActionButton) findViewById(R.id.addBooking);
    }

    private void networkAction() {
        this.progress.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.Email);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        final String[] strArr = new String[1];
        final String[] strArr2 = new String[1];
        final View findViewById = findViewById(16908290);
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    strArr[0] = jSONObject.getString("success");
                    strArr2[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (strArr[0].equals("1") != null) {
                        str = jSONObject.getJSONArray("product");
                        LiveMusicActivity.this.bookingId = new ArrayList();
                        LiveMusicActivity.this.bookingDate = new ArrayList();
                        LiveMusicActivity.this.startTime = new ArrayList();
                        LiveMusicActivity.this.endTime = new ArrayList();
                        LiveMusicActivity.this.status = new ArrayList();
                        LiveMusicActivity.this.declineStatus = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            LiveMusicActivity.this.bookingId.add(jSONObject2.getString("booking_id"));
                            LiveMusicActivity.this.bookingDate.add(jSONObject2.getString("date"));
                            LiveMusicActivity.this.startTime.add(jSONObject2.getString("start_time"));
                            LiveMusicActivity.this.endTime.add(jSONObject2.getString("end_time"));
                            LiveMusicActivity.this.status.add(jSONObject2.getString("is_approved"));
                            LiveMusicActivity.this.declineStatus.add(jSONObject2.getString("decline_reason"));
                        }
                        LiveMusicActivity.this.progress.hide();
                        LiveMusicActivity.this.setUpRecyclerView(LiveMusicActivity.this.bookingId, LiveMusicActivity.this.bookingDate, LiveMusicActivity.this.startTime, LiveMusicActivity.this.endTime, LiveMusicActivity.this.status, LiveMusicActivity.this.declineStatus);
                        return;
                    }
                    LiveMusicActivity.this.progress.hide();
                    Toast.makeText(LiveMusicActivity.this, "No booking found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    LiveMusicActivity.this.progress.hide();
                    Context context = LiveMusicActivity.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(str2.toString());
                    Toast.makeText(context, stringBuilder.toString(), 0).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if ((volleyError instanceof NoConnectionError) != null) {
                    Snackbar.make(findViewById, (CharSequence) "Connection Error", 0).setAction((CharSequence) "RETRY", new OnClickListener() {
                        public void onClick(View view) {
                            LiveMusicActivity.this.progress.hide();
                            LiveMusicActivity.this.networkAction();
                        }
                    }).show();
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "Live Music");
    }

    private void setUpRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mAdapter = new LiveBookingAdapter(getApplicationContext(), getDataSet(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private ArrayList<LiveMusicOther> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6) {
        ArrayList<LiveMusicOther> arrayList7 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList7.add(i, new LiveMusicOther((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), (String) arrayList6.get(i)));
        }
        return arrayList7;
    }
}
