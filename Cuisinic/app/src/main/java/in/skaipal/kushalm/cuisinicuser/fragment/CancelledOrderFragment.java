package in.skaipal.kushalm.cuisinicuser.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.adapter.HomeDeliveryAdapter;
import in.skaipal.kushalm.cuisinicuser.adapter.TableBookingAdapter;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.HomeDelivery;
import in.skaipal.kushalm.cuisinicuser.model.TableBooking;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CancelledOrderFragment extends Fragment {
    JSONArray DeliveryArray;
    JSONArray TableArray;
    boolean deliveryData = false;
    ImageView homeDeliveryArrow;
    TextView homeDeliveryCount;
    LinearLayout homeDeliveryHead;
    RecyclerView homeDeliveryRecycler;
    boolean homeLayout = false;
    LinearLayout loadingLayout;
    HomeDeliveryAdapter mAdapterH;
    TableBookingAdapter mAdapterT;
    ImageView tableBookingArrow;
    TextView tableBookingCount;
    LinearLayout tableBookingHead;
    RecyclerView tableBookingRecycler;
    boolean tableData = false;
    boolean tableLayout = false;
    String url = APIUrls.orderStatusUrl;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.fragament_new_order, viewGroup, false);
        initializeViews(layoutInflater);
        this.loadingLayout.setVisibility(0);
        newtworkAction();
        this.homeDeliveryHead.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CancelledOrderFragment.this.homeLayout = CancelledOrderFragment.this.setRecyclerAndImage(CancelledOrderFragment.this.homeDeliveryRecycler, CancelledOrderFragment.this.homeDeliveryArrow, CancelledOrderFragment.this.homeLayout);
            }
        });
        this.tableBookingHead.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CancelledOrderFragment.this.tableLayout = CancelledOrderFragment.this.setRecyclerAndImage(CancelledOrderFragment.this.tableBookingRecycler, CancelledOrderFragment.this.tableBookingArrow, CancelledOrderFragment.this.tableLayout);
            }
        });
        return layoutInflater;
    }

    private void newtworkAction() {
        String string = getContext().getSharedPreferences("SessionSharedPreference", 0).getString("mail", null);
        final ArrayList arrayList = new ArrayList();
        arrayList.add("cancelled");
        arrayList.add("1");
        arrayList.add(string);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("orderType");
        arrayList2.add("userType");
        arrayList2.add("email");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        AppSingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(java.lang.String r6) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
                /*
                r5 = this;
                r0 = java.lang.System.out;	 Catch:{ Exception -> 0x007b }
                r0.println(r6);	 Catch:{ Exception -> 0x007b }
                r0 = new org.json.JSONObject;	 Catch:{ Exception -> 0x007b }
                r0.<init>(r6);	 Catch:{ Exception -> 0x007b }
                r6 = r1;	 Catch:{ Exception -> 0x007b }
                r1 = "success";	 Catch:{ Exception -> 0x007b }
                r1 = r0.getInt(r1);	 Catch:{ Exception -> 0x007b }
                r2 = 0;	 Catch:{ Exception -> 0x007b }
                r6[r2] = r1;	 Catch:{ Exception -> 0x007b }
                r6 = r0;	 Catch:{ Exception -> 0x007b }
                r1 = "message";	 Catch:{ Exception -> 0x007b }
                r1 = r0.getString(r1);	 Catch:{ Exception -> 0x007b }
                r6[r2] = r1;	 Catch:{ Exception -> 0x007b }
                r6 = r1;	 Catch:{ Exception -> 0x007b }
                r6 = r6[r2];	 Catch:{ Exception -> 0x007b }
                r1 = 8;	 Catch:{ Exception -> 0x007b }
                r3 = 1;	 Catch:{ Exception -> 0x007b }
                if (r6 != r3) goto L_0x006d;	 Catch:{ Exception -> 0x007b }
            L_0x0028:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r4 = new org.json.JSONArray;	 Catch:{ Exception -> 0x007b }
                r4.<init>();	 Catch:{ Exception -> 0x007b }
                r6.DeliveryArray = r4;	 Catch:{ Exception -> 0x007b }
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r4 = new org.json.JSONArray;	 Catch:{ Exception -> 0x007b }
                r4.<init>();	 Catch:{ Exception -> 0x007b }
                r6.TableArray = r4;	 Catch:{ Exception -> 0x007b }
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x0049 }
                r4 = "Delivery";	 Catch:{ Exception -> 0x0049 }
                r4 = r0.getJSONArray(r4);	 Catch:{ Exception -> 0x0049 }
                r6.DeliveryArray = r4;	 Catch:{ Exception -> 0x0049 }
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x0049 }
                r6.deliveryData = r3;	 Catch:{ Exception -> 0x0049 }
                goto L_0x004d;
            L_0x0049:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r6.deliveryData = r2;	 Catch:{ Exception -> 0x007b }
            L_0x004d:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x005c }
                r4 = "Table";	 Catch:{ Exception -> 0x005c }
                r0 = r0.getJSONArray(r4);	 Catch:{ Exception -> 0x005c }
                r6.TableArray = r0;	 Catch:{ Exception -> 0x005c }
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x005c }
                r6.tableData = r3;	 Catch:{ Exception -> 0x005c }
                goto L_0x0060;
            L_0x005c:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r6.tableData = r2;	 Catch:{ Exception -> 0x007b }
            L_0x0060:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r6 = r6.loadingLayout;	 Catch:{ Exception -> 0x007b }
                r6.setVisibility(r1);	 Catch:{ Exception -> 0x007b }
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r6.populateRecyclerViews();	 Catch:{ Exception -> 0x007b }
                goto L_0x007f;	 Catch:{ Exception -> 0x007b }
            L_0x006d:
                r6 = r1;	 Catch:{ Exception -> 0x007b }
                r6 = r6[r2];	 Catch:{ Exception -> 0x007b }
                if (r6 != 0) goto L_0x007f;	 Catch:{ Exception -> 0x007b }
            L_0x0073:
                r6 = in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.this;	 Catch:{ Exception -> 0x007b }
                r6 = r6.loadingLayout;	 Catch:{ Exception -> 0x007b }
                r6.setVisibility(r1);	 Catch:{ Exception -> 0x007b }
                goto L_0x007f;
            L_0x007b:
                r6 = move-exception;
                r6.printStackTrace();
            L_0x007f:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.fragment.CancelledOrderFragment.3.onResponse(java.lang.String):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    CancelledOrderFragment.this.loadingLayout.setVisibility(8);
                    Toast.makeText(CancelledOrderFragment.this.getActivity(), "An Error Occurred", 0).show();
                } catch (VolleyError volleyError2) {
                    volleyError2.printStackTrace();
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
        }, "OrderAdmin");
    }

    private void populateRecyclerViews() {
        LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.homeDeliveryRecycler.setLayoutManager(linearLayoutManager);
        this.tableBookingRecycler.setLayoutManager(linearLayoutManager2);
        if (this.deliveryData) {
            this.mAdapterH = new HomeDeliveryAdapter(getDataSetHome(), getContext(), "cancelled");
        }
        if (this.tableData) {
            this.mAdapterT = new TableBookingAdapter(getDataSetTable(), getContext(), "cancelled");
        }
        this.homeDeliveryRecycler.setItemAnimator(new DefaultItemAnimator());
        this.homeDeliveryRecycler.setAdapter(this.mAdapterH);
        this.tableBookingRecycler.setItemAnimator(new DefaultItemAnimator());
        this.tableBookingRecycler.setAdapter(this.mAdapterT);
    }

    private ArrayList<HomeDelivery> getDataSetHome() {
        CancelledOrderFragment cancelledOrderFragment = this;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        ArrayList arrayList11 = new ArrayList();
        int i = 0;
        while (i < cancelledOrderFragment.DeliveryArray.length()) {
            try {
                JSONObject jSONObject = cancelledOrderFragment.DeliveryArray.getJSONObject(i);
                arrayList5.add(jSONObject.getString("order_id"));
                arrayList.add(jSONObject.getString("timestamp"));
                arrayList2.add(jSONObject.getString("name"));
                arrayList4.add(jSONObject.getString("mobile"));
                arrayList3.add(jSONObject.getString("address"));
                arrayList11.add(jSONObject.getString("items_count"));
                arrayList6.add(jSONObject.getString("order_items"));
                arrayList8.add(jSONObject.getString("order_paid_price"));
                arrayList7.add(jSONObject.getString("order_price"));
                arrayList9.add(jSONObject.getString("order_coupon"));
                arrayList10.add(jSONObject.getString("order_discount"));
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String) arrayList5.get(i));
                stringBuilder.append(" ");
                stringBuilder.append((String) arrayList.get(i));
                stringBuilder.append(" ");
                stringBuilder.append((String) arrayList2.get(i));
                stringBuilder.append(" ");
                stringBuilder.append((String) arrayList4.get(i));
                stringBuilder.append(" ");
                stringBuilder.append((String) arrayList3.get(i));
                stringBuilder.append(" ");
                stringBuilder.append((String) arrayList11.get(i));
                printStream.println(stringBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
            cancelledOrderFragment = this;
        }
        ArrayList<HomeDelivery> arrayList12 = new ArrayList();
        for (int i2 = 0; i2 < arrayList5.size(); i2++) {
            arrayList12.add(new HomeDelivery((String) arrayList.get(i2), (String) arrayList5.get(i2), (String) arrayList11.get(i2), (String) arrayList2.get(i2), (String) arrayList4.get(i2), (String) arrayList3.get(i2), (String) arrayList6.get(i2), (String) arrayList7.get(i2), (String) arrayList8.get(i2), (String) arrayList9.get(i2), (String) arrayList10.get(i2)));
        }
        TextView textView = this.homeDeliveryCount;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(arrayList12.size());
        textView.setText(stringBuilder2.toString());
        return arrayList12;
    }

    private ArrayList<TableBooking> getDataSetTable() {
        ArrayList arrayList;
        JSONException e;
        CancelledOrderFragment cancelledOrderFragment = this;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        ArrayList arrayList11 = new ArrayList();
        ArrayList arrayList12 = new ArrayList();
        ArrayList arrayList13 = new ArrayList();
        ArrayList arrayList14 = new ArrayList();
        ArrayList arrayList15 = new ArrayList();
        ArrayList arrayList16 = arrayList13;
        ArrayList arrayList17 = arrayList14;
        int i = 0;
        while (i < cancelledOrderFragment.TableArray.length()) {
            try {
                JSONObject jSONObject = cancelledOrderFragment.TableArray.getJSONObject(i);
                arrayList5.add(jSONObject.getString("order_id"));
                arrayList2.add(jSONObject.getString("timestamp"));
                arrayList3.add(jSONObject.getString("name"));
                arrayList4.add(jSONObject.getString("mobile"));
                arrayList6.add(jSONObject.getString("items_count"));
                arrayList7.add(jSONObject.getString("order_table"));
                arrayList8.add(jSONObject.getString("order_seater"));
                arrayList9.add(jSONObject.getString("order_table_booking_time"));
                arrayList15.add(jSONObject.getString("order_table_booking_date"));
                arrayList10.add(jSONObject.getString("order_items"));
                arrayList12.add(jSONObject.getString("order_paid_price"));
                arrayList11.add(jSONObject.getString("order_price"));
                arrayList = arrayList12;
                arrayList12 = arrayList16;
                try {
                    arrayList12.add(jSONObject.getString("order_coupon"));
                    String string = jSONObject.getString("order_discount");
                    arrayList13 = arrayList17;
                    try {
                        arrayList13.add(string);
                    } catch (JSONException e2) {
                        e = e2;
                        e.printStackTrace();
                        i++;
                        arrayList16 = arrayList12;
                        arrayList17 = arrayList13;
                        arrayList12 = arrayList;
                        cancelledOrderFragment = this;
                    }
                } catch (JSONException e3) {
                    e = e3;
                    arrayList13 = arrayList17;
                    e.printStackTrace();
                    i++;
                    arrayList16 = arrayList12;
                    arrayList17 = arrayList13;
                    arrayList12 = arrayList;
                    cancelledOrderFragment = this;
                }
            } catch (JSONException e4) {
                e = e4;
                arrayList = arrayList12;
                arrayList13 = arrayList17;
                arrayList12 = arrayList16;
                e.printStackTrace();
                i++;
                arrayList16 = arrayList12;
                arrayList17 = arrayList13;
                arrayList12 = arrayList;
                cancelledOrderFragment = this;
            }
            i++;
            arrayList16 = arrayList12;
            arrayList17 = arrayList13;
            arrayList12 = arrayList;
            cancelledOrderFragment = this;
        }
        arrayList = arrayList12;
        arrayList13 = arrayList17;
        arrayList12 = arrayList16;
        ArrayList<TableBooking> arrayList18 = new ArrayList();
        i = 0;
        while (i < arrayList5.size()) {
            ArrayList arrayList19 = arrayList2;
            arrayList2 = arrayList;
            ArrayList arrayList20 = arrayList2;
            arrayList18.add(new TableBooking((String) arrayList2.get(i), (String) arrayList5.get(i), (String) arrayList6.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList7.get(i), (String) arrayList8.get(i), (String) arrayList9.get(i), (String) arrayList15.get(i), (String) arrayList10.get(i), (String) arrayList11.get(i), (String) arrayList2.get(i), (String) arrayList12.get(i), (String) arrayList13.get(i)));
            i++;
            arrayList2 = arrayList19;
            arrayList = arrayList20;
        }
        ArrayList<TableBooking> arrayList21 = arrayList18;
        TextView textView = this.tableBookingCount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(arrayList21.size());
        textView.setText(stringBuilder.toString());
        return arrayList21;
    }

    private void initializeViews(View view) {
        this.homeDeliveryCount = (TextView) view.findViewById(R.id.homeDeliveryCount);
        this.tableBookingCount = (TextView) view.findViewById(R.id.tableBookingCount);
        this.homeDeliveryHead = (LinearLayout) view.findViewById(R.id.homeDeliveryHead);
        this.tableBookingHead = (LinearLayout) view.findViewById(R.id.tableBookingHead);
        this.homeDeliveryArrow = (ImageView) view.findViewById(R.id.homeDeliveryArrow);
        this.tableBookingArrow = (ImageView) view.findViewById(R.id.tableBookingArrow);
        this.homeDeliveryRecycler = (RecyclerView) view.findViewById(R.id.homeDeliveryRecycler);
        this.homeDeliveryRecycler.setVisibility(8);
        this.tableBookingRecycler = (RecyclerView) view.findViewById(R.id.tableBookingRecycler);
        this.tableBookingRecycler.setVisibility(8);
        this.loadingLayout = (LinearLayout) view.findViewById(R.id.loadingLayout);
    }

    private boolean setRecyclerAndImage(RecyclerView recyclerView, ImageView imageView, boolean z) {
        if (z) {
            recyclerView.setVisibility(true);
            imageView.setScaleY(1.0f);
            return false;
        }
        recyclerView.setVisibility(0);
        imageView.setScaleY(-1.0f);
        return true;
    }

    public void onResume() {
        super.onResume();
        this.loadingLayout.setVisibility(0);
        newtworkAction();
    }
}
