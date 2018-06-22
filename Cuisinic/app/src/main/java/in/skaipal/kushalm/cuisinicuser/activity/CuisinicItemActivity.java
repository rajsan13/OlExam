package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.andremion.counterfab.CounterFab;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.adapter.StarterOtherAdapter;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.StarterOther;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CuisinicItemActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static CounterFab fab;
    public static MenuItem menuItem;
    private String Email = null;
    String category;
    CartDatabase db;
    private StarterOtherAdapter mAdapter;
    private Menu menu;
    ArrayList<String> name;
    ArrayList<String> price;
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    SQLiteDatabase sdb;
    private SharedPreferences sp;
    String subCategory;
    int total = 0;
    ArrayList<String> type;
    String url = APIUrls.getMenuUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cuisinic_item);
        getSupportActionBar().setTitle((CharSequence) "Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.category = bundle.getString("category");
            this.subCategory = bundle.getString("sub-category");
        }
        if (this.subCategory.equalsIgnoreCase("none") != null) {
            getSupportActionBar().setTitle(this.category);
        } else {
            bundle = getSupportActionBar();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.category);
            stringBuilder.append("-");
            stringBuilder.append(this.subCategory);
            bundle.setTitle(stringBuilder.toString());
        }
        initialiseViews();
        networkAction();
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        updateFabAndCart();
        fab.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CuisinicItemActivity.this.startActivity(new Intent(CuisinicItemActivity.this, CartActivity.class));
            }
        });
    }

    private void updateFabAndCart() {
        try {
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT item_name FROM cart where item_quantity!=0 and rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            fab.setCount(sQLiteDatabase.rawQuery(stringBuilder.toString(), null).getCount());
            sQLiteDatabase = this.sdb;
            stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT SUM(item_total) FROM cart where rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                this.total = rawQuery.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fab.setCount(0);
        }
    }

    private void networkAction() {
        this.progress.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.category);
        arrayList.add(this.subCategory);
        arrayList.add("0");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("category");
        arrayList2.add("subCategory");
        arrayList2.add(UserSessionManager.KEY_TYPE);
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
                        CuisinicItemActivity.this.name = new ArrayList();
                        CuisinicItemActivity.this.price = new ArrayList();
                        CuisinicItemActivity.this.type = new ArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            CuisinicItemActivity.this.name.add(jSONObject2.getString("itemName"));
                            CuisinicItemActivity.this.price.add(jSONObject2.getString("price"));
                            CuisinicItemActivity.this.type.add(jSONObject2.getString(UserSessionManager.KEY_TYPE));
                        }
                        CuisinicItemActivity.this.progress.hide();
                        CuisinicItemActivity.this.setUpRecyclerView(CuisinicItemActivity.this.name, CuisinicItemActivity.this.price, CuisinicItemActivity.this.type);
                        return;
                    }
                    CuisinicItemActivity.this.progress.hide();
                    Toast.makeText(CuisinicItemActivity.this, "No menu found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    CuisinicItemActivity.this.progress.hide();
                    Context context = CuisinicItemActivity.this;
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
                            CuisinicItemActivity.this.progress.hide();
                            CuisinicItemActivity.this.networkAction();
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
        }, "Hello");
    }

    private void setUpRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mAdapter = new StarterOtherAdapter(getApplicationContext(), this.category, this.subCategory, getDataSet(arrayList, arrayList2, arrayList3));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        fab = (CounterFab) findViewById(R.id.cartFab);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
    }

    private ArrayList<StarterOther> getDataSet(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
        ArrayList<StarterOther> arrayList4 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList4.add(new StarterOther((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList3.get(i)));
        }
        return arrayList4;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        menuItem = menu.findItem(R.id.menuCart);
        MenuItem menuItem = menuItem;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(this.total);
        menuItem.setTitle(stringBuilder.toString());
        this.menu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId != R.id.menuCart) {
            if (itemId == R.id.restGallery) {
                startActivity(new Intent(this, GalleryActivity.class));
            }
            return super.onOptionsItemSelected(menuItem);
        } else {
            startActivity(new Intent(this, CartActivity.class));
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onRestart() {
        super.onRestart();
        updateFabAndCart();
    }

    protected void onResume() {
        super.onResume();
        updateFabAndCart();
    }

    protected void onStart() {
        super.onStart();
    }
}
