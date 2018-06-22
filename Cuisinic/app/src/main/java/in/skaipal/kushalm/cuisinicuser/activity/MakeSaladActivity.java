package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.adapter.MultiCheckGenreAdapter;
import in.skaipal.kushalm.cuisinicuser.adapter.saladViewAdapter;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.expand.GenreDataFactory;
import in.skaipal.kushalm.cuisinicuser.model.saladModel;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MakeSaladActivity extends AppCompatActivity {
    public static ArrayList<String> ADDITIONAL = null;
    public static ArrayList<String> CRITERIA = null;
    public static Button Cart = null;
    public static ArrayList<String> DESCRIPTION = null;
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static ArrayList<String> PRIMARY_PRICE;
    public static ArrayList<String> PRIMARY_QUATITY;
    public static saladViewAdapter adapter1;
    static CartDatabase db;
    public static RecyclerView recycler;
    static SQLiteDatabase sdb;
    ArrayList<String> Categories;
    ArrayList<String> Description;
    private String Email;
    ArrayList<ArrayList<String>> ITEM;
    MultiCheckGenreAdapter adapter;
    ArrayList<ArrayList<String>> items;
    ArrayList<String> newCriteria = new ArrayList();
    ArrayList<String> newItem = new ArrayList();
    AVLoadingIndicatorView progress;
    RecyclerView recyclerView;
    private SharedPreferences sp;
    int total = 0;
    String url = APIUrls.ownSalad;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_make_salad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0.0f);
        getSupportActionBar().setTitle((CharSequence) "Make Salad");
        db = new CartDatabase(this);
        sdb = db.getWritableDatabase();
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initialiseViews();
        networkAction();
        Cart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Exception exception;
                ContentValues contentValues;
                StringBuilder stringBuilder;
                String trim;
                Context context;
                StringBuilder stringBuilder2;
                view = MakeSaladActivity.Cart.getText().toString().trim();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Add- ");
                stringBuilder3.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                view.replace(stringBuilder3.toString(), "");
                int i = 0;
                if (MakeSaladActivity.Cart.getText().toString().equalsIgnoreCase("add") == null) {
                    view = new ArrayList();
                    for (int i2 = 0; i2 < MakeSaladActivity.this.adapter.getChoices().size(); i2++) {
                        if (((Boolean) MakeSaladActivity.this.adapter.getChoices().get(i2)).booleanValue()) {
                            StringBuilder stringBuilder4 = new StringBuilder();
                            stringBuilder4.append(((String) MakeSaladActivity.this.newCriteria.get(i2)).trim().replace(" ", "-"));
                            stringBuilder4.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                            stringBuilder4.append(((String) MakeSaladActivity.this.newItem.get(i2)).trim().replace(" ", "-"));
                            view.add(stringBuilder4.toString());
                        }
                    }
                    try {
                        Cursor rawQuery = MakeSaladActivity.sdb.rawQuery("select count(name) from salad", null);
                        if (rawQuery.moveToFirst()) {
                            int i3;
                            int i4 = 0;
                            while (true) {
                                try {
                                    i3 = rawQuery.getInt(0);
                                    try {
                                        if (!rawQuery.moveToNext()) {
                                            break;
                                        }
                                        i4 = i3;
                                    } catch (Exception e) {
                                        exception = e;
                                        i = i3;
                                    }
                                } catch (Exception e2) {
                                    exception = e2;
                                    i = i4;
                                }
                            }
                            i = i3;
                        }
                        rawQuery.close();
                    } catch (Exception e3) {
                        exception = e3;
                        exception.printStackTrace();
                        contentValues = new ContentValues();
                        i++;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Custom Salad_");
                        stringBuilder.append(i);
                        contentValues.put("name", stringBuilder.toString());
                        contentValues.put("item", view.toString());
                        trim = MakeSaladActivity.Cart.getText().toString().trim();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Add- ");
                        stringBuilder.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                        contentValues.put("price", trim.replace(stringBuilder.toString(), ""));
                        MakeSaladActivity.sdb.insert("salad", null, contentValues);
                        MakeSaladActivity.this.showDialogBox();
                        context = MakeSaladActivity.this;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(view.toString());
                        Toast.makeText(context, stringBuilder2.toString(), 1).show();
                        return;
                    }
                    contentValues = new ContentValues();
                    i++;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Custom Salad_");
                    stringBuilder.append(i);
                    contentValues.put("name", stringBuilder.toString());
                    contentValues.put("item", view.toString());
                    trim = MakeSaladActivity.Cart.getText().toString().trim();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Add- ");
                    stringBuilder.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                    contentValues.put("price", trim.replace(stringBuilder.toString(), ""));
                    MakeSaladActivity.sdb.insert("salad", null, contentValues);
                    MakeSaladActivity.this.showDialogBox();
                    context = MakeSaladActivity.this;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(view.toString());
                    Toast.makeText(context, stringBuilder2.toString(), 1).show();
                    return;
                }
                Toast.makeText(MakeSaladActivity.this, "No item has been selected for the salad", 0).show();
                MakeSaladActivity.this.showDialogBox();
            }
        });
    }

    private void showDialogBox() {
        Builder builder = new Builder(this);
        builder.setCancelable(false);
        View inflate = getLayoutInflater().inflate(R.layout.recycler_salad_main, null);
        builder.setView(inflate);
        recycler = (RecyclerView) inflate.findViewById(R.id.ownSaladRecyclerView);
        Button button = (Button) inflate.findViewById(R.id.makeAnotherSalad);
        Button button2 = (Button) inflate.findViewById(R.id.addSaladToCart);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.closeDialog);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter1 = new saladViewAdapter(this, getDataSet());
        recycler.setAdapter(adapter1);
        final AlertDialog create = builder.create();
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MakeSaladActivity.this.adapter.clearChoices();
                MakeSaladActivity.Cart.setText("ADD");
                create.dismiss();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MakeSaladActivity.this.adapter.clearChoices();
                MakeSaladActivity.Cart.setText("ADD");
                create.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MakeSaladActivity.this.checkDB(MakeSaladActivity.adapter1.getAll());
            }
        });
        create.show();
    }

    private void checkDB(ArrayList<saladModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            SQLiteDatabase sQLiteDatabase = sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Select * from cart where rest_name ='");
            stringBuilder.append(this.Email);
            stringBuilder.append("' and item_name ='");
            stringBuilder.append(((saladModel) arrayList.get(i)).getName().trim());
            stringBuilder.append("'and item_category ='Make Salad' and item_sub_category ='none'");
            String str = null;
            if (sQLiteDatabase.rawQuery(stringBuilder.toString(), null).getCount() <= 0) {
                insertToDB((saladModel) arrayList.get(i));
            } else {
                sQLiteDatabase = sdb;
                stringBuilder = new StringBuilder();
                stringBuilder.append("select item_quantity from cart where rest_name ='");
                stringBuilder.append(this.Email);
                stringBuilder.append("' and item_name ='");
                stringBuilder.append(((saladModel) arrayList.get(i)).getName().trim());
                stringBuilder.append("' and item_category ='Make Salad' and item_sub_category ='none'");
                Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
                if (rawQuery.moveToFirst()) {
                    do {
                        str = rawQuery.getString(0);
                    } while (rawQuery.moveToNext());
                }
                if (str.equals(((saladModel) arrayList.get(i)).getQuantity().trim())) {
                    Toast.makeText(this, "Item with same quantity already exists", 0).show();
                } else {
                    updateDB((saladModel) arrayList.get(i));
                }
            }
        }
        startActivity(new Intent(this, CartActivity.class));
        finish();
    }

    private void updateDB(saladModel saladmodel) {
        String[] strArr = new String[]{this.Email, saladmodel.getName().trim(), "Make Salad", "none"};
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_quantity", saladmodel.getQuantity().trim());
        int parseInt = Integer.parseInt(saladmodel.getPrice().trim()) * Integer.parseInt(saladmodel.getQuantity().trim());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(parseInt);
        contentValues.put("item_total", stringBuilder.toString());
        sdb.update("cart", contentValues, "rest_name=? AND item_name=? AND item_category=? AND item_sub_category=?", strArr);
    }

    private void insertToDB(saladModel saladmodel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("rest_name", this.Email);
        contentValues.put("item_category", "Make Salad");
        contentValues.put("item_sub_category", "none");
        contentValues.put("item_name", saladmodel.getName().trim());
        contentValues.put("item_price", saladmodel.getPrice().trim());
        contentValues.put("item_quantity", saladmodel.getQuantity().trim());
        int parseInt = Integer.parseInt(saladmodel.getPrice().trim()) * Integer.parseInt(saladmodel.getQuantity().trim());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(parseInt);
        contentValues.put("item_total", stringBuilder.toString());
        sdb.insert("cart", null, contentValues);
    }

    public static java.util.ArrayList<in.skaipal.kushalm.cuisinicuser.model.saladModel> getDataSet() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = new java.util.ArrayList;
        r1.<init>();
        r2 = new java.util.ArrayList;
        r2.<init>();
        r3 = db;
        r3 = r3.getWritableDatabase();
        sdb = r3;
        r3 = 0;
        r4 = sdb;	 Catch:{ Exception -> 0x0044 }
        r5 = "select * from salad";	 Catch:{ Exception -> 0x0044 }
        r6 = 0;	 Catch:{ Exception -> 0x0044 }
        r4 = r4.rawQuery(r5, r6);	 Catch:{ Exception -> 0x0044 }
        r5 = r4.moveToFirst();	 Catch:{ Exception -> 0x0044 }
        if (r5 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0044 }
    L_0x0027:
        r5 = r4.getString(r3);	 Catch:{ Exception -> 0x0044 }
        r0.add(r5);	 Catch:{ Exception -> 0x0044 }
        r5 = 1;	 Catch:{ Exception -> 0x0044 }
        r5 = r4.getString(r5);	 Catch:{ Exception -> 0x0044 }
        r2.add(r5);	 Catch:{ Exception -> 0x0044 }
        r5 = 2;	 Catch:{ Exception -> 0x0044 }
        r5 = r4.getString(r5);	 Catch:{ Exception -> 0x0044 }
        r1.add(r5);	 Catch:{ Exception -> 0x0044 }
        r5 = r4.moveToNext();	 Catch:{ Exception -> 0x0044 }
        if (r5 != 0) goto L_0x0027;
    L_0x0044:
        r4 = new java.util.ArrayList;
        r4.<init>();
    L_0x0049:
        r5 = r0.size();
        if (r3 >= r5) goto L_0x006e;
    L_0x004f:
        r5 = new in.skaipal.kushalm.cuisinicuser.model.saladModel;
        r6 = r0.get(r3);
        r6 = (java.lang.String) r6;
        r7 = r1.get(r3);
        r7 = (java.lang.String) r7;
        r8 = r2.get(r3);
        r8 = (java.lang.String) r8;
        r9 = "1";
        r5.<init>(r6, r7, r8, r9);
        r4.add(r3, r5);
        r3 = r3 + 1;
        goto L_0x0049;
    L_0x006e:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.MakeSaladActivity.getDataSet():java.util.ArrayList<in.skaipal.kushalm.cuisinicuser.model.saladModel>");
    }

    private void networkAction() {
        this.progress.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("make_salad");
        CRITERIA = new ArrayList();
        PRIMARY_PRICE = new ArrayList();
        PRIMARY_QUATITY = new ArrayList();
        ADDITIONAL = new ArrayList();
        DESCRIPTION = new ArrayList();
        this.ITEM = new ArrayList();
        final String[] strArr = new String[1];
        final String[] strArr2 = new String[1];
        final View findViewById = findViewById(16908290);
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    strArr[0] = jSONObject.getString("success");
                    strArr2[0] = jSONObject.getString("result");
                    if (strArr[0].equals("1") != null) {
                        str = jSONObject.getJSONArray("product");
                        for (int i = 0; i < str.length(); i++) {
                            JSONObject jSONObject2 = str.getJSONObject(i);
                            MakeSaladActivity.CRITERIA.add(jSONObject2.getString("criteria"));
                            MakeSaladActivity.PRIMARY_PRICE.add(jSONObject2.getString("primary_price"));
                            MakeSaladActivity.PRIMARY_QUATITY.add(jSONObject2.getString("primary_quantity"));
                            MakeSaladActivity.ADDITIONAL.add(jSONObject2.getString("additional"));
                            JSONArray jSONArray = jSONObject2.getJSONArray("item");
                            ArrayList arrayList = new ArrayList();
                            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                arrayList.add(jSONArray.getString(i2));
                                MakeSaladActivity.this.newCriteria.add(MakeSaladActivity.CRITERIA.get(i));
                                MakeSaladActivity.this.newItem.add(arrayList.get(i2));
                            }
                            MakeSaladActivity.this.ITEM.add(arrayList);
                            ArrayList arrayList2;
                            StringBuilder stringBuilder;
                            if (((String) MakeSaladActivity.ADDITIONAL.get(i)).equals("0")) {
                                arrayList2 = MakeSaladActivity.DESCRIPTION;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Any ");
                                stringBuilder.append((String) MakeSaladActivity.PRIMARY_QUATITY.get(i));
                                stringBuilder.append(" for ");
                                stringBuilder.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                                stringBuilder.append(" ");
                                stringBuilder.append((String) MakeSaladActivity.PRIMARY_PRICE.get(i));
                                stringBuilder.append(" and No additional");
                                arrayList2.add(stringBuilder.toString());
                            } else {
                                arrayList2 = MakeSaladActivity.DESCRIPTION;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Any ");
                                stringBuilder.append((String) MakeSaladActivity.PRIMARY_QUATITY.get(i));
                                stringBuilder.append(" for ");
                                stringBuilder.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                                stringBuilder.append(" ");
                                stringBuilder.append((String) MakeSaladActivity.PRIMARY_PRICE.get(i));
                                stringBuilder.append(" and Additional extra ");
                                stringBuilder.append(MakeSaladActivity.this.getResources().getString(R.string.Rupee));
                                stringBuilder.append(" ");
                                stringBuilder.append((String) MakeSaladActivity.ADDITIONAL.get(i));
                                arrayList2.add(stringBuilder.toString());
                            }
                        }
                        MakeSaladActivity.this.progress.hide();
                        MakeSaladActivity.this.setRecyclerView(MakeSaladActivity.CRITERIA, MakeSaladActivity.DESCRIPTION, MakeSaladActivity.PRIMARY_PRICE, MakeSaladActivity.PRIMARY_QUATITY, MakeSaladActivity.ADDITIONAL, MakeSaladActivity.this.ITEM);
                        return;
                    }
                    MakeSaladActivity.this.progress.hide();
                    Toast.makeText(MakeSaladActivity.this, "No menu found", 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    MakeSaladActivity.this.progress.hide();
                    Context context = MakeSaladActivity.this;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(str2.toString());
                    Toast.makeText(context, stringBuilder2.toString(), 0).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if ((volleyError instanceof NoConnectionError) != null) {
                    Snackbar.make(findViewById, (CharSequence) "Connection Error", 0).setAction((CharSequence) "RETRY", new OnClickListener() {
                        public void onClick(View view) {
                            MakeSaladActivity.this.progress.hide();
                            MakeSaladActivity.this.networkAction();
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
        }, "make_salad");
    }

    private void setRecyclerView(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<ArrayList<String>> arrayList6) {
        this.adapter = new MultiCheckGenreAdapter(GenreDataFactory.makeMultiCheckGenres(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6), this);
        this.recyclerView.setAdapter(this.adapter);
    }

    private void initialiseViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.make_salad_recycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cart = (Button) findViewById(R.id.makeSaladCartButton);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.adapter.onSaveInstanceState(bundle);
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.adapter.onRestoreInstanceState(bundle);
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem = menuItem.getItemId();
        if (menuItem != 16908332) {
            if (menuItem != R.id.clearCart) {
                return true;
            }
            this.adapter.clearChoices();
            Cart.setText("ADD");
        }
        onBackPressed();
        return true;
    }
}
