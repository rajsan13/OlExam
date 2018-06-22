package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.OffersModel;
import in.skaipal.kushalm.cuisinicuser.model.addressModel;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DeliveryActivity extends AppCompatActivity implements LocationListener {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static String selectAddress;
    public static String selectMobile;
    public static String selectName;
    private static String updateUrl = APIUrls.deliveryUrl;
    private String Email;
    ArrayList<String> address;
    float amount;
    TextView couponAmount;
    boolean couponApplied = false;
    CheckBox couponBox;
    Button couponCheck;
    TextView couponDetails;
    EditText couponcode;
    CartDatabase db;
    EditText deliveryAddress;
    TextView deliveryAmount;
    float deliveryCharges = 30.0f;
    EditText deliveryName;
    EditText deliveryNumber;
    float finalTotal;
    private Location location;
    private LocationManager locationManager;
    MapView mapView;
    ArrayList<String> mobile;
    ArrayList<String> name;
    TextView orderAmount;
    Button pay;
    Spinner paymentMode;
    private String provider;
    RadioButton rb1;
    RadioButton rb2;
    String restName;
    ArrayList<OffersModel> returnData;
    RadioGroup rg;
    SQLiteDatabase sdb;
    LinearLayout showCoupon;
    private SharedPreferences sp;
    float tot;
    TextView totalAmount;

    public void onLocationChanged(Location location) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setSoftInputMode(3);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView((int) R.layout.activity_home_delivery);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.restName = extras.getString("restName");
            this.amount = extras.getFloat("orderAmount");
        }
        getSupportActionBar().setTitle((CharSequence) "Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        this.db = new CartDatabase(this);
        initializeView();
        this.couponcode.setEnabled(false);
        this.couponCheck.setEnabled(false);
        initMap(bundle);
        this.rb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    DeliveryActivity.this.initMap(bundle);
                    DeliveryActivity.this.deliveryName.setHint("Enter a Name");
                    DeliveryActivity.this.deliveryNumber.setHint("Enter mobile Number");
                }
            }
        });
        this.rb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    DeliveryActivity.this.showDialogBox();
                }
            }
        });
        try {
            addCurrentLocationDetailsToDeliverAddressEditText();
        } catch (Bundle bundle2) {
            bundle2.printStackTrace();
        }
        this.sdb = this.db.getWritableDatabase();
        bundle2 = this.sdb;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Select name, mobile from user where mail ='");
        stringBuilder.append(this.Email);
        stringBuilder.append("'");
        bundle2 = bundle2.rawQuery(stringBuilder.toString(), null);
        try {
            if (bundle2.moveToFirst()) {
                do {
                    this.deliveryName.setText(bundle2.getString(0));
                    this.deliveryNumber.setText(bundle2.getString(1));
                } while (bundle2.moveToNext());
            }
        } catch (Bundle bundle22) {
            bundle22.printStackTrace();
        }
        checkAndSetOrderAmount();
        populatePaymentModeSpinner();
        this.couponBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (DeliveryActivity.this.couponBox.isChecked() != null) {
                    DeliveryActivity.this.startActivityForResult(new Intent(DeliveryActivity.this, OffersSelectActivity.class), 333);
                    DeliveryActivity.this.couponcode.setEnabled(true);
                    DeliveryActivity.this.couponCheck.setEnabled(true);
                    return;
                }
                DeliveryActivity.this.couponcode.setEnabled(false);
                DeliveryActivity.this.couponCheck.setEnabled(false);
                DeliveryActivity.this.couponcode.setText("");
                DeliveryActivity.this.showCoupon.setVisibility(8);
                DeliveryActivity.this.couponApplied = false;
                DeliveryActivity.this.checkAndSetOrderAmount();
            }
        });
        this.pay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (DeliveryActivity.this.couponBox.isChecked()) {
                    if (DeliveryActivity.this.couponcode.getText().toString().equals("")) {
                        DeliveryActivity.this.couponcode.setError("Enter Coupon code");
                    } else {
                        DeliveryActivity.this.applyCoupon(true);
                    }
                }
                String trim = DeliveryActivity.this.deliveryAddress.getText().toString().trim();
                String trim2 = DeliveryActivity.this.deliveryName.getText().toString().trim();
                String trim3 = DeliveryActivity.this.deliveryNumber.getText().toString().trim();
                if (!trim.equals("")) {
                    if (!trim.isEmpty()) {
                        if (!trim2.equals("")) {
                            if (!trim2.isEmpty()) {
                                if (!trim3.equals("")) {
                                    if (!trim3.isEmpty()) {
                                        String stringBuilder;
                                        String trim4;
                                        String replace;
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
                                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        String format = simpleDateFormat.format(new Date());
                                        String format2 = simpleDateFormat2.format(new Date());
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        stringBuilder2.append("ORD_");
                                        stringBuilder2.append(format);
                                        format = stringBuilder2.toString();
                                        String str = "Delivery";
                                        StringBuilder stringBuilder3 = new StringBuilder();
                                        stringBuilder3.append("ITEM_");
                                        stringBuilder3.append(format);
                                        String stringBuilder4 = stringBuilder3.toString();
                                        StringBuilder stringBuilder5;
                                        if (DeliveryActivity.this.amount >= 200.0f) {
                                            DeliveryActivity.this.tot = DeliveryActivity.this.amount + 0.0f;
                                            stringBuilder5 = new StringBuilder();
                                            stringBuilder5.append("");
                                            stringBuilder5.append(DeliveryActivity.this.tot);
                                            stringBuilder = stringBuilder5.toString();
                                        } else {
                                            DeliveryActivity.this.tot = DeliveryActivity.this.amount + DeliveryActivity.this.deliveryCharges;
                                            stringBuilder5 = new StringBuilder();
                                            stringBuilder5.append("");
                                            stringBuilder5.append(DeliveryActivity.this.tot);
                                            stringBuilder = stringBuilder5.toString();
                                        }
                                        String str2 = "nill";
                                        if (DeliveryActivity.this.couponApplied) {
                                            trim4 = DeliveryActivity.this.couponcode.getText().toString().trim();
                                            String charSequence = DeliveryActivity.this.couponAmount.getText().toString();
                                            StringBuilder stringBuilder6 = new StringBuilder();
                                            stringBuilder6.append("- ");
                                            stringBuilder6.append(DeliveryActivity.this.getResources().getString(R.string.Rupee));
                                            stringBuilder6.append(" ");
                                            replace = charSequence.replace(stringBuilder6.toString(), "");
                                        } else {
                                            trim4 = "nill";
                                            replace = "nill";
                                        }
                                        ArrayList arrayList = new ArrayList();
                                        arrayList.add(format2.trim());
                                        arrayList.add(format.trim());
                                        arrayList.add(DeliveryActivity.this.Email.trim());
                                        arrayList.add(trim2.trim());
                                        arrayList.add(trim3.trim());
                                        arrayList.add(trim.trim().replace(",", ";"));
                                        arrayList.add(str.trim());
                                        arrayList.add(stringBuilder4.trim());
                                        arrayList.add(stringBuilder.trim());
                                        arrayList.add(str2.trim());
                                        arrayList.add(trim4.trim());
                                        arrayList.add(replace.trim());
                                        arrayList.add("nill".trim());
                                        arrayList.add("nill".trim());
                                        arrayList.add("nill".trim());
                                        arrayList.add("nill".trim());
                                        try {
                                            ArrayList arrayList2 = new ArrayList();
                                            ArrayList arrayList3 = new ArrayList();
                                            ArrayList arrayList4 = new ArrayList();
                                            ArrayList arrayList5 = new ArrayList();
                                            ArrayList arrayList6 = new ArrayList();
                                            DeliveryActivity.this.sdb = DeliveryActivity.this.db.getWritableDatabase();
                                            SQLiteDatabase sQLiteDatabase = DeliveryActivity.this.sdb;
                                            stringBuilder2 = new StringBuilder();
                                            stringBuilder2.append("select item_category, item_sub_category, item_name, item_quantity from cart where rest_name ='");
                                            stringBuilder2.append(DeliveryActivity.this.Email);
                                            stringBuilder2.append("'");
                                            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder2.toString(), null);
                                            if (rawQuery.moveToFirst()) {
                                                do {
                                                    arrayList2.add(rawQuery.getString(0));
                                                    arrayList3.add(rawQuery.getString(1));
                                                    arrayList4.add(rawQuery.getString(2));
                                                    if (rawQuery.getString(2).contains("Custom Salad")) {
                                                        SQLiteDatabase sQLiteDatabase2 = DeliveryActivity.this.sdb;
                                                        StringBuilder stringBuilder7 = new StringBuilder();
                                                        stringBuilder7.append("select item from salad where name ='");
                                                        stringBuilder7.append(rawQuery.getString(2));
                                                        stringBuilder7.append("'");
                                                        Cursor rawQuery2 = sQLiteDatabase2.rawQuery(stringBuilder7.toString(), null);
                                                        if (rawQuery2.moveToFirst()) {
                                                            do {
                                                                arrayList6.add(rawQuery2.getString(0).replace("[", "{").replace("]", "}").replace(",", "|"));
                                                            } while (rawQuery2.moveToNext());
                                                        }
                                                    } else {
                                                        arrayList6.add("null");
                                                    }
                                                    arrayList5.add(rawQuery.getString(3));
                                                } while (rawQuery.moveToNext());
                                            }
                                            arrayList.add(String.valueOf(arrayList4.size()));
                                            DeliveryActivity.this.networkAction(arrayList, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6);
                                            return;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    }
                                }
                                DeliveryActivity.this.deliveryNumber.setError("Add the mobile number of the receiver");
                                return;
                            }
                        }
                        DeliveryActivity.this.deliveryName.setError("Add the name of the receiver");
                        return;
                    }
                }
                DeliveryActivity.this.deliveryAddress.setError("Add the delivery Address");
            }
        });
        this.couponCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DeliveryActivity.this.applyCoupon(false);
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

    private void networkAction(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<String> arrayList6) throws JSONException {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Placing your order please wait!");
        progressDialog.show();
        final ArrayList arrayList7 = new ArrayList();
        arrayList7.add(arrayList.toString());
        arrayList7.add(arrayList2.toString());
        arrayList7.add(arrayList3.toString());
        arrayList7.add(arrayList4.toString());
        arrayList7.add(arrayList5.toString());
        arrayList7.add(arrayList6.toString());
        final ArrayList arrayList8 = new ArrayList();
        arrayList8.add("orderDetails");
        arrayList8.add("itemCategory");
        arrayList8.add("itemSubCategory");
        arrayList8.add("itemName");
        arrayList8.add("itemQuantity");
        arrayList8.add("saladItems");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, updateUrl, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    System.out.println(str);
                    JSONObject jSONObject = new JSONObject(str);
                    iArr[0] = jSONObject.getInt("success");
                    strArr[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (iArr[0] == 1) {
                        progressDialog.hide();
                        DeliveryActivity.this.deleteFromSqlite(DeliveryActivity.this.Email);
                        str = DeliveryActivity.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Order of ");
                        stringBuilder.append(DeliveryActivity.this.getResources().getString(R.string.Rupee));
                        stringBuilder.append(" ");
                        stringBuilder.append(DeliveryActivity.this.tot);
                        stringBuilder.append(" been placed from ");
                        stringBuilder.append(DeliveryActivity.this.restName);
                        Toast.makeText(str, stringBuilder.toString(), 0).show();
                    } else if (iArr[0] == null) {
                        progressDialog.hide();
                        Toast.makeText(DeliveryActivity.this, strArr[0], 0).show();
                    }
                } catch (String str2) {
                    progressDialog.hide();
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.hide();
                Toast.makeText(DeliveryActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList7.size(); i++) {
                    hashMap.put(arrayList8.get(i), arrayList7.get(i));
                }
                return hashMap;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                hashMap.put(HttpRequest.HEADER_CONTENT_TYPE, HttpRequest.CONTENT_TYPE_FORM);
                return hashMap;
            }
        }, "Delivery");
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("orderDetails ");
        stringBuilder.append(arrayList.toString());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("itemCategory ");
        stringBuilder.append(arrayList2.toString());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("itemSubCategory ");
        stringBuilder.append(arrayList3.toString());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("itemName ");
        stringBuilder.append(arrayList4.toString());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("itemQuantity ");
        stringBuilder.append(arrayList5.toString());
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("saladItems ");
        stringBuilder.append(arrayList6.toString());
        printStream.println(stringBuilder.toString());
    }

    private void showDialogBox() {
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
        r10 = this;
        r0 = new android.app.AlertDialog$Builder;
        r0.<init>(r10);
        r1 = 0;
        r0.setCancelable(r1);
        r2 = r10.getLayoutInflater();
        r3 = 0;
        r4 = 2131427410; // 0x7f0b0052 float:1.8476435E38 double:1.05306506E-314;
        r2 = r2.inflate(r4, r3);
        r0.setView(r2);
        r4 = 2131296667; // 0x7f09019b float:1.8211257E38 double:1.0530004643E-314;
        r4 = r2.findViewById(r4);
        r4 = (android.support.v7.widget.RecyclerView) r4;
        r5 = 2131296670; // 0x7f09019e float:1.8211263E38 double:1.0530004657E-314;
        r5 = r2.findViewById(r5);
        r5 = (android.widget.Button) r5;
        r6 = 2131296668; // 0x7f09019c float:1.821126E38 double:1.053000465E-314;
        r6 = r2.findViewById(r6);
        r6 = (android.widget.Button) r6;
        r7 = 2131296531; // 0x7f090113 float:1.8210981E38 double:1.053000397E-314;
        r2 = r2.findViewById(r7);
        r2 = (android.widget.TextView) r2;
        r7 = 8;
        r2.setVisibility(r7);
        r8 = r10.db;
        r8 = r8.getWritableDatabase();
        r10.sdb = r8;
        r8 = new java.util.ArrayList;
        r8.<init>();
        r10.name = r8;
        r8 = new java.util.ArrayList;
        r8.<init>();
        r10.mobile = r8;
        r8 = new java.util.ArrayList;
        r8.<init>();
        r10.address = r8;
        r8 = r10.sdb;	 Catch:{ Exception -> 0x00b4 }
        r9 = "Select * from address";	 Catch:{ Exception -> 0x00b4 }
        r3 = r8.rawQuery(r9, r3);	 Catch:{ Exception -> 0x00b4 }
        r8 = r3.moveToFirst();	 Catch:{ Exception -> 0x00b4 }
        if (r8 == 0) goto L_0x008f;	 Catch:{ Exception -> 0x00b4 }
    L_0x006c:
        r8 = r10.name;	 Catch:{ Exception -> 0x00b4 }
        r9 = r3.getString(r1);	 Catch:{ Exception -> 0x00b4 }
        r8.add(r9);	 Catch:{ Exception -> 0x00b4 }
        r8 = r10.mobile;	 Catch:{ Exception -> 0x00b4 }
        r9 = 1;	 Catch:{ Exception -> 0x00b4 }
        r9 = r3.getString(r9);	 Catch:{ Exception -> 0x00b4 }
        r8.add(r9);	 Catch:{ Exception -> 0x00b4 }
        r8 = r10.address;	 Catch:{ Exception -> 0x00b4 }
        r9 = 2;	 Catch:{ Exception -> 0x00b4 }
        r9 = r3.getString(r9);	 Catch:{ Exception -> 0x00b4 }
        r8.add(r9);	 Catch:{ Exception -> 0x00b4 }
        r8 = r3.moveToNext();	 Catch:{ Exception -> 0x00b4 }
        if (r8 != 0) goto L_0x006c;	 Catch:{ Exception -> 0x00b4 }
    L_0x008f:
        r3 = new android.support.v7.widget.LinearLayoutManager;	 Catch:{ Exception -> 0x00b4 }
        r8 = r10.getApplicationContext();	 Catch:{ Exception -> 0x00b4 }
        r3.<init>(r8);	 Catch:{ Exception -> 0x00b4 }
        r4.setLayoutManager(r3);	 Catch:{ Exception -> 0x00b4 }
        r3 = new in.skaipal.kushalm.cuisinicuser.adapter.DeliveryAddressSelectAdapter;	 Catch:{ Exception -> 0x00b4 }
        r8 = r10.getApplication();	 Catch:{ Exception -> 0x00b4 }
        r9 = r10.getDataSet();	 Catch:{ Exception -> 0x00b4 }
        r3.<init>(r8, r9);	 Catch:{ Exception -> 0x00b4 }
        r8 = new android.support.v7.widget.DefaultItemAnimator;	 Catch:{ Exception -> 0x00b4 }
        r8.<init>();	 Catch:{ Exception -> 0x00b4 }
        r4.setItemAnimator(r8);	 Catch:{ Exception -> 0x00b4 }
        r4.setAdapter(r3);	 Catch:{ Exception -> 0x00b4 }
        goto L_0x00ba;
    L_0x00b4:
        r2.setVisibility(r1);
        r4.setVisibility(r7);
    L_0x00ba:
        r3 = r10.name;
        r3 = r3.size();
        if (r3 <= 0) goto L_0x00c9;
    L_0x00c2:
        r2.setVisibility(r7);
        r4.setVisibility(r1);
        goto L_0x00cf;
    L_0x00c9:
        r2.setVisibility(r1);
        r4.setVisibility(r7);
    L_0x00cf:
        r0 = r0.create();
        r1 = new in.skaipal.kushalm.cuisinicuser.activity.DeliveryActivity$9;
        r1.<init>(r0);
        r5.setOnClickListener(r1);
        r1 = new in.skaipal.kushalm.cuisinicuser.activity.DeliveryActivity$10;
        r1.<init>(r0);
        r6.setOnClickListener(r1);
        r0.show();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.DeliveryActivity.showDialogBox():void");
    }

    private ArrayList<addressModel> getDataSet() {
        ArrayList<addressModel> arrayList = new ArrayList();
        for (int i = 0; i < this.name.size(); i++) {
            arrayList.add(i, new addressModel((String) this.name.get(i), (String) this.mobile.get(i), (String) this.address.get(i)));
        }
        return arrayList;
    }

    private void addCurrentLocationDetailsToDeliverAddressEditText() throws java.io.IOException {
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
        r6 = this;
        r0 = new android.location.Geocoder;	 Catch:{ Exception -> 0x002b }
        r1 = java.util.Locale.getDefault();	 Catch:{ Exception -> 0x002b }
        r0.<init>(r6, r1);	 Catch:{ Exception -> 0x002b }
        r1 = r6.location;	 Catch:{ Exception -> 0x002b }
        r1 = r1.getLatitude();	 Catch:{ Exception -> 0x002b }
        r3 = r6.location;	 Catch:{ Exception -> 0x002b }
        r3 = r3.getLongitude();	 Catch:{ Exception -> 0x002b }
        r5 = 1;	 Catch:{ Exception -> 0x002b }
        r0 = r0.getFromLocation(r1, r3, r5);	 Catch:{ Exception -> 0x002b }
        r1 = 0;	 Catch:{ Exception -> 0x002b }
        r0 = r0.get(r1);	 Catch:{ Exception -> 0x002b }
        r0 = (android.location.Address) r0;	 Catch:{ Exception -> 0x002b }
        r0 = r0.getAddressLine(r1);	 Catch:{ Exception -> 0x002b }
        r1 = r6.deliveryAddress;	 Catch:{ Exception -> 0x002b }
        r1.setText(r0);	 Catch:{ Exception -> 0x002b }
        goto L_0x0032;
    L_0x002b:
        r0 = "Unable to detect address of your current location";
        r1 = r6.deliveryAddress;
        r1.setHint(r0);
    L_0x0032:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.DeliveryActivity.addCurrentLocationDetailsToDeliverAddressEditText():void");
    }

    private void initMap(Bundle bundle) {
        this.locationManager = (LocationManager) getSystemService("location");
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.requestLocationUpdates("network", 0, 0.0f, this);
            this.location = this.locationManager.getLastKnownLocation("network");
            if (this.location == null) {
                Toast.makeText(this, "Cannot fetch current location!", 1).show();
            }
            this.provider = this.locationManager.getBestProvider(new Criteria(), true);
            this.mapView.onCreate(bundle);
            this.mapView.getMapAsync(new OnMapReadyCallback() {
                public void onMapReady(GoogleMap googleMap) {
                    try {
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(DeliveryActivity.this.location.getLatitude(), DeliveryActivity.this.location.getLongitude())).title("marker"));
                        if (ContextCompat.checkSelfPermission(DeliveryActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(DeliveryActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                            googleMap.setMyLocationEnabled(true);
                            googleMap.setBuildingsEnabled(true);
                            googleMap.getUiSettings().setZoomControlsEnabled(true);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(DeliveryActivity.this.location.getLatitude(), DeliveryActivity.this.location.getLongitude()), 16.0f));
                        }
                    } catch (GoogleMap googleMap2) {
                        googleMap2.printStackTrace();
                    }
                }
            });
        }
    }

    private void applyCoupon(boolean z) {
        if (z) {
            doStuff(0.0f);
        } else {
            doStuff(0.0f);
        }
    }

    private void doStuff(float f) {
        if (this.couponcode.getText().toString().equals("")) {
            this.couponcode.setError("Select an offer");
            return;
        }
        this.showCoupon.setVisibility(0);
        this.couponDetails.setText(this.couponcode.getText().toString().toUpperCase());
        try {
            TextView textView;
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2;
            if (((OffersModel) this.returnData.get(0)).getCondition().equalsIgnoreCase("greater than")) {
                if (((int) this.tot) >= Integer.parseInt(((OffersModel) this.returnData.get(0)).getTotal().replace(getResources().getString(R.string.Rupee), "").trim())) {
                    if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("%")) {
                        f = (this.tot * ((float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue()))) / 100.0f;
                    } else if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("₹")) {
                        f = (float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue());
                    }
                    textView = this.couponAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("- ");
                    stringBuilder.append(getResources().getString(R.string.Rupee));
                    stringBuilder.append(" ");
                    stringBuilder.append(f);
                    textView.setText(stringBuilder.toString());
                    this.finalTotal = this.tot - f;
                    f = this.totalAmount;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(getResources().getString(R.string.Rupee));
                    stringBuilder2.append(" ");
                    stringBuilder2.append(this.finalTotal);
                    f.setText(stringBuilder2.toString());
                    this.couponApplied = true;
                    f = new StringBuilder();
                    f.append("Offer ");
                    f.append(this.couponcode.getText().toString().toUpperCase());
                    f.append(" has been applied");
                    Toast.makeText(this, f.toString(), 0).show();
                    return;
                }
                this.showCoupon.setVisibility(8);
                this.couponBox.setChecked(false);
                this.couponcode.setText("");
                Toast.makeText(this, "Offer creteria not matched", 0).show();
            } else if (!((OffersModel) this.returnData.get(0)).getCondition().equalsIgnoreCase("equals to")) {
            } else {
                if (((int) this.tot) == Integer.parseInt(((OffersModel) this.returnData.get(0)).getTotal().replace(getResources().getString(R.string.Rupee), "").trim())) {
                    if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("%")) {
                        f = (this.tot * ((float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue()))) / 100.0f;
                    } else if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("₹")) {
                        f = (float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue());
                    }
                    textView = this.couponAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("- ");
                    stringBuilder.append(getResources().getString(R.string.Rupee));
                    stringBuilder.append(" ");
                    stringBuilder.append(f);
                    textView.setText(stringBuilder.toString());
                    this.finalTotal = this.tot - f;
                    f = this.totalAmount;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(getResources().getString(R.string.Rupee));
                    stringBuilder2.append(" ");
                    stringBuilder2.append(this.finalTotal);
                    f.setText(stringBuilder2.toString());
                    this.couponApplied = true;
                    f = new StringBuilder();
                    f.append("Offer ");
                    f.append(this.couponcode.getText().toString().toUpperCase());
                    f.append(" has been applied");
                    Toast.makeText(this, f.toString(), 0).show();
                    return;
                }
                this.showCoupon.setVisibility(8);
                this.couponBox.setChecked(false);
                this.couponcode.setText("");
                Toast.makeText(this, "Offer creteria not matched", 0).show();
            }
        } catch (float f2) {
            f2.printStackTrace();
        }
    }

    private void populatePaymentModeSpinner() {
        List arrayList = new ArrayList();
        arrayList.add("Cash on Delivery");
        this.paymentMode.setAdapter(new ArrayAdapter(this, 17367049, arrayList));
    }

    private void checkAndSetOrderAmount() {
        TextView textView = this.orderAmount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(this.amount);
        textView.setText(stringBuilder.toString());
        if (this.amount >= 200.0f) {
            textView = this.deliveryAmount;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.Rupee));
            stringBuilder.append(" 0");
            textView.setText(stringBuilder.toString());
            this.tot = this.amount + 0.0f;
        } else {
            textView = this.deliveryAmount;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.Rupee));
            stringBuilder.append(" ");
            stringBuilder.append(this.deliveryCharges);
            textView.setText(stringBuilder.toString());
            this.tot = this.amount + this.deliveryCharges;
        }
        textView = this.totalAmount;
        stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(this.tot);
        textView.setText(stringBuilder.toString());
    }

    private void deleteFromSqlite(String str) {
        this.sdb = this.db.getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sdb;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Delete from cart where rest_name='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        sQLiteDatabase.execSQL(stringBuilder.toString());
        str = new Builder(this);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Thank you for ordering food from ");
        stringBuilder2.append(getSupportActionBar().getTitle().toString());
        stringBuilder2.append(" Enjoy the food");
        str.setMessage(stringBuilder2.toString());
        str.setCancelable(false);
        str.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface = new Intent(DeliveryActivity.this, CuisinicMenuActivity.class);
                dialogInterface.setFlags(268468224);
                DeliveryActivity.this.startActivity(dialogInterface);
            }
        });
        str = str.create();
        str.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                str.getButton(-2).setTextColor(DeliveryActivity.this.getResources().getColor(R.color.colorPrimary));
                str.getButton(-1).setTextColor(DeliveryActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        str.show();
    }

    @RequiresApi(api = 21)
    private void initializeView() {
        r2 = new int[2][];
        r2[0] = new int[]{-16842910};
        r2[1] = new int[]{16842910};
        ColorStateList colorStateList = new ColorStateList(r2, new int[]{ViewCompat.MEASURED_STATE_MASK, getResources().getColor(R.color.colorPrimary)});
        this.mapView = (MapView) findViewById(R.id.googleMap);
        this.orderAmount = (TextView) findViewById(R.id.orderAmount);
        this.deliveryAmount = (TextView) findViewById(R.id.deliveryAmount);
        this.totalAmount = (TextView) findViewById(R.id.totalAmount);
        this.couponBox = (CheckBox) findViewById(R.id.couponCheckBox);
        this.couponcode = (EditText) findViewById(R.id.couponEditText);
        this.pay = (Button) findViewById(R.id.payButton);
        this.paymentMode = (Spinner) findViewById(R.id.paymentMode);
        this.couponCheck = (Button) findViewById(R.id.couponCheckButton);
        this.showCoupon = (LinearLayout) findViewById(R.id.showCouponDetails);
        this.showCoupon.setVisibility(8);
        this.couponAmount = (TextView) findViewById(R.id.couponAmountTextView);
        this.couponDetails = (TextView) findViewById(R.id.couponDetails);
        this.deliveryAddress = (EditText) findViewById(R.id.deliveryAddress);
        this.deliveryNumber = (EditText) findViewById(R.id.deliveryNumber);
        this.deliveryName = (EditText) findViewById(R.id.deliveryName);
        this.rg = (RadioGroup) findViewById(R.id.rg);
        this.rb1 = (RadioButton) findViewById(R.id.rb1);
        this.rb1.setButtonTintList(colorStateList);
        this.rb1.invalidate();
        this.rb2 = (RadioButton) findViewById(R.id.rb2);
        this.rb2.setButtonTintList(colorStateList);
        this.rb2.invalidate();
        this.couponCheck.setVisibility(0);
    }

    private boolean isGooglePlayServicesAvailable() {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        GooglePlayServicesUtil.getErrorDialog(isGooglePlayServicesAvailable, this, 0).show();
        return false;
    }

    public void onResume() {
        this.mapView.onResume();
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }

    @RequiresApi(api = 19)
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 333) {
            return;
        }
        if (i2 == -1) {
            this.returnData = new ArrayList();
            i = intent.getExtras();
            this.returnData.add(new OffersModel(i.getString("NAME"), "", i.getString("CONDITION"), i.getString("TYPE"), i.getString("VALUE"), "", "", "", i.getString("TOTAL")));
            this.couponcode.setText(((OffersModel) this.returnData.get(0)).getName());
        } else if (i2 == 0) {
            this.couponBox.setChecked(false);
            this.couponcode.setEnabled(false);
            this.couponCheck.setEnabled(false);
        }
    }
}
