package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
import in.skaipal.kushalm.cuisinicuser.adapter.OfferAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.OffersModel;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class OfferActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    FloatingActionButton add;
    ArrayList<String> condition;
    ArrayList<String> description;
    OfferAdapter mAdapter;
    ArrayList<String> name;
    ArrayList<String> nearby;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    private SharedPreferences sp;
    ArrayList<String> total;
    ArrayList<String> type;
    String url = APIUrls.offerUrl;
    ArrayList<String> valid;
    ArrayList<String> value;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_live_music);
        getSupportActionBar().setTitle((CharSequence) "Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initialiseViews();
        networkAction();
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        this.add = (FloatingActionButton) findViewById(R.id.addBooking);
        this.add.setVisibility(8);
    }

    private void networkAction() {
        this.progress.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("offer");
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
                        OfferActivity.this.name = new ArrayList();
                        OfferActivity.this.valid = new ArrayList();
                        OfferActivity.this.condition = new ArrayList();
                        OfferActivity.this.type = new ArrayList();
                        OfferActivity.this.value = new ArrayList();
                        OfferActivity.this.nearby = new ArrayList();
                        OfferActivity.this.description = new ArrayList();
                        OfferActivity.this.total = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            OfferActivity.this.name.add(jSONObject2.getString("offer_name"));
                            OfferActivity.this.valid.add(jSONObject2.getString("valid_upto"));
                            OfferActivity.this.condition.add(jSONObject2.getString("offer_condition"));
                            OfferActivity.this.type.add(jSONObject2.getString("offer_type"));
                            OfferActivity.this.value.add(jSONObject2.getString("offer_value"));
                            OfferActivity.this.nearby.add(jSONObject2.getString("show_nearby"));
                            OfferActivity.this.description.add(jSONObject2.getString("offer_description"));
                            OfferActivity.this.total.add(jSONObject2.getString("total_value"));
                        }
                        OfferActivity.this.progress.hide();
                        OfferActivity.this.setUpRecyclerView(OfferActivity.this.name, OfferActivity.this.valid, OfferActivity.this.condition, OfferActivity.this.type, OfferActivity.this.value, OfferActivity.this.nearby, OfferActivity.this.description, OfferActivity.this.total);
                        return;
                    }
                    OfferActivity.this.progress.hide();
                    Toast.makeText(OfferActivity.this, "No offers found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    OfferActivity.this.progress.hide();
                    Context context = OfferActivity.this;
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
                            OfferActivity.this.progress.hide();
                            OfferActivity.this.networkAction();
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
        }, "Offers");
    }

    private void setUpRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mAdapter = new OfferAdapter(getApplicationContext(), getDataSet(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7, arrayList8), null);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private ArrayList<OffersModel> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8) {
        ArrayList arrayList9 = arrayList2;
        ArrayList<OffersModel> arrayList10 = new ArrayList();
        int i = 0;
        while (i < arrayList.size()) {
            ArrayList<String> arrayList11;
            Calendar instance = Calendar.getInstance();
            instance.get(1);
            instance.get(2);
            instance.get(5);
            Date time = instance.getTime();
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse((String) arrayList9.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.compareTo(time) <= 0) {
                if (date.compareTo(time) != 0) {
                    ArrayList<String> arrayList12 = arrayList;
                    arrayList11 = arrayList8;
                    i++;
                    arrayList11 = arrayList2;
                }
            }
            arrayList10.add(new OffersModel((String) arrayList.get(i), (String) arrayList9.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), "1", (String) arrayList6.get(i), (String) arrayList7.get(i), (String) arrayList8.get(i)));
            i++;
            arrayList11 = arrayList2;
        }
        return arrayList10;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return true;
    }
}
