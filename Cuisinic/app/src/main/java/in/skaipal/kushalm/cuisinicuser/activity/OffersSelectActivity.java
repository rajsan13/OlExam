package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.adapter.OfferSelectedAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.OffersModel;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class OffersSelectActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static ArrayList<Boolean> selected = new ArrayList();
    private String Email;
    Button Select;
    ArrayList<String> condition;
    int count = 0;
    ArrayList<String> description;
    int index = 0;
    OfferSelectedAdapter mAdapter;
    ArrayList<String> name;
    ArrayList<String> nearby;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    ArrayList<OffersModel> results;
    private SharedPreferences sp;
    ArrayList<String> total;
    ArrayList<String> type;
    String url = APIUrls.offerUrl;
    ArrayList<String> valid;
    ArrayList<String> value;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_offer_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Offers");
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initialiseViews();
        networkAction();
        this.Select.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = null;
                for (int i = 0; i < OffersSelectActivity.selected.size(); i++) {
                    if (((Boolean) OffersSelectActivity.selected.get(i)).booleanValue()) {
                        OffersSelectActivity offersSelectActivity = OffersSelectActivity.this;
                        offersSelectActivity.count++;
                    }
                }
                if (OffersSelectActivity.this.count > 1) {
                    Toast.makeText(OffersSelectActivity.this, "Only one offer is selectable", 0).show();
                    return;
                }
                while (view < OffersSelectActivity.selected.size()) {
                    if (((Boolean) OffersSelectActivity.selected.get(view)).booleanValue()) {
                        OffersSelectActivity.this.index = view;
                    }
                    view++;
                }
                view = new Intent();
                view.putExtra("NAME", (String) OffersSelectActivity.this.name.get(OffersSelectActivity.this.index));
                view.putExtra("CONDITION", (String) OffersSelectActivity.this.condition.get(OffersSelectActivity.this.index));
                view.putExtra("TOTAL", (String) OffersSelectActivity.this.total.get(OffersSelectActivity.this.index));
                view.putExtra("TYPE", (String) OffersSelectActivity.this.type.get(OffersSelectActivity.this.index));
                view.putExtra("VALUE", (String) OffersSelectActivity.this.value.get(OffersSelectActivity.this.index));
                OffersSelectActivity.this.setResult(-1, view);
                OffersSelectActivity.this.finish();
            }
        });
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        this.Select = (Button) findViewById(R.id.selectOffer);
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
                        OffersSelectActivity.this.name = new ArrayList();
                        OffersSelectActivity.this.valid = new ArrayList();
                        OffersSelectActivity.this.condition = new ArrayList();
                        OffersSelectActivity.this.type = new ArrayList();
                        OffersSelectActivity.this.value = new ArrayList();
                        OffersSelectActivity.this.nearby = new ArrayList();
                        OffersSelectActivity.this.description = new ArrayList();
                        OffersSelectActivity.this.total = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            OffersSelectActivity.this.name.add(jSONObject2.getString("offer_name"));
                            OffersSelectActivity.this.valid.add(jSONObject2.getString("valid_upto"));
                            OffersSelectActivity.this.condition.add(jSONObject2.getString("offer_condition"));
                            OffersSelectActivity.this.type.add(jSONObject2.getString("offer_type"));
                            OffersSelectActivity.this.value.add(jSONObject2.getString("offer_value"));
                            OffersSelectActivity.this.nearby.add(jSONObject2.getString("show_nearby"));
                            OffersSelectActivity.this.description.add(jSONObject2.getString("offer_description"));
                            OffersSelectActivity.this.total.add(jSONObject2.getString("total_value"));
                        }
                        OffersSelectActivity.this.progress.hide();
                        OffersSelectActivity.this.setUpRecyclerView(OffersSelectActivity.this.name, OffersSelectActivity.this.valid, OffersSelectActivity.this.condition, OffersSelectActivity.this.type, OffersSelectActivity.this.value, OffersSelectActivity.this.nearby, OffersSelectActivity.this.description, OffersSelectActivity.this.total);
                        return;
                    }
                    OffersSelectActivity.this.progress.hide();
                    Toast.makeText(OffersSelectActivity.this, "No offers found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    OffersSelectActivity.this.progress.hide();
                    Context context = OffersSelectActivity.this;
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
                            OffersSelectActivity.this.progress.hide();
                            OffersSelectActivity.this.networkAction();
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
        this.mAdapter = new OfferSelectedAdapter(getApplicationContext(), getDataSet(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7, arrayList8), true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private ArrayList<OffersModel> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7, ArrayList<String> arrayList8) {
        this.results = new ArrayList();
        selected.clear();
        boolean z = false;
        int i = 0;
        while (i < arrayList.size()) {
            selected.add(Boolean.valueOf(z));
            r0.results.add(i, new OffersModel((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), "1", (String) arrayList6.get(i), (String) arrayList7.get(i), (String) arrayList8.get(i)));
            i++;
            z = false;
        }
        return r0.results;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(0, null);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
