package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.OffersModel;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class BookTableActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private static String updateUrl = APIUrls.bookTableUrl;
    EditText BookingName;
    EditText BookingNumber;
    TextView CouponDetails;
    private String Email;
    float amount;
    boolean couponApplied = false;
    CheckBox couponBox;
    Button couponCheck;
    EditText couponcode;
    CartDatabase db;
    float finalAmount;
    Button galleryButton;
    Button payAndBook;
    int[] payId = new int[]{R.id.paymentFull, R.id.paymentToken};
    Spinner paymentMode;
    Date pick;
    RadioButton[] rb1;
    RadioButton[] rb2;
    RadioButton[] rb3;
    String restName;
    ArrayList<OffersModel> returnData;
    RadioGroup rg1;
    RadioGroup rg2;
    RadioGroup rg3;
    SQLiteDatabase sdb;
    EditText seatMore;
    int[] seaterId = new int[]{R.id.seat1, R.id.seat2, R.id.seat3, R.id.seat4, R.id.seat5, R.id.seat6, R.id.seat7, R.id.seat8, R.id.seat9, R.id.seatMore};
    EditText selectDate;
    EditText selectTime;
    private SharedPreferences sp;
    int[] tableId = new int[]{R.id.tableAny, R.id.table1, R.id.table2, R.id.table3, R.id.table4, R.id.table5, R.id.table6, R.id.table7, R.id.table8, R.id.table9};
    Date today;

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_table_booking);
        getSupportActionBar().setTitle((CharSequence) "Table Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bundle = getIntent().getExtras();
        if (!(bundle == null || bundle == null)) {
            this.restName = bundle.getString("restName");
            this.amount = bundle.getFloat("orderAmount");
            getSupportActionBar().setTitle(this.restName);
        }
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        this.db = new CartDatabase(this);
        initializeViews();
        populatePaymentModeSpinner();
        this.couponBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (BookTableActivity.this.couponBox.isChecked() != null) {
                    BookTableActivity.this.startActivityForResult(new Intent(BookTableActivity.this, OffersSelectActivity.class), 333);
                    BookTableActivity.this.couponcode.setEnabled(true);
                    BookTableActivity.this.couponCheck.setEnabled(true);
                    return;
                }
                BookTableActivity.this.couponcode.setEnabled(false);
                BookTableActivity.this.couponCheck.setEnabled(false);
                BookTableActivity.this.CouponDetails.setVisibility(8);
                view = BookTableActivity.this.rb3[0];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Full ");
                stringBuilder.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                stringBuilder.append(" ");
                stringBuilder.append(BookTableActivity.this.amount);
                view.setText(stringBuilder.toString());
                view = (1092616192 * BookTableActivity.this.amount) / 100.0f;
                RadioButton radioButton = BookTableActivity.this.rb3[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("Token (10 % of Full Amount) ");
                stringBuilder.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                stringBuilder.append(" ");
                stringBuilder.append(view);
                radioButton.setText(stringBuilder.toString());
                BookTableActivity.this.couponcode.setText("");
                BookTableActivity.this.couponApplied = false;
            }
        });
        this.couponCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BookTableActivity.this.applyCoupon(false);
            }
        });
        this.selectTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BookTableActivity.this.timePickerDialogBox();
            }
        });
        this.selectDate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BookTableActivity.this.datePickerDialogBox();
            }
        });
        this.rb1[9].setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    BookTableActivity.this.seatMore.setVisibility(false);
                } else {
                    BookTableActivity.this.seatMore.setVisibility(true);
                }
            }
        });
        this.payAndBook.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String trim = BookTableActivity.this.BookingName.getText().toString().trim();
                String trim2 = BookTableActivity.this.BookingNumber.getText().toString().trim();
                String str = "nill";
                ArrayList arrayList = new ArrayList();
                if (!trim.equals("")) {
                    if (!trim.isEmpty()) {
                        if (!trim2.equals("")) {
                            if (!trim2.isEmpty()) {
                                if (trim2.length() != 10) {
                                    BookTableActivity.this.BookingNumber.setError("Invalid Mobile Number");
                                    return;
                                } else if (BookTableActivity.this.rb1[9].isChecked()) {
                                    if (BookTableActivity.this.seatMore.getText().toString().equals("")) {
                                        BookTableActivity.this.seatMore.setError("Please Select number of seats");
                                    } else {
                                        r5 = BookTableActivity.this.selectTime.getText().toString().trim();
                                        r15 = BookTableActivity.this.selectDate.getText().toString().trim();
                                        if (r5.equals("")) {
                                            BookTableActivity.this.selectTime.setError("Select Time");
                                        } else if (r15.equals("")) {
                                            BookTableActivity.this.selectDate.setError("Select Date");
                                        } else {
                                            String str2;
                                            String str3;
                                            BookTableActivity.this.selectTime.setError(null);
                                            BookTableActivity.this.selectDate.setError(null);
                                            r7 = BookTableActivity.this.seatMore.getText().toString().trim();
                                            r12 = BookTableActivity.this.rg2.getCheckedRadioButtonId();
                                            r11 = BookTableActivity.this.rg3.getCheckedRadioButtonId();
                                            CharSequence charSequence = null;
                                            for (r13 = 0; r13 < BookTableActivity.this.tableId.length; r13++) {
                                                if (r12 == BookTableActivity.this.tableId[r13]) {
                                                    charSequence = BookTableActivity.this.rb2[r13].getText().toString();
                                                }
                                            }
                                            if (r11 == BookTableActivity.this.payId[0]) {
                                                r8 = new StringBuilder();
                                                r8.append("");
                                                r8.append(BookTableActivity.this.amount);
                                                r8.toString();
                                            } else {
                                                r9 = (10.0f * BookTableActivity.this.amount) / 100.0f;
                                                r8 = new StringBuilder();
                                                r8.append("");
                                                r8.append(r9);
                                                r8.toString();
                                            }
                                            r8 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
                                            r9 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                            r8 = r8.format(new Date());
                                            r9 = r9.format(new Date());
                                            r10 = new StringBuilder();
                                            r10.append("ORD_");
                                            r10.append(r8);
                                            r8 = r10.toString();
                                            r10 = "Table";
                                            r12 = new StringBuilder();
                                            r12.append("ITEM_");
                                            r12.append(r8);
                                            r12 = r12.toString();
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("");
                                            stringBuilder.append(BookTableActivity.this.amount);
                                            r13 = stringBuilder.toString();
                                            r14 = "nill";
                                            String str4 = r5;
                                            if (BookTableActivity.this.couponApplied) {
                                                String trim3 = BookTableActivity.this.couponcode.getText().toString().trim();
                                                if (r11 == BookTableActivity.this.payId[0]) {
                                                    r5 = BookTableActivity.this.rb3[0].getText().toString().trim();
                                                    r11 = new StringBuilder();
                                                    str2 = r15;
                                                    r11.append("Full ");
                                                    str3 = r7;
                                                    r11.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                                                    r11.append(" ");
                                                    r5 = r5.replace(r11.toString(), "");
                                                } else {
                                                    str3 = r7;
                                                    str2 = r15;
                                                    r5 = BookTableActivity.this.rb3[0].getText().toString().trim();
                                                    StringBuilder stringBuilder2 = new StringBuilder();
                                                    stringBuilder2.append("Token (10 % of Full Amount) ");
                                                    stringBuilder2.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                                                    stringBuilder2.append(" ");
                                                    r5 = r5.replace(stringBuilder2.toString(), "");
                                                }
                                                r7 = r5;
                                                r5 = trim3;
                                            } else {
                                                str3 = r7;
                                                str2 = r15;
                                                r5 = "nill";
                                                r7 = "nill";
                                            }
                                            arrayList.add(r9.trim());
                                            arrayList.add(r8.trim());
                                            arrayList.add(BookTableActivity.this.Email.trim());
                                            arrayList.add(trim.trim());
                                            arrayList.add(trim2.trim());
                                            arrayList.add(str.trim());
                                            arrayList.add(r10.trim());
                                            arrayList.add(r12.trim());
                                            arrayList.add(r13.trim());
                                            arrayList.add(r14.trim());
                                            arrayList.add(r5.trim());
                                            arrayList.add(r7.trim());
                                            arrayList.add(charSequence.trim());
                                            arrayList.add(str3.trim());
                                            arrayList.add(str2.trim());
                                            arrayList.add(str4.trim());
                                        }
                                    }
                                    try {
                                        r7 = new ArrayList();
                                        r8 = new ArrayList();
                                        r9 = new ArrayList();
                                        r10 = new ArrayList();
                                        r11 = new ArrayList();
                                        BookTableActivity.this.sdb = BookTableActivity.this.db.getWritableDatabase();
                                        r2 = BookTableActivity.this.sdb;
                                        r3 = new StringBuilder();
                                        r3.append("select item_category, item_sub_category, item_name, item_quantity from cart where rest_name ='");
                                        r3.append(BookTableActivity.this.Email);
                                        r3.append("'");
                                        r2 = r2.rawQuery(r3.toString(), null);
                                        if (r2.moveToFirst()) {
                                            do {
                                                r7.add(r2.getString(0));
                                                r8.add(r2.getString(1));
                                                r9.add(r2.getString(2));
                                                if (r2.getString(2).contains("Custom Salad")) {
                                                    SQLiteDatabase sQLiteDatabase = BookTableActivity.this.sdb;
                                                    StringBuilder stringBuilder3 = new StringBuilder();
                                                    stringBuilder3.append("select item from salad where name ='");
                                                    stringBuilder3.append(r2.getString(2));
                                                    stringBuilder3.append("'");
                                                    Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder3.toString(), null);
                                                    if (rawQuery.moveToFirst()) {
                                                        do {
                                                            r11.add(rawQuery.getString(0).replace("[", "{").replace("]", "}").replace(",", "|"));
                                                        } while (rawQuery.moveToNext());
                                                    }
                                                } else {
                                                    r11.add("null");
                                                }
                                                r10.add(r2.getString(3));
                                            } while (r2.moveToNext());
                                        }
                                        arrayList.add(String.valueOf(r9.size()));
                                        BookTableActivity.this.networkAction(arrayList, r7, r8, r9, r10, r11);
                                        return;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                } else {
                                    r5 = BookTableActivity.this.selectTime.getText().toString().trim();
                                    r7 = BookTableActivity.this.selectDate.getText().toString().trim();
                                    if (r5.equals("")) {
                                        BookTableActivity.this.selectTime.setError("Select Time");
                                        return;
                                    } else if (r7.equals("")) {
                                        BookTableActivity.this.selectDate.setError("Select Date");
                                        return;
                                    } else {
                                        String str5;
                                        String str6;
                                        BookTableActivity.this.selectTime.setError(null);
                                        int checkedRadioButtonId = BookTableActivity.this.rg1.getCheckedRadioButtonId();
                                        r11 = BookTableActivity.this.rg2.getCheckedRadioButtonId();
                                        r12 = BookTableActivity.this.rg3.getCheckedRadioButtonId();
                                        r14 = null;
                                        for (r13 = 0; r13 < BookTableActivity.this.seaterId.length; r13++) {
                                            if (checkedRadioButtonId == BookTableActivity.this.seaterId[r13]) {
                                                r14 = BookTableActivity.this.rb1[r13].getText().toString().trim();
                                            }
                                        }
                                        r13 = null;
                                        for (checkedRadioButtonId = 0; checkedRadioButtonId < BookTableActivity.this.tableId.length; checkedRadioButtonId++) {
                                            if (r11 == BookTableActivity.this.tableId[checkedRadioButtonId]) {
                                                r13 = BookTableActivity.this.rb2[checkedRadioButtonId].getText().toString().trim();
                                            }
                                        }
                                        if (r12 == BookTableActivity.this.payId[0]) {
                                            r8 = new StringBuilder();
                                            r8.append("");
                                            r8.append(BookTableActivity.this.amount);
                                            r8.toString();
                                        } else {
                                            r9 = (10.0f * BookTableActivity.this.amount) / 100.0f;
                                            r8 = new StringBuilder();
                                            r8.append("");
                                            r8.append(r9);
                                            r8.toString();
                                        }
                                        r8 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
                                        r9 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        r8 = r8.format(new Date());
                                        r9 = r9.format(new Date());
                                        r10 = new StringBuilder();
                                        r10.append("ORD_");
                                        r10.append(r8);
                                        r8 = r10.toString();
                                        r10 = "Table";
                                        r11 = new StringBuilder();
                                        r11.append("ITEM_");
                                        r11.append(r8);
                                        String stringBuilder4 = r11.toString();
                                        StringBuilder stringBuilder5 = new StringBuilder();
                                        String str7 = r5;
                                        stringBuilder5.append("");
                                        stringBuilder5.append(BookTableActivity.this.amount);
                                        r5 = stringBuilder5.toString();
                                        r15 = "nill";
                                        String str8 = r7;
                                        if (BookTableActivity.this.couponApplied) {
                                            String trim4 = BookTableActivity.this.couponcode.getText().toString().trim();
                                            if (r12 == BookTableActivity.this.payId[0]) {
                                                r7 = BookTableActivity.this.rb3[0].getText().toString().trim();
                                                r12 = new StringBuilder();
                                                str5 = r14;
                                                r12.append("Full ");
                                                str6 = r13;
                                                r12.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                                                r12.append(" ");
                                                r7 = r7.replace(r12.toString(), "");
                                            } else {
                                                str6 = r13;
                                                str5 = r14;
                                                r7 = BookTableActivity.this.rb3[0].getText().toString().trim();
                                                r12 = new StringBuilder();
                                                r12.append("Token (10 % of Full Amount) ");
                                                r12.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                                                r12.append(" ");
                                                r7 = r7.replace(r12.toString(), "");
                                            }
                                            r12 = r7;
                                            r7 = trim4;
                                        } else {
                                            str6 = r13;
                                            str5 = r14;
                                            r7 = "nill";
                                            r12 = "nill";
                                        }
                                        arrayList.add(r9.trim());
                                        arrayList.add(r8.trim());
                                        arrayList.add(BookTableActivity.this.Email.trim());
                                        arrayList.add(trim.trim());
                                        arrayList.add(trim2.trim());
                                        arrayList.add(str.trim());
                                        arrayList.add(r10.trim());
                                        arrayList.add(stringBuilder4.trim());
                                        arrayList.add(r5.trim());
                                        arrayList.add(r15.trim());
                                        arrayList.add(r7.trim());
                                        arrayList.add(r12.trim());
                                        arrayList.add(str6.trim());
                                        arrayList.add(str5.trim());
                                        arrayList.add(str8.trim());
                                        arrayList.add(str7.trim());
                                        try {
                                            r7 = new ArrayList();
                                            r8 = new ArrayList();
                                            r9 = new ArrayList();
                                            r10 = new ArrayList();
                                            r11 = new ArrayList();
                                            BookTableActivity.this.sdb = BookTableActivity.this.db.getWritableDatabase();
                                            r2 = BookTableActivity.this.sdb;
                                            r3 = new StringBuilder();
                                            r3.append("select item_category, item_sub_category, item_name, item_quantity from cart where rest_name ='");
                                            r3.append(BookTableActivity.this.Email);
                                            r3.append("'");
                                            r2 = r2.rawQuery(r3.toString(), null);
                                            if (r2.moveToFirst()) {
                                                int i = 0;
                                                while (true) {
                                                    int i2;
                                                    r7.add(r2.getString(i));
                                                    r8.add(r2.getString(1));
                                                    r9.add(r2.getString(2));
                                                    if (r2.getString(2).contains("Custom Salad")) {
                                                        SQLiteDatabase sQLiteDatabase2 = BookTableActivity.this.sdb;
                                                        StringBuilder stringBuilder6 = new StringBuilder();
                                                        stringBuilder6.append("select item from salad where name ='");
                                                        stringBuilder6.append(r2.getString(2));
                                                        stringBuilder6.append("'");
                                                        Cursor rawQuery2 = sQLiteDatabase2.rawQuery(stringBuilder6.toString(), null);
                                                        if (rawQuery2.moveToFirst()) {
                                                            i2 = 0;
                                                            while (true) {
                                                                r11.add(rawQuery2.getString(0).replace("[", "{").replace("]", "}").replace(",", "|"));
                                                                if (!rawQuery2.moveToNext()) {
                                                                    break;
                                                                }
                                                            }
                                                        } else {
                                                            i2 = 0;
                                                        }
                                                    } else {
                                                        i2 = 0;
                                                        r11.add("null");
                                                    }
                                                    r10.add(r2.getString(3));
                                                    if (!r2.moveToNext()) {
                                                        break;
                                                    }
                                                    i = i2;
                                                }
                                            }
                                            arrayList.add(String.valueOf(r9.size()));
                                            BookTableActivity.this.networkAction(arrayList, r7, r8, r9, r10, r11);
                                            return;
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        BookTableActivity.this.BookingNumber.setError("Enter Number");
                        return;
                    }
                }
                BookTableActivity.this.BookingName.setError("Enter Name");
            }
        });
        this.galleryButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(BookTableActivity.this, GalleryActivity.class);
                view.putExtra("restName", BookTableActivity.this.getSupportActionBar().getTitle());
                BookTableActivity.this.startActivity(view);
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
        progressDialog.setMessage("Booking a table for you, please wait!");
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
                        BookTableActivity.this.deleteFromSqlite(BookTableActivity.this.Email);
                        str = BookTableActivity.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Table has been booked in ");
                        stringBuilder.append(BookTableActivity.this.getResources().getString(R.string.Rupee));
                        stringBuilder.append(" ");
                        stringBuilder.append(BookTableActivity.this.amount);
                        stringBuilder.append(" been placed from ");
                        stringBuilder.append(BookTableActivity.this.restName);
                        Toast.makeText(str, stringBuilder.toString(), 0).show();
                    } else if (iArr[0] == null) {
                        progressDialog.hide();
                        Toast.makeText(BookTableActivity.this, strArr[0], 0).show();
                    }
                } catch (String str2) {
                    progressDialog.hide();
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.hide();
                Toast.makeText(BookTableActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList7.size(); i++) {
                    hashMap.put(arrayList8.get(i), arrayList7.get(i));
                }
                return hashMap;
            }
        }, "Order");
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
        } else if (((OffersModel) this.returnData.get(0)).getCondition().equalsIgnoreCase("greater than")) {
            if (((int) this.amount) >= Integer.parseInt(((OffersModel) this.returnData.get(0)).getTotal().replace(getResources().getString(R.string.Rupee), "").trim())) {
                if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("%")) {
                    f = (this.amount * ((float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue()))) / 100.0f;
                } else if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("₹")) {
                    f = (float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue());
                }
                this.couponApplied = true;
                this.finalAmount = this.amount - f;
                r0 = this.rb3[0];
                r2 = new StringBuilder();
                r2.append("Full ");
                r2.append(getResources().getString(R.string.Rupee));
                r2.append(" ");
                r2.append(this.finalAmount);
                r0.setText(r2.toString());
                r3 = (10.0f * this.finalAmount) / 100.0f;
                r0 = this.rb3[1];
                r2 = new StringBuilder();
                r2.append("Token (10 % of Full Amount) ");
                r2.append(getResources().getString(R.string.Rupee));
                r2.append(" ");
                r2.append(r3);
                r0.setText(r2.toString());
                r0 = new StringBuilder();
                r0.append("Coupon code ");
                r0.append(this.couponcode.getText().toString().toUpperCase());
                r0.append("has been applied");
                Toast.makeText(this, r0.toString(), 0).show();
                this.CouponDetails.setVisibility(0);
                r0 = this.CouponDetails;
                r1 = new StringBuilder();
                r1.append(this.couponcode.getText().toString());
                r1.append(" has been applied to avial a discount of ");
                r1.append(f);
                r1.append("%");
                r0.setText(r1.toString());
                return;
            }
            f = this.amount;
            f = this.rb3[0];
            r0 = new StringBuilder();
            r0.append("Full ");
            r0.append(getResources().getString(R.string.Rupee));
            r0.append(" ");
            r0.append(this.amount);
            f.setText(r0.toString());
            r3 = (10.0f * this.amount) / 100.0f;
            f = this.rb3[1];
            r0 = new StringBuilder();
            r0.append("Token (10 % of Full Amount) ");
            r0.append(getResources().getString(R.string.Rupee));
            r0.append(" ");
            r0.append(r3);
            f.setText(r0.toString());
            this.couponApplied = false;
            Toast.makeText(this, "Offer Criteria not matched", 0).show();
            this.CouponDetails.setVisibility(8);
        } else if (!((OffersModel) this.returnData.get(0)).getCondition().equalsIgnoreCase("equals to")) {
        } else {
            if (((int) this.amount) == Integer.parseInt(((OffersModel) this.returnData.get(0)).getTotal().replace(getResources().getString(R.string.Rupee), "").trim())) {
                if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("%")) {
                    f = (this.amount * ((float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue()))) / 100.0f;
                } else if (((OffersModel) this.returnData.get(0)).getType().equalsIgnoreCase("₹")) {
                    f = (float) Integer.parseInt(((OffersModel) this.returnData.get(0)).getValue());
                }
                this.couponApplied = true;
                this.finalAmount = this.amount - f;
                r0 = this.rb3[0];
                r2 = new StringBuilder();
                r2.append("Full ");
                r2.append(getResources().getString(R.string.Rupee));
                r2.append(" ");
                r2.append(this.finalAmount);
                r0.setText(r2.toString());
                r3 = (10.0f * this.finalAmount) / 100.0f;
                r0 = this.rb3[1];
                r2 = new StringBuilder();
                r2.append("Token (10 % of Full Amount) ");
                r2.append(getResources().getString(R.string.Rupee));
                r2.append(" ");
                r2.append(r3);
                r0.setText(r2.toString());
                r0 = new StringBuilder();
                r0.append("Coupon code ");
                r0.append(this.couponcode.getText().toString().toUpperCase());
                r0.append("has been applied");
                Toast.makeText(this, r0.toString(), 0).show();
                this.CouponDetails.setVisibility(0);
                r0 = this.CouponDetails;
                r1 = new StringBuilder();
                r1.append(this.couponcode.getText().toString());
                r1.append(" has been applied to avial a discount of ");
                r1.append(f);
                r1.append("%");
                r0.setText(r1.toString());
                return;
            }
            f = this.amount;
            f = this.rb3[0];
            r0 = new StringBuilder();
            r0.append("Full ");
            r0.append(getResources().getString(R.string.Rupee));
            r0.append(" ");
            r0.append(this.amount);
            f.setText(r0.toString());
            r3 = (10.0f * this.amount) / 100.0f;
            f = this.rb3[1];
            r0 = new StringBuilder();
            r0.append("Token (10 % of Full Amount) ");
            r0.append(getResources().getString(R.string.Rupee));
            r0.append(" ");
            r0.append(r3);
            f.setText(r0.toString());
            this.couponApplied = false;
            Toast.makeText(this, "Offer Criteria not matched", 0).show();
            this.CouponDetails.setVisibility(8);
        }
    }

    private void datePickerDialogBox() {
        final Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        this.today = instance.getTime();
        new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                datePicker = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Current_time::::");
                stringBuilder.append(instance.getTime());
                datePicker.print(stringBuilder.toString());
                BookTableActivity.this.populateSetDate(instance, i, i2, i3);
            }
        }, i, i2, i3).show();
    }

    public void populateSetDate(Calendar calendar, int i, int i2, int i3) {
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
        this.pick = calendar.getTime();
        calendar = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        if (this.pick.compareTo(this.today) <= 0) {
            if (this.pick.compareTo(this.today) != 0) {
                Toast.makeText(this, "Selected date cannot be previous to today's date", 0).show();
                return;
            }
        }
        this.selectDate.setText(calendar);
    }

    private void deleteFromSqlite(String str) {
        this.sdb = this.db.getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sdb;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Delete from cart where rest_name='");
        stringBuilder.append(str.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
        stringBuilder.append("'");
        sQLiteDatabase.execSQL(stringBuilder.toString());
        str = new Builder(this);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Thank you for booking table from ");
        stringBuilder2.append(getSupportActionBar().getTitle().toString());
        stringBuilder2.append(" Enjoy the food");
        str.setMessage(stringBuilder2.toString());
        str.setCancelable(false);
        str.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface = new Intent(BookTableActivity.this, CuisinicMenuActivity.class);
                dialogInterface.setFlags(268468224);
                BookTableActivity.this.startActivity(dialogInterface);
            }
        });
        str = str.create();
        str.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                str.getButton(-2).setTextColor(BookTableActivity.this.getResources().getColor(R.color.colorPrimary));
                str.getButton(-1).setTextColor(BookTableActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        str.show();
    }

    private void timePickerDialogBox() {
        Calendar instance = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                timePicker = BookTableActivity.this.selectTime;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append(":");
                stringBuilder.append(i2);
                timePicker.setText(stringBuilder.toString());
            }
        }, instance.get(11), instance.get(12), true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void populatePaymentModeSpinner() {
        List arrayList = new ArrayList();
        arrayList.add("Paytm");
        this.paymentMode.setAdapter(new ArrayAdapter(this, 17367049, arrayList));
        this.paymentMode.setSelection(0);
    }

    @RequiresApi(api = 21)
    private void initializeViews() {
        int i;
        this.rg1 = (RadioGroup) findViewById(R.id.rg1);
        this.rg2 = (RadioGroup) findViewById(R.id.rg2);
        this.rg3 = (RadioGroup) findViewById(R.id.rg3);
        this.rb1 = new RadioButton[this.seaterId.length];
        this.rb2 = new RadioButton[this.tableId.length];
        this.rb3 = new RadioButton[this.payId.length];
        this.couponCheck = (Button) findViewById(R.id.couponCheckButton);
        this.CouponDetails = (TextView) findViewById(R.id.tableCouponDetails);
        this.CouponDetails.setVisibility(8);
        this.couponCheck.setEnabled(false);
        this.couponBox = (CheckBox) findViewById(R.id.couponCheckBox);
        this.couponcode = (EditText) findViewById(R.id.couponEditText);
        this.couponcode.setEnabled(false);
        this.seatMore = (EditText) findViewById(R.id.seaterMoreET);
        this.selectTime = (EditText) findViewById(R.id.selectTime);
        this.selectDate = (EditText) findViewById(R.id.selectDate);
        this.BookingName = (EditText) findViewById(R.id.BookingName);
        this.BookingNumber = (EditText) findViewById(R.id.BookingNumber);
        this.paymentMode = (Spinner) findViewById(R.id.paymentModeBook);
        this.payAndBook = (Button) findViewById(R.id.payAndBook);
        this.galleryButton = (Button) findViewById(R.id.galleryButton);
        r5 = new int[2][];
        r5[0] = new int[]{-16842910};
        r5[1] = new int[]{16842910};
        ColorStateList colorStateList = new ColorStateList(r5, new int[]{ViewCompat.MEASURED_STATE_MASK, getResources().getColor(R.color.colorPrimary)});
        for (i = 0; i < r0.seaterId.length; i++) {
            r0.rb1[i] = (RadioButton) findViewById(r0.seaterId[i]);
            r0.rb1[i].setButtonTintList(colorStateList);
            r0.rb1[i].invalidate();
        }
        for (i = 0; i < r0.tableId.length; i++) {
            r0.rb2[i] = (RadioButton) findViewById(r0.tableId[i]);
            r0.rb2[i].setButtonTintList(colorStateList);
            r0.rb2[i].invalidate();
        }
        for (i = 0; i < r0.payId.length; i++) {
            r0.rb3[i] = (RadioButton) findViewById(r0.payId[i]);
            r0.rb3[i].setButtonTintList(colorStateList);
            r0.rb3[i].invalidate();
        }
        RadioButton radioButton = r0.rb3[0];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Full ");
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(r0.amount);
        radioButton.setText(stringBuilder.toString());
        float f = (10.0f * r0.amount) / 100.0f;
        RadioButton radioButton2 = r0.rb3[1];
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Token (10 % of Full Amount) ");
        stringBuilder2.append(getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(f);
        radioButton2.setText(stringBuilder2.toString());
        r0.rb1[0].setChecked(true);
        r0.rb2[0].setChecked(true);
        r0.rb3[0].setChecked(true);
    }

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
