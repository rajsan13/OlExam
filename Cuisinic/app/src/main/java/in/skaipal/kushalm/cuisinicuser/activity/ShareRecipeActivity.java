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
import in.skaipal.kushalm.cuisinicuser.adapter.ShareRecipeAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.ShareRecipeOther;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ShareRecipeActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    FloatingActionButton add;
    ArrayList<String> declineStatus;
    ArrayList<String> description;
    ArrayList<String> details;
    ShareRecipeAdapter mAdapter;
    ArrayList<String> name;
    ArrayList<String> price;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    private SharedPreferences sp;
    ArrayList<String> status;
    ArrayList<String> type;
    String url = APIUrls.getRecipeUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_share_recipe);
        getSupportActionBar().setTitle((CharSequence) "Share Recipe");
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initialiseViews();
        networkAction();
        this.add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShareRecipeActivity.this.startActivity(new Intent(ShareRecipeActivity.this.getApplicationContext(), AddRecipeActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
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
                        ShareRecipeActivity.this.name = new ArrayList();
                        ShareRecipeActivity.this.price = new ArrayList();
                        ShareRecipeActivity.this.description = new ArrayList();
                        ShareRecipeActivity.this.details = new ArrayList();
                        ShareRecipeActivity.this.type = new ArrayList();
                        ShareRecipeActivity.this.status = new ArrayList();
                        ShareRecipeActivity.this.declineStatus = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            ShareRecipeActivity.this.name.add(jSONObject2.getString("recipe_name"));
                            ShareRecipeActivity.this.price.add(jSONObject2.getString("recipe_price"));
                            ShareRecipeActivity.this.description.add(jSONObject2.getString("description"));
                            ShareRecipeActivity.this.details.add(jSONObject2.getString("details"));
                            ShareRecipeActivity.this.type.add(jSONObject2.getString(UserSessionManager.KEY_TYPE));
                            ShareRecipeActivity.this.status.add(jSONObject2.getString("is_approved"));
                            ShareRecipeActivity.this.declineStatus.add(jSONObject2.getString("decline_reason"));
                        }
                        ShareRecipeActivity.this.progress.hide();
                        ShareRecipeActivity.this.setUpRecyclerView(ShareRecipeActivity.this.name, ShareRecipeActivity.this.price, ShareRecipeActivity.this.description, ShareRecipeActivity.this.details, ShareRecipeActivity.this.type, ShareRecipeActivity.this.status, ShareRecipeActivity.this.declineStatus);
                        return;
                    }
                    ShareRecipeActivity.this.progress.hide();
                    Toast.makeText(ShareRecipeActivity.this, "No recepies found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    ShareRecipeActivity.this.progress.hide();
                    Context context = ShareRecipeActivity.this;
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
                            ShareRecipeActivity.this.progress.hide();
                            ShareRecipeActivity.this.networkAction();
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
        }, "Share Recipe");
    }

    private void setUpRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mAdapter = new ShareRecipeAdapter(getApplicationContext(), getDataSet(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private ArrayList<ShareRecipeOther> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6, ArrayList<String> arrayList7) {
        ArrayList<ShareRecipeOther> arrayList8 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList8.add(i, new ShareRecipeOther((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), (String) arrayList6.get(i), (String) arrayList7.get(i)));
        }
        return arrayList8;
    }
}
