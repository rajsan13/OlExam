package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewOrderViewActivity extends AppCompatActivity {
    private static final String KEY_ITEM_CATEGORY = "item_category";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_SUB_CATEGORY = "item_sub_category";
    private static final String KEY_QUANTITY = "item_quantity";
    private static final String TABLE_NEW_CART = "newCart";
    String ItemId;
    String OrderNo;
    String Status;
    TableRow TR;
    TableRow TR1;
    TableRow TR2;
    Button accept;
    TextView address;
    LinearLayout addressLayout;
    TextView bookingDate;
    TextView bookingTime;
    TextView companyTV;
    CartDatabase db;
    Button decline;
    float gTotal;
    ArrayList<String> itemCategoryList;
    ArrayList<String> itemNameList;
    ArrayList<String> itemQuantityList;
    ArrayList<String> itemSaladList;
    ArrayList<String> itemSubCategory;
    LinearLayout loadingLayout;
    TextView members;
    TextView mobile;
    TextView name;
    TextView orderCoupon;
    TextView orderDiscout;
    TextView orderNumber;
    TextView orderPaidPrice;
    TextView orderPrice;
    TextView orderTime;
    SQLiteDatabase sdb;
    LinearLayout tableDetails;
    TextView tableNumber;
    String timestamp = null;
    TableLayout tl;
    TableRow tr;
    String url = APIUrls.getItemDetailsUrl;
    String url_update = APIUrls.updateOrderStatusUrl;
    TextView valueTV;
    TextView valueTV1;
    TextView valueTV2;

    @RequiresApi(api = 23)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_new_order_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.db = new CartDatabase(this);
        initializeViews();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.Status = bundle.getString("status");
            if (this.Status.equals("open")) {
                this.accept.setText("Process");
                this.accept.setVisibility(8);
            } else if (this.Status.equals("processed")) {
                this.accept.setText("Complete");
                this.accept.setVisibility(8);
                this.decline.setVisibility(8);
            } else if (this.Status.equals("completed")) {
                this.accept.setVisibility(8);
                this.decline.setVisibility(8);
            } else if (this.Status.equals("cancelled")) {
                this.accept.setVisibility(8);
                this.decline.setVisibility(8);
            }
            this.timestamp = bundle.getString("time");
            this.orderTime.setText(this.timestamp);
            this.OrderNo = bundle.getString("orderNo");
            this.name.setText(bundle.getString("name"));
            this.mobile.setText(bundle.getString("mobile"));
            TextView textView = this.orderNumber;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Order: ");
            stringBuilder.append(this.OrderNo);
            textView.setText(stringBuilder.toString());
            textView = this.orderPrice;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.Rupee));
            stringBuilder.append(" ");
            stringBuilder.append(bundle.getString("orderPrice"));
            textView.setText(stringBuilder.toString());
            if (bundle.getString("orderPaidPrice").equals("nill")) {
                this.orderPaidPrice.setText(bundle.getString("orderPaidPrice"));
            } else {
                textView = this.orderPaidPrice;
                stringBuilder = new StringBuilder();
                stringBuilder.append(getResources().getString(R.string.Rupee));
                stringBuilder.append(" ");
                stringBuilder.append(bundle.getString("orderPaidPrice"));
                textView.setText(stringBuilder.toString());
            }
            this.orderCoupon.setText(bundle.getString("orderCoupon"));
            if (bundle.getString("orderDiscount").equals("nill")) {
                this.orderDiscout.setText(bundle.getString("orderDiscount"));
            } else {
                textView = this.orderDiscout;
                stringBuilder = new StringBuilder();
                stringBuilder.append(getResources().getString(R.string.Rupee));
                stringBuilder.append(" ");
                stringBuilder.append(bundle.getString("orderDiscount"));
                textView.setText(stringBuilder.toString());
            }
            this.ItemId = bundle.getString("itemId");
            if (bundle.getString(UserSessionManager.KEY_TYPE).equalsIgnoreCase("table")) {
                this.tableDetails.setVisibility(0);
                this.addressLayout.setVisibility(8);
                this.bookingTime.setText(bundle.getString("orderTime"));
                this.bookingDate.setText(bundle.getString("orderDate"));
                this.tableNumber.setText(bundle.getString("tableNumber"));
                this.members.setText(bundle.getString("members"));
            } else {
                this.tableDetails.setVisibility(8);
                this.addressLayout.setVisibility(0);
                this.address.setText(bundle.getString("address"));
            }
        }
        networkAction();
        this.accept.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = NewOrderViewActivity.this.accept.getText().toString().trim();
                if (view.equalsIgnoreCase("process")) {
                    view = new Builder(NewOrderViewActivity.this);
                    View inflate = NewOrderViewActivity.this.getLayoutInflater().inflate(R.layout.alert_process_order, null);
                    view.setView(inflate);
                    final EditText editText = (EditText) inflate.findViewById(R.id.timeOne);
                    final EditText editText2 = (EditText) inflate.findViewById(R.id.timeTwo);
                    Button button = (Button) inflate.findViewById(R.id.doneButton);
                    Button button2 = (Button) inflate.findViewById(R.id.cancelButton);
                    final LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.progress);
                    view = view.create();
                    view.show();
                    view.setCancelable(false);
                    final View view2 = view;
                    button.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (editText.getText().toString().isEmpty() != null) {
                                editText.setError("Enter time 1");
                            } else if (editText2.getText().toString().isEmpty() != null) {
                                editText2.setError("Enter time 2");
                            } else {
                                view = Integer.parseInt(editText.getText().toString().trim());
                                int parseInt = Integer.parseInt(editText2.getText().toString().trim());
                                if (view <= parseInt) {
                                    if (view != parseInt) {
                                        linearLayout.setVisibility(0);
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("");
                                        stringBuilder.append(view);
                                        stringBuilder.append("-");
                                        stringBuilder.append(parseInt);
                                        NewOrderViewActivity.this.networkActionUpdate(NewOrderViewActivity.this.OrderNo, "processed", stringBuilder.toString(), view2, linearLayout);
                                        return;
                                    }
                                }
                                Toast.makeText(NewOrderViewActivity.this, "Time two should be greater than Time 1", 0).show();
                            }
                        }
                    });
                    button2.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            view.dismiss();
                        }
                    });
                } else if (view.equalsIgnoreCase("complete") != null) {
                    NewOrderViewActivity.this.networkActionUpdate(NewOrderViewActivity.this.OrderNo, "completed", "1");
                }
            }
        });
        this.decline.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Builder(NewOrderViewActivity.this);
                View inflate = NewOrderViewActivity.this.getLayoutInflater().inflate(R.layout.alert_cancle_order, null);
                view.setView(inflate);
                final EditText editText = (EditText) inflate.findViewById(R.id.reason);
                Button button = (Button) inflate.findViewById(R.id.doneButton);
                Button button2 = (Button) inflate.findViewById(R.id.cancelButton);
                final LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.progress);
                view = view.create();
                view.show();
                view.setCancelable(false);
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().isEmpty() != null) {
                            editText.setError("Please provide the reason");
                            return;
                        }
                        view = new StringBuilder();
                        view.append(editText.getText().toString().trim());
                        view.append(" (user)");
                        String stringBuilder = view.toString();
                        linearLayout.setVisibility(0);
                        NewOrderViewActivity.this.networkActionUpdate(NewOrderViewActivity.this.OrderNo, "cancelled", stringBuilder, view, linearLayout);
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        view.dismiss();
                    }
                });
            }
        });
    }

    private void networkActionUpdate(String str, String str2, String str3) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Alert");
        progressDialog.setMessage("Changing orders status to complete please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("orderId");
        arrayList2.add("status");
        arrayList2.add("value");
        str2 = new int[1];
        str = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url_update, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    System.out.println(str);
                    JSONObject jSONObject = new JSONObject(str);
                    str2[0] = jSONObject.getInt("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (str2[0] == 1) {
                        progressDialog.dismiss();
                        NewOrderViewActivity.this.onBackPressed();
                    } else if (str2[0] == null) {
                        Toast.makeText(NewOrderViewActivity.this, str[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(NewOrderViewActivity.this, "An Error Occurred", 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "OrderItemsUpdate");
    }

    private void networkAction() {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.ItemId);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("ItemId");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(java.lang.String r7) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
                /*
                r6 = this;
                r0 = 8;
                r1 = java.lang.System.out;	 Catch:{ Exception -> 0x0240 }
                r1.println(r7);	 Catch:{ Exception -> 0x0240 }
                r1 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0240 }
                r1.<init>(r7);	 Catch:{ Exception -> 0x0240 }
                r7 = r1;	 Catch:{ Exception -> 0x0240 }
                r2 = "success";	 Catch:{ Exception -> 0x0240 }
                r2 = r1.getInt(r2);	 Catch:{ Exception -> 0x0240 }
                r3 = 0;	 Catch:{ Exception -> 0x0240 }
                r7[r3] = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = r0;	 Catch:{ Exception -> 0x0240 }
                r2 = "message";	 Catch:{ Exception -> 0x0240 }
                r2 = r1.getString(r2);	 Catch:{ Exception -> 0x0240 }
                r7[r3] = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = r1;	 Catch:{ Exception -> 0x0240 }
                r7 = r7[r3];	 Catch:{ Exception -> 0x0240 }
                r2 = 1;	 Catch:{ Exception -> 0x0240 }
                if (r7 != r2) goto L_0x0225;	 Catch:{ Exception -> 0x0240 }
            L_0x0028:
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r7 = r7.loadingLayout;	 Catch:{ Exception -> 0x0240 }
                r7.setVisibility(r0);	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r2.<init>();	 Catch:{ Exception -> 0x0240 }
                r7.itemNameList = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r2.<init>();	 Catch:{ Exception -> 0x0240 }
                r7.itemQuantityList = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r2.<init>();	 Catch:{ Exception -> 0x0240 }
                r7.itemCategoryList = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r2.<init>();	 Catch:{ Exception -> 0x0240 }
                r7.itemSubCategory = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r2 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r2.<init>();	 Catch:{ Exception -> 0x0240 }
                r7.itemSaladList = r2;	 Catch:{ Exception -> 0x0240 }
                r7 = "product";	 Catch:{ Exception -> 0x0240 }
                r7 = r1.getJSONArray(r7);	 Catch:{ Exception -> 0x0240 }
                r1 = r3;	 Catch:{ Exception -> 0x0240 }
            L_0x0063:
                r2 = r7.length();	 Catch:{ Exception -> 0x0240 }
                if (r1 >= r2) goto L_0x00b6;
            L_0x0069:
                r2 = r7.getJSONObject(r1);	 Catch:{ JSONException -> 0x00af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ JSONException -> 0x00af }
                r4 = r4.itemNameList;	 Catch:{ JSONException -> 0x00af }
                r5 = "item_name";	 Catch:{ JSONException -> 0x00af }
                r5 = r2.getString(r5);	 Catch:{ JSONException -> 0x00af }
                r4.add(r5);	 Catch:{ JSONException -> 0x00af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ JSONException -> 0x00af }
                r4 = r4.itemQuantityList;	 Catch:{ JSONException -> 0x00af }
                r5 = "item_quantity";	 Catch:{ JSONException -> 0x00af }
                r5 = r2.getString(r5);	 Catch:{ JSONException -> 0x00af }
                r4.add(r5);	 Catch:{ JSONException -> 0x00af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ JSONException -> 0x00af }
                r4 = r4.itemCategoryList;	 Catch:{ JSONException -> 0x00af }
                r5 = "item_category";	 Catch:{ JSONException -> 0x00af }
                r5 = r2.getString(r5);	 Catch:{ JSONException -> 0x00af }
                r4.add(r5);	 Catch:{ JSONException -> 0x00af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ JSONException -> 0x00af }
                r4 = r4.itemSubCategory;	 Catch:{ JSONException -> 0x00af }
                r5 = "item_sub_category";	 Catch:{ JSONException -> 0x00af }
                r5 = r2.getString(r5);	 Catch:{ JSONException -> 0x00af }
                r4.add(r5);	 Catch:{ JSONException -> 0x00af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ JSONException -> 0x00af }
                r4 = r4.itemSaladList;	 Catch:{ JSONException -> 0x00af }
                r5 = "salad";	 Catch:{ JSONException -> 0x00af }
                r2 = r2.getString(r5);	 Catch:{ JSONException -> 0x00af }
                r4.add(r2);	 Catch:{ JSONException -> 0x00af }
                goto L_0x00b3;
            L_0x00af:
                r2 = move-exception;
                r2.printStackTrace();	 Catch:{ Exception -> 0x0240 }
            L_0x00b3:
                r1 = r1 + 1;	 Catch:{ Exception -> 0x0240 }
                goto L_0x0063;	 Catch:{ Exception -> 0x0240 }
            L_0x00b6:
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r1 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r1 = r1.db;	 Catch:{ Exception -> 0x0240 }
                r1 = r1.getWritableDatabase();	 Catch:{ Exception -> 0x0240 }
                r7.sdb = r1;	 Catch:{ Exception -> 0x0240 }
                r7 = 0;
                r1 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r1 = r1.sdb;	 Catch:{ Exception -> 0x0145 }
                r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0145 }
                r2.<init>();	 Catch:{ Exception -> 0x0145 }
                r4 = "delete from newCart where item_id = '";	 Catch:{ Exception -> 0x0145 }
                r2.append(r4);	 Catch:{ Exception -> 0x0145 }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r4 = r4.ItemId;	 Catch:{ Exception -> 0x0145 }
                r2.append(r4);	 Catch:{ Exception -> 0x0145 }
                r4 = "'";	 Catch:{ Exception -> 0x0145 }
                r2.append(r4);	 Catch:{ Exception -> 0x0145 }
                r2 = r2.toString();	 Catch:{ Exception -> 0x0145 }
                r1.execSQL(r2);	 Catch:{ Exception -> 0x0145 }
                r1 = r3;	 Catch:{ Exception -> 0x0145 }
            L_0x00e5:
                r2 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r2 = r2.itemNameList;	 Catch:{ Exception -> 0x0145 }
                r2 = r2.size();	 Catch:{ Exception -> 0x0145 }
                if (r1 >= r2) goto L_0x01b3;	 Catch:{ Exception -> 0x0145 }
            L_0x00ef:
                r2 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0145 }
                r2.<init>();	 Catch:{ Exception -> 0x0145 }
                r4 = "item_id";	 Catch:{ Exception -> 0x0145 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.ItemId;	 Catch:{ Exception -> 0x0145 }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x0145 }
                r4 = "item_category";	 Catch:{ Exception -> 0x0145 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.itemCategoryList;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x0145 }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x0145 }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x0145 }
                r4 = "item_sub_category";	 Catch:{ Exception -> 0x0145 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.itemSubCategory;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x0145 }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x0145 }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x0145 }
                r4 = "item_name";	 Catch:{ Exception -> 0x0145 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.itemNameList;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x0145 }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x0145 }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x0145 }
                r4 = "item_quantity";	 Catch:{ Exception -> 0x0145 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.itemQuantityList;	 Catch:{ Exception -> 0x0145 }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x0145 }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x0145 }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x0145 }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0145 }
                r4 = r4.sdb;	 Catch:{ Exception -> 0x0145 }
                r5 = "newCart";	 Catch:{ Exception -> 0x0145 }
                r4.insert(r5, r7, r2);	 Catch:{ Exception -> 0x0145 }
                r1 = r1 + 1;
                goto L_0x00e5;
            L_0x0145:
                r1 = "CREATE TABLE IF NOT EXISTS newCart(item_id TEXT,item_category TEXT,item_sub_category TEXT,item_name TEXT,item_quantity TEXT)";	 Catch:{ Exception -> 0x01af }
                r2 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r2 = r2.sdb;	 Catch:{ Exception -> 0x01af }
                r2.execSQL(r1);	 Catch:{ Exception -> 0x01af }
                r1 = r3;	 Catch:{ Exception -> 0x01af }
            L_0x014f:
                r2 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r2 = r2.itemNameList;	 Catch:{ Exception -> 0x01af }
                r2 = r2.size();	 Catch:{ Exception -> 0x01af }
                if (r1 >= r2) goto L_0x01b3;	 Catch:{ Exception -> 0x01af }
            L_0x0159:
                r2 = new android.content.ContentValues;	 Catch:{ Exception -> 0x01af }
                r2.<init>();	 Catch:{ Exception -> 0x01af }
                r4 = "item_id";	 Catch:{ Exception -> 0x01af }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r5 = r5.ItemId;	 Catch:{ Exception -> 0x01af }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x01af }
                r4 = "item_category";	 Catch:{ Exception -> 0x01af }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r5 = r5.itemCategoryList;	 Catch:{ Exception -> 0x01af }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x01af }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x01af }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x01af }
                r4 = "item_sub_category";	 Catch:{ Exception -> 0x01af }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r5 = r5.itemSubCategory;	 Catch:{ Exception -> 0x01af }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x01af }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x01af }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x01af }
                r4 = "item_name";	 Catch:{ Exception -> 0x01af }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r5 = r5.itemNameList;	 Catch:{ Exception -> 0x01af }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x01af }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x01af }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x01af }
                r4 = "item_quantity";	 Catch:{ Exception -> 0x01af }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r5 = r5.itemQuantityList;	 Catch:{ Exception -> 0x01af }
                r5 = r5.get(r1);	 Catch:{ Exception -> 0x01af }
                r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x01af }
                r2.put(r4, r5);	 Catch:{ Exception -> 0x01af }
                r4 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x01af }
                r4 = r4.sdb;	 Catch:{ Exception -> 0x01af }
                r5 = "newCart";	 Catch:{ Exception -> 0x01af }
                r4.insert(r5, r7, r2);	 Catch:{ Exception -> 0x01af }
                r1 = r1 + 1;
                goto L_0x014f;
            L_0x01af:
                r1 = move-exception;
                r1.printStackTrace();	 Catch:{ Exception -> 0x0240 }
            L_0x01b3:
                r1 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0240 }
                r1.<init>();	 Catch:{ Exception -> 0x0240 }
                r2 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0220 }
                r2 = r2.sdb;	 Catch:{ Exception -> 0x0220 }
                r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220 }
                r4.<init>();	 Catch:{ Exception -> 0x0220 }
                r5 = "select Distinct(item_category) from newCart where item_id='";	 Catch:{ Exception -> 0x0220 }
                r4.append(r5);	 Catch:{ Exception -> 0x0220 }
                r5 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0220 }
                r5 = r5.ItemId;	 Catch:{ Exception -> 0x0220 }
                r4.append(r5);	 Catch:{ Exception -> 0x0220 }
                r5 = "'";	 Catch:{ Exception -> 0x0220 }
                r4.append(r5);	 Catch:{ Exception -> 0x0220 }
                r4 = r4.toString();	 Catch:{ Exception -> 0x0220 }
                r7 = r2.rawQuery(r4, r7);	 Catch:{ Exception -> 0x0220 }
                r2 = r7.moveToFirst();	 Catch:{ Exception -> 0x0220 }
                if (r2 == 0) goto L_0x01ed;	 Catch:{ Exception -> 0x0220 }
            L_0x01e0:
                r2 = r7.getString(r3);	 Catch:{ Exception -> 0x0220 }
                r1.add(r2);	 Catch:{ Exception -> 0x0220 }
                r2 = r7.moveToNext();	 Catch:{ Exception -> 0x0220 }
                if (r2 != 0) goto L_0x01e0;	 Catch:{ Exception -> 0x0220 }
            L_0x01ed:
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0220 }
                r7.addHeaders();	 Catch:{ Exception -> 0x0220 }
                r7 = new java.util.HashSet;	 Catch:{ Exception -> 0x0220 }
                r7.<init>(r1);	 Catch:{ Exception -> 0x0220 }
                r2 = java.lang.System.out;	 Catch:{ Exception -> 0x0220 }
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220 }
                r3.<init>();	 Catch:{ Exception -> 0x0220 }
                r4 = "Values in Set:";	 Catch:{ Exception -> 0x0220 }
                r3.append(r4);	 Catch:{ Exception -> 0x0220 }
                r3.append(r7);	 Catch:{ Exception -> 0x0220 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x0220 }
                r2.println(r3);	 Catch:{ Exception -> 0x0220 }
                r2 = java.lang.System.out;	 Catch:{ Exception -> 0x0220 }
                r3 = "------------------------";	 Catch:{ Exception -> 0x0220 }
                r2.println(r3);	 Catch:{ Exception -> 0x0220 }
                r1.clear();	 Catch:{ Exception -> 0x0220 }
                r1.addAll(r7);	 Catch:{ Exception -> 0x0220 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0220 }
                r7.addData(r1);	 Catch:{ Exception -> 0x0220 }
                goto L_0x024b;
            L_0x0220:
                r7 = move-exception;
                r7.printStackTrace();	 Catch:{ Exception -> 0x0240 }
                goto L_0x024b;	 Catch:{ Exception -> 0x0240 }
            L_0x0225:
                r7 = r1;	 Catch:{ Exception -> 0x0240 }
                r7 = r7[r3];	 Catch:{ Exception -> 0x0240 }
                if (r7 != 0) goto L_0x024b;	 Catch:{ Exception -> 0x0240 }
            L_0x022b:
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r7 = r7.loadingLayout;	 Catch:{ Exception -> 0x0240 }
                r7.setVisibility(r0);	 Catch:{ Exception -> 0x0240 }
                r7 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;	 Catch:{ Exception -> 0x0240 }
                r1 = r0;	 Catch:{ Exception -> 0x0240 }
                r1 = r1[r3];	 Catch:{ Exception -> 0x0240 }
                r7 = android.widget.Toast.makeText(r7, r1, r3);	 Catch:{ Exception -> 0x0240 }
                r7.show();	 Catch:{ Exception -> 0x0240 }
                goto L_0x024b;
            L_0x0240:
                r7 = move-exception;
                r1 = in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.this;
                r1 = r1.loadingLayout;
                r1.setVisibility(r0);
                r7.printStackTrace();
            L_0x024b:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity.6.onResponse(java.lang.String):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                NewOrderViewActivity.this.loadingLayout.setVisibility(8);
                Toast.makeText(NewOrderViewActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "OrderItems");
    }

    private void networkActionUpdate(String str, String str2, String str3, final AlertDialog alertDialog, final LinearLayout linearLayout) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        arrayList.add("1");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("orderId");
        arrayList2.add("status");
        arrayList2.add("value");
        arrayList2.add("cancelType");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        str2 = this.url_update;
        final LinearLayout linearLayout2 = linearLayout;
        final AlertDialog alertDialog2 = alertDialog;
        String anonymousClass9 = new Listener<String>() {
            public void onResponse(String str) {
                try {
                    System.out.println(str);
                    JSONObject jSONObject = new JSONObject(str);
                    iArr[0] = jSONObject.getInt("success");
                    strArr[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (iArr[0] == 1) {
                        NewOrderViewActivity.this.onBackPressed();
                    } else if (iArr[0] == null) {
                        Toast.makeText(NewOrderViewActivity.this, strArr[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
                linearLayout2.setVisibility(8);
                alertDialog2.dismiss();
            }
        };
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, str2, anonymousClass9, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(NewOrderViewActivity.this, volleyError.toString(), 0).show();
                linearLayout.setVisibility(8);
                alertDialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "OrderItemsUpdate");
    }

    private void initializeViews() {
        this.tl = (TableLayout) findViewById(R.id.maintable);
        this.tableDetails = (LinearLayout) findViewById(R.id.tableDetails);
        this.accept = (Button) findViewById(R.id.acceptButton);
        this.decline = (Button) findViewById(R.id.declineButton);
        this.orderTime = (TextView) findViewById(R.id.orderTime);
        this.orderNumber = (TextView) findViewById(R.id.head);
        this.name = (TextView) findViewById(R.id.orderName);
        this.mobile = (TextView) findViewById(R.id.orderMobile);
        this.address = (TextView) findViewById(R.id.orderAddress);
        this.bookingDate = (TextView) findViewById(R.id.tableBookingDate);
        this.bookingTime = (TextView) findViewById(R.id.tableBookingTime);
        this.tableNumber = (TextView) findViewById(R.id.orderTableNumber);
        this.members = (TextView) findViewById(R.id.orderTableMembers);
        this.addressLayout = (LinearLayout) findViewById(R.id.addressLayout);
        this.orderPrice = (TextView) findViewById(R.id.orderPrice);
        this.orderPaidPrice = (TextView) findViewById(R.id.orderPaidPrice);
        this.orderCoupon = (TextView) findViewById(R.id.orderCoupon);
        this.orderDiscout = (TextView) findViewById(R.id.orderDiscount);
        this.loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
    }

    public void addHeaders() {
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        View textView = new TextView(this);
        textView.setText("Items");
        textView.setGravity(17);
        textView.setTextColor(-7829368);
        textView.setTypeface(Typeface.DEFAULT, 1);
        textView.setPadding(5, 5, 5, 0);
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setText("Qty");
        textView.setGravity(17);
        textView.setTextColor(-7829368);
        textView.setPadding(5, 5, 5, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        textView = new TextView(this);
        textView.setText("----------------------------------");
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setGravity(17);
        textView.setText("----");
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    public void addData(ArrayList<String> arrayList) {
        JSONException e;
        int i;
        int i2;
        Context context = this;
        ArrayList arrayList2 = arrayList;
        int i3 = 0;
        int i4 = 0;
        while (i4 < arrayList.size()) {
            ArrayList<String> arrayList3;
            context.TR = new TableRow(context);
            int i5 = -2;
            int i6 = -1;
            context.TR.setLayoutParams(new LayoutParams(-1, -2));
            View textView = new TextView(context);
            ArrayList arrayList4 = new ArrayList();
            SQLiteDatabase sQLiteDatabase = context.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select Distinct(item_sub_category) from newCart where item_id='");
            stringBuilder.append(context.ItemId);
            stringBuilder.append("'and item_category ='");
            stringBuilder.append((String) arrayList2.get(i4));
            stringBuilder.append("'");
            String[] strArr = null;
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                do {
                    arrayList4.add(rawQuery.getString(i3));
                } while (rawQuery.moveToNext());
            }
            textView.setText((CharSequence) arrayList2.get(i4));
            context.TR.addView(textView);
            context.tl.addView(context.TR, new TableLayout.LayoutParams(-1, -2));
            int i7 = i3;
            while (i7 < arrayList4.size()) {
                ArrayList arrayList5;
                if (!((String) arrayList4.get(i7)).equalsIgnoreCase("none")) {
                    context.TR1 = new TableRow(context);
                    context.TR1.setLayoutParams(new LayoutParams(i6, i5));
                    View textView2 = new TextView(context);
                    textView2.setText((CharSequence) arrayList4.get(i7));
                    textView2.setPadding(15, i3, i3, i3);
                    context.TR1.addView(textView2);
                    context.tl.addView(context.TR1, new TableLayout.LayoutParams(i6, i5));
                }
                ArrayList arrayList6 = new ArrayList();
                ArrayList arrayList7 = new ArrayList();
                SQLiteDatabase sQLiteDatabase2 = context.sdb;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("select item_name, item_quantity from newCart where item_id ='");
                stringBuilder2.append(context.ItemId);
                stringBuilder2.append("' and item_category ='");
                stringBuilder2.append((String) arrayList2.get(i4));
                stringBuilder2.append("' and item_sub_category ='");
                stringBuilder2.append((String) arrayList4.get(i7));
                stringBuilder2.append("'");
                Cursor rawQuery2 = sQLiteDatabase2.rawQuery(stringBuilder2.toString(), strArr);
                int i8 = 1;
                if (rawQuery2.moveToFirst()) {
                    while (true) {
                        arrayList6.add(rawQuery2.getString(i3).replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, " "));
                        arrayList7.add(rawQuery2.getString(1));
                        if (!rawQuery2.moveToNext()) {
                            break;
                        }
                    }
                }
                int i9 = i3;
                while (i9 < arrayList6.size()) {
                    context.tr = new TableRow(context);
                    context.tr.setLayoutParams(new LayoutParams(i6, i5));
                    context.companyTV = new TextView(context);
                    context.companyTV.setText((CharSequence) arrayList6.get(i9));
                    context.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    context.companyTV.setTypeface(Typeface.DEFAULT, i8);
                    context.companyTV.setPadding(30, 5, 5, 5);
                    context.tr.addView(context.companyTV);
                    context.valueTV1 = new TextView(context);
                    context.valueTV1.setText((CharSequence) arrayList7.get(i9));
                    context.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
                    context.valueTV1.setGravity(17);
                    context.valueTV1.setPadding(5, 5, 5, 5);
                    context.valueTV1.setTypeface(Typeface.DEFAULT, i8);
                    context.tr.addView(context.valueTV1);
                    context.tl.addView(context.tr, new TableLayout.LayoutParams(i6, i5));
                    if (context.companyTV.getText().toString().contains("Custom Salad")) {
                        String str = null;
                        for (int i10 = i3; i10 < context.itemNameList.size(); i10++) {
                            if (((String) context.itemNameList.get(i10)).replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, " ").contains((CharSequence) arrayList6.get(i9))) {
                                str = ((String) context.itemSaladList.get(i10)).replace("|", ",").replace("{", "[").replace("}", "]");
                            }
                        }
                        try {
                            JSONArray jSONArray = new JSONArray(str);
                            Object arrayList8 = new ArrayList();
                            ArrayList arrayList9 = new ArrayList();
                            int i11 = i3;
                            while (i11 < jSONArray.length()) {
                                try {
                                    String[] split = jSONArray.get(i11).toString().split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                                    arrayList8.add(split[i3]);
                                    arrayList9.add(split[1]);
                                    i11++;
                                    i8 = 1;
                                } catch (JSONException e2) {
                                    JSONException jSONException = e2;
                                    arrayList5 = arrayList4;
                                    i = -2;
                                    i2 = -1;
                                }
                            }
                            i2 = i8;
                            Collection hashSet = new HashSet(arrayList8);
                            ArrayList arrayList10 = new ArrayList();
                            arrayList10.addAll(hashSet);
                            i5 = i3;
                            while (i5 < arrayList10.size()) {
                                context.TR2 = new TableRow(context);
                                try {
                                    context.TR2.setLayoutParams(new LayoutParams(-1, -2));
                                    View textView3 = new TextView(context);
                                    textView3.setText((CharSequence) arrayList10.get(i5));
                                    textView3.setPadding(80, 0, 0, 0);
                                    context.TR2.addView(textView3);
                                    arrayList5 = arrayList4;
                                    i2 = -1;
                                    try {
                                        try {
                                            context.tl.addView(context.TR2, new TableLayout.LayoutParams(-1, -2));
                                            int i12 = 0;
                                            while (i12 < arrayList9.size()) {
                                                if (((String) arrayList10.get(i5)).equals(arrayList8.get(i12))) {
                                                    View tableRow = new TableRow(context);
                                                    try {
                                                        tableRow.setLayoutParams(new LayoutParams(-1, -2));
                                                        View textView4 = new TextView(context);
                                                        textView4.setText((CharSequence) arrayList9.get(i12));
                                                        textView4.setPadding(120, 0, 0, 0);
                                                        tableRow.addView(textView4);
                                                        TableLayout tableLayout = context.tl;
                                                        i = -2;
                                                        i2 = -1;
                                                        try {
                                                            tableLayout.addView(tableRow, new TableLayout.LayoutParams(-1, -2));
                                                        } catch (JSONException e3) {
                                                            e2 = e3;
                                                        }
                                                    } catch (JSONException e4) {
                                                        e2 = e4;
                                                        i = -2;
                                                        i2 = -1;
                                                    }
                                                }
                                                i12++;
                                                context = this;
                                            }
                                            i5++;
                                            arrayList4 = arrayList5;
                                            context = this;
                                            arrayList3 = arrayList;
                                        } catch (JSONException e5) {
                                            e2 = e5;
                                        }
                                    } catch (JSONException e6) {
                                        e2 = e6;
                                        i = -2;
                                    }
                                } catch (JSONException e7) {
                                    e2 = e7;
                                    i = -2;
                                    i2 = -1;
                                    arrayList5 = arrayList4;
                                }
                            }
                            arrayList5 = arrayList4;
                            i = -2;
                            i2 = -1;
                        } catch (JSONException e8) {
                            e2 = e8;
                            arrayList5 = arrayList4;
                        }
                    } else {
                        arrayList5 = arrayList4;
                        i = i5;
                        i2 = i6;
                    }
                    i9++;
                    i5 = i;
                    i6 = i2;
                    arrayList4 = arrayList5;
                    context = this;
                    arrayList3 = arrayList;
                    i3 = 0;
                    i8 = 1;
                }
                arrayList5 = arrayList4;
                i = i5;
                i2 = i6;
                i7++;
                context = this;
                arrayList3 = arrayList;
                i3 = 0;
                strArr = null;
            }
            i4++;
            context = this;
            arrayList3 = arrayList;
            i3 = 0;
        }
        return;
        jSONException = e2;
        jSONException.printStackTrace();
        i9++;
        i5 = i;
        i6 = i2;
        arrayList4 = arrayList5;
        context = this;
        arrayList3 = arrayList;
        i3 = 0;
        i8 = 1;
        jSONException.printStackTrace();
        i9++;
        i5 = i;
        i6 = i2;
        arrayList4 = arrayList5;
        context = this;
        arrayList3 = arrayList;
        i3 = 0;
        i8 = 1;
        i = -2;
        i2 = -1;
        jSONException = e2;
        jSONException.printStackTrace();
        i9++;
        i5 = i;
        i6 = i2;
        arrayList4 = arrayList5;
        context = this;
        arrayList3 = arrayList;
        i3 = 0;
        i8 = 1;
    }

    private void setGrandTotal(JSONArray jSONArray) throws JSONException {
        int i = 0;
        int i2 = 0;
        while (i < jSONArray.length()) {
            i2 += Integer.parseInt(jSONArray.get(i).toString());
            i++;
        }
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        this.companyTV = new TextView(this);
        this.companyTV.setText("Grand Total");
        this.companyTV.setGravity(GravityCompat.END);
        this.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.companyTV.setTypeface(Typeface.DEFAULT, 1);
        this.companyTV.setPadding(5, 5, 5, 5);
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        TextView textView = this.valueTV1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(jSONArray.length());
        textView.setText(stringBuilder.toString());
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV1);
        this.gTotal = ((float) i2) + ((float) ((i2 * 18) / 100));
        this.valueTV2 = new TextView(this);
        jSONArray = this.valueTV2;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(this.gTotal);
        jSONArray.setText(stringBuilder2.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.END);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    private void setTotal(JSONArray jSONArray) throws JSONException {
        int i = 0;
        int i2 = 0;
        while (i < jSONArray.length()) {
            i2 += Integer.parseInt(jSONArray.get(i).toString());
            i++;
        }
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        this.companyTV = new TextView(this);
        this.companyTV.setText("Total");
        this.companyTV.setGravity(GravityCompat.END);
        this.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.companyTV.setTypeface(Typeface.DEFAULT, 1);
        this.companyTV.setPadding(5, 5, 5, 5);
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        TextView textView = this.valueTV1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(jSONArray.length());
        textView.setText(stringBuilder.toString());
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV1);
        this.valueTV2 = new TextView(this);
        jSONArray = this.valueTV2;
        stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(i2);
        jSONArray.setText(stringBuilder.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.END);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    private void setTax(JSONArray jSONArray) throws JSONException {
        int i = 0;
        int i2 = 0;
        while (i < jSONArray.length()) {
            i2 += Integer.parseInt(jSONArray.get(i).toString());
            i++;
        }
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        this.companyTV = new TextView(this);
        this.companyTV.setText("GST");
        this.companyTV.setGravity(GravityCompat.END);
        this.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.companyTV.setTypeface(Typeface.DEFAULT, 1);
        this.companyTV.setPadding(5, 5, 5, 5);
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        this.valueTV1.setText("18%");
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV1);
        jSONArray = (float) ((i2 * 18) / 100);
        this.valueTV2 = new TextView(this);
        TextView textView = this.valueTV2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(jSONArray);
        textView.setText(stringBuilder.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.END);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
