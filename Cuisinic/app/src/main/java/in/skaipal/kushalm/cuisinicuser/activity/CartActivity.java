package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    Integer GST = Integer.valueOf(5);
    String RName;
    TableRow TR;
    TableRow TR1;
    TextView companyTV;
    CartDatabase db;
    String deletedRest;
    Button delivery;
    TextView emptycart;
    float gTotal;
    boolean isDeleted = false;
    ArrayList<String> newRestNameList;
    ArrayList<String> restNameList;
    SQLiteDatabase sdb;
    private SharedPreferences sp;
    Button tableBooking;
    TableLayout tl;
    TableRow tr;
    TextView valueTV;
    TextView valueTV1;
    TextView valueTV2;

    @RequiresApi(api = 23)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cart);
        getSupportActionBar().setTitle((CharSequence) "Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.tl = (TableLayout) findViewById(R.id.maintable);
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        this.emptycart = (TextView) findViewById(R.id.emptyCart);
        this.delivery = (Button) findViewById(R.id.deliveryButton);
        this.tableBooking = (Button) findViewById(R.id.tableBooking);
        this.restNameList = new ArrayList();
        this.newRestNameList = new ArrayList();
        try {
            bundle = new ArrayList();
            ArrayList arrayList = new ArrayList();
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select Distinct(item_category) from cart where rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("' and item_quantity!=0");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                do {
                    bundle.add(rawQuery.getString(0));
                } while (rawQuery.moveToNext());
            }
            addHeaders();
            addData(bundle);
        } catch (Bundle bundle2) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(bundle2.toString());
            Toast.makeText(this, stringBuilder2.toString(), 1).show();
            bundle2.printStackTrace();
            this.tl.setVisibility(8);
            this.emptycart.setVisibility(0);
        }
        this.delivery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CartActivity.this, DeliveryActivity.class);
                view.putExtra("restName", CartActivity.this.getSupportActionBar().getTitle());
                view.putExtra("orderAmount", CartActivity.this.gTotal);
                CartActivity.this.startActivity(view);
            }
        });
        this.tableBooking.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CartActivity.this.gTotal >= 200.0f) {
                    view = new Intent(CartActivity.this, BookTableActivity.class);
                    view.putExtra("restName", CartActivity.this.getSupportActionBar().getTitle());
                    view.putExtra("orderAmount", CartActivity.this.gTotal);
                    CartActivity.this.startActivity(view);
                    return;
                }
                Toast.makeText(CartActivity.this, "The order amount should be more than 200 to book a table", 0).show();
            }
        });
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
        textView.setText("Price");
        textView.setGravity(17);
        textView.setTextColor(-7829368);
        textView.setGravity(GravityCompat.END);
        textView.setPadding(5, 5, 5, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setText("Qty");
        textView.setGravity(17);
        textView.setTextColor(-7829368);
        textView.setPadding(5, 5, 5, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setText("Total");
        textView.setGravity(17);
        textView.setTextColor(-7829368);
        textView.setPadding(35, 5, 5, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        this.tr.addView(textView);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-2, -2));
        textView = new TextView(this);
        textView.setText("----------------------------------");
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        textView.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setText("-------");
        textView.setGravity(GravityCompat.END);
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        textView.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setGravity(GravityCompat.END);
        textView.setText("----");
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        textView.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(textView);
        textView = new TextView(this);
        textView.setText("-------");
        textView.setGravity(17);
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setPadding(5, 0, 0, 0);
        textView.setTypeface(Typeface.DEFAULT, 1);
        textView.setLayoutParams(new LayoutParams(-1, -2));
        this.tr.addView(textView);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    @RequiresApi(api = 23)
    public void addData(ArrayList<String> arrayList) {
        Context context = this;
        ArrayList<String> arrayList2 = arrayList;
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = null;
            if (i2 >= arrayList.size()) {
                break;
            }
            context.TR = new TableRow(context);
            int i3 = -1;
            int i4 = -2;
            context.TR.setLayoutParams(new LayoutParams(-1, -2));
            View textView = new TextView(context);
            ArrayList arrayList3 = new ArrayList();
            SQLiteDatabase sQLiteDatabase = context.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select Distinct(item_sub_category) from cart where rest_name='");
            stringBuilder.append(context.Email);
            stringBuilder.append("' and item_quantity!=0 and  item_category ='");
            stringBuilder.append((String) arrayList2.get(i2));
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                do {
                    arrayList3.add(rawQuery.getString(i));
                } while (rawQuery.moveToNext());
            }
            textView.setText((CharSequence) arrayList2.get(i2));
            context.TR.addView(textView);
            context.tl.addView(context.TR, new TableLayout.LayoutParams(-1, -2));
            int i5 = i;
            while (i5 < arrayList3.size()) {
                if (!((String) arrayList3.get(i5)).equalsIgnoreCase("none")) {
                    context.TR1 = new TableRow(context);
                    context.TR1.setLayoutParams(new LayoutParams(i3, i4));
                    View textView2 = new TextView(context);
                    textView2.setText((CharSequence) arrayList3.get(i5));
                    textView2.setPadding(15, i, i, i);
                    context.TR1.addView(textView2);
                    context.tl.addView(context.TR1, new TableLayout.LayoutParams(i3, i4));
                }
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                ArrayList arrayList7 = new ArrayList();
                SQLiteDatabase sQLiteDatabase2 = context.sdb;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("select item_name, item_quantity, item_price, item_total from cart where rest_name='");
                stringBuilder2.append(context.Email);
                stringBuilder2.append("' and item_quantity!=0 and  item_category ='");
                stringBuilder2.append((String) arrayList2.get(i2));
                stringBuilder2.append("' and item_sub_category ='");
                stringBuilder2.append((String) arrayList3.get(i5));
                stringBuilder2.append("'");
                Cursor rawQuery2 = sQLiteDatabase2.rawQuery(stringBuilder2.toString(), strArr);
                int i6 = 1;
                if (rawQuery2.moveToFirst()) {
                    while (true) {
                        arrayList4.add(rawQuery2.getString(i).replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, " "));
                        arrayList5.add(rawQuery2.getString(1));
                        arrayList6.add(rawQuery2.getString(2));
                        arrayList7.add(rawQuery2.getString(3));
                        if (!rawQuery2.moveToNext()) {
                            break;
                        }
                        i = 0;
                    }
                }
                i = 0;
                while (i < arrayList4.size()) {
                    context.tr = new TableRow(context);
                    context.tr.setLayoutParams(new LayoutParams(i3, i4));
                    context.companyTV = new TextView(context);
                    context.companyTV.setText((CharSequence) arrayList4.get(i));
                    context.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    context.companyTV.setTypeface(Typeface.DEFAULT, i6);
                    context.companyTV.setPadding(30, 5, 5, 5);
                    context.companyTV.setLayoutParams(new LayoutParams(i4, i4));
                    context.tr.addView(context.companyTV);
                    context.valueTV = new TextView(context);
                    TextView textView3 = context.valueTV;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(getResources().getString(R.string.Rupee));
                    stringBuilder2.append(" ");
                    stringBuilder2.append((String) arrayList6.get(i));
                    textView3.setText(stringBuilder2.toString());
                    context.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    context.valueTV.setGravity(GravityCompat.END);
                    context.valueTV.setPadding(5, 5, 5, 5);
                    context.valueTV.setTypeface(Typeface.DEFAULT, 1);
                    context.valueTV.setLayoutParams(new LayoutParams(-2, -2));
                    context.tr.addView(context.valueTV);
                    context.valueTV1 = new TextView(context);
                    textView3 = context.valueTV1;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("X ");
                    stringBuilder3.append((String) arrayList5.get(i));
                    textView3.setText(stringBuilder3.toString());
                    context.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
                    context.valueTV1.setPadding(5, 5, 5, 5);
                    context.valueTV1.setTypeface(Typeface.DEFAULT, 1);
                    context.valueTV1.setLayoutParams(new LayoutParams(-2, -2));
                    context.tr.addView(context.valueTV1);
                    context.valueTV2 = new TextView(context);
                    textView3 = context.valueTV2;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(getResources().getString(R.string.Rupee));
                    stringBuilder4.append(" ");
                    stringBuilder4.append((String) arrayList7.get(i));
                    textView3.setText(stringBuilder4.toString());
                    context.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
                    context.valueTV2.setGravity(GravityCompat.START);
                    context.valueTV2.setPadding(35, 5, 5, 5);
                    context.valueTV2.setTypeface(Typeface.DEFAULT, 1);
                    context.valueTV2.setLayoutParams(new LayoutParams(-1, -2));
                    context.tr.addView(context.valueTV2);
                    context.tl.addView(context.tr, new TableLayout.LayoutParams(-1, -2));
                    i++;
                    i6 = 1;
                    i4 = -2;
                    i3 = -1;
                }
                int i7 = i3;
                int i8 = i4;
                i5++;
                i = 0;
                strArr = null;
            }
            i2++;
            i = 0;
        }
        ArrayList arrayList8 = new ArrayList();
        SQLiteDatabase sQLiteDatabase3 = context.sdb;
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("select item_total from cart where rest_name='");
        stringBuilder5.append(context.Email);
        stringBuilder5.append("' and item_quantity!=0");
        Cursor rawQuery3 = sQLiteDatabase3.rawQuery(stringBuilder5.toString(), null);
        if (rawQuery3.moveToFirst()) {
            do {
                arrayList8.add(rawQuery3.getString(0));
            } while (rawQuery3.moveToNext());
        }
        setTotal(arrayList8);
        setTax(arrayList8);
        setGrandTotal(arrayList8);
    }

    private void setGrandTotal(ArrayList<String> arrayList) {
        int i = 0;
        int i2 = 0;
        while (i < arrayList.size()) {
            i2 += Integer.parseInt((String) arrayList.get(i));
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
        this.companyTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        TextView textView = this.valueTV1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(arrayList.size());
        textView.setText(stringBuilder.toString());
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV1.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV1);
        this.gTotal = ((float) i2) + ((float) ((this.GST.intValue() * i2) / 100));
        this.valueTV2 = new TextView(this);
        arrayList = this.valueTV2;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(this.gTotal);
        arrayList.setText(stringBuilder2.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.START);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV2.setLayoutParams(new LayoutParams(-1, -2));
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    private void setTotal(ArrayList<String> arrayList) {
        int i = 0;
        int i2 = 0;
        while (i < arrayList.size()) {
            i2 += Integer.parseInt((String) arrayList.get(i));
            i++;
        }
        this.tr = new TableRow(this);
        this.tr.setLayoutParams(new LayoutParams(-1, -2));
        this.companyTV = new TextView(this);
        this.companyTV.setText("Total Items");
        this.companyTV.setGravity(GravityCompat.END);
        this.companyTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.companyTV.setTypeface(Typeface.DEFAULT, 1);
        this.companyTV.setPadding(5, 5, 5, 5);
        this.companyTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        TextView textView = this.valueTV1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(arrayList.size());
        textView.setText(stringBuilder.toString());
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV1.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV1);
        this.valueTV2 = new TextView(this);
        arrayList = this.valueTV2;
        stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(i2);
        arrayList.setText(stringBuilder.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.START);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV2.setLayoutParams(new LayoutParams(-1, -2));
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    private void setTax(ArrayList<String> arrayList) {
        int i = 0;
        int i2 = 0;
        while (i < arrayList.size()) {
            i2 += Integer.parseInt((String) arrayList.get(i));
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
        this.companyTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.companyTV);
        this.valueTV = new TextView(this);
        this.valueTV.setText(" ");
        this.valueTV.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.valueTV.setGravity(GravityCompat.END);
        this.valueTV.setPadding(5, 5, 5, 5);
        this.valueTV.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV);
        this.valueTV1 = new TextView(this);
        arrayList = this.valueTV1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.GST);
        stringBuilder.append("%");
        arrayList.setText(stringBuilder.toString());
        this.valueTV1.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV1.setPadding(5, 5, 5, 5);
        this.valueTV1.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV1.setLayoutParams(new LayoutParams(-2, -2));
        this.tr.addView(this.valueTV1);
        arrayList = (float) ((i2 * this.GST.intValue()) / 100);
        this.valueTV2 = new TextView(this);
        TextView textView = this.valueTV2;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(arrayList);
        textView.setText(stringBuilder2.toString());
        this.valueTV2.setTextColor(SupportMenu.CATEGORY_MASK);
        this.valueTV2.setGravity(GravityCompat.START);
        this.valueTV2.setPadding(35, 5, 5, 5);
        this.valueTV2.setTypeface(Typeface.DEFAULT, 1);
        this.valueTV2.setLayoutParams(new LayoutParams(-1, -2));
        this.tr.addView(this.valueTV2);
        this.tl.addView(this.tr, new TableLayout.LayoutParams(-1, -2));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem = menuItem.getItemId();
        if (menuItem == 16908332) {
            onBackPressed();
        } else if (menuItem == R.id.clearCart) {
            createAlert();
        }
        return true;
    }

    private void createAlert() {
        Builder builder = new Builder(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Are you sure you want to empty the cart for ");
        stringBuilder.append(getSupportActionBar().getTitle().toString());
        builder.setMessage(stringBuilder.toString());
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CartActivity.this.deleteFromSqlite(CartActivity.this.getSupportActionBar().getTitle().toString());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog create = builder.create();
        create.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                create.getButton(-2).setTextColor(CartActivity.this.getResources().getColor(R.color.colorPrimary));
                create.getButton(-1).setTextColor(CartActivity.this.getResources().getColor(R.color.colorPrimary));
            }
        });
        create.show();
    }

    private void deleteFromSqlite(String str) {
        str = this.sdb;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Delete from cart where rest_name='");
        stringBuilder.append(this.Email);
        stringBuilder.append("'");
        str.execSQL(stringBuilder.toString());
        Toast.makeText(this, "The cart has been cleared!!!", 0).show();
        startActivity(new Intent(this, CuisinicMenuActivity.class));
        finish();
    }
}
