package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import in.skaipal.kushalm.cuisinicuser.adapter.ContestAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.ContestOther;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ContestActivity extends AppCompatActivity {
    ArrayList<String> contest1stPrize;
    ArrayList<String> contest2ndPrize;
    ArrayList<String> contest3rdPrize;
    ArrayList<String> contestDate;
    ArrayList<String> contestDescription;
    ArrayList<String> contestId;
    ContestAdapter mAdapter;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    String url = APIUrls.getContestUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_contest);
        getSupportActionBar().setTitle((CharSequence) "Contest");
        initialiseViews();
        networkAction();
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
    }

    private void networkAction() {
        this.progress.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("contest");
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
                        ContestActivity.this.contestId = new ArrayList();
                        ContestActivity.this.contestDate = new ArrayList();
                        ContestActivity.this.contestDescription = new ArrayList();
                        ContestActivity.this.contest1stPrize = new ArrayList();
                        ContestActivity.this.contest2ndPrize = new ArrayList();
                        ContestActivity.this.contest3rdPrize = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            ContestActivity.this.contestId.add(jSONObject2.getString("contest_id"));
                            ContestActivity.this.contestDate.add(jSONObject2.getString("contest_date"));
                            ContestActivity.this.contestDescription.add(jSONObject2.getString("description"));
                            ContestActivity.this.contest1stPrize.add(jSONObject2.getString("1st_prize"));
                            ContestActivity.this.contest2ndPrize.add(jSONObject2.getString("2nd_prize"));
                            ContestActivity.this.contest3rdPrize.add(jSONObject2.getString("3rd_prize"));
                        }
                        ContestActivity.this.progress.hide();
                        ContestActivity.this.setUpRecyclerView(ContestActivity.this.contestId, ContestActivity.this.contestDate, ContestActivity.this.contestDescription, ContestActivity.this.contest1stPrize, ContestActivity.this.contest2ndPrize, ContestActivity.this.contest3rdPrize);
                        return;
                    }
                    ContestActivity.this.progress.hide();
                    Toast.makeText(ContestActivity.this, "No contest found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    ContestActivity.this.progress.hide();
                    Context context = ContestActivity.this;
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
                            ContestActivity.this.progress.hide();
                            ContestActivity.this.networkAction();
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
        }, "Contest");
    }

    private void setUpRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mAdapter = new ContestAdapter(getApplicationContext(), getDataSet(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private ArrayList<ContestOther> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6) {
        ArrayList<ContestOther> arrayList7 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList7.add(i, new ContestOther((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), (String) arrayList6.get(i), (String) arrayList3.get(i)));
        }
        return arrayList7;
    }
}
