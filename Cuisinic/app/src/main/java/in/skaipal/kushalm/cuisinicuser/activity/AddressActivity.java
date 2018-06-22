package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.adapter.AddressAdapter;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.addressModel;
import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    Button addAddressButton;
    ArrayList<String> address;
    TextView count;
    CartDatabase db;
    AddressAdapter mAdapter;
    ArrayList<String> mobile;
    ArrayList<String> name;
    TextView noAddress;
    RecyclerView recyclerView;
    SQLiteDatabase sdb;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.main_layout_fragment_profile);
        getSupportActionBar().setTitle((CharSequence) "Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialiseViews();
        getAddressFromSQLite();
        this.addAddressButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(AddressActivity.this, AddAddressActivity.class);
                view.putExtra("do", "add");
                AddressActivity.this.startActivityForResult(view, 1);
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

    private void getAddressFromSQLite() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r5 = this;
        r0 = 8;
        r1 = 0;
        r2 = r5.sdb;	 Catch:{ Exception -> 0x005c }
        r3 = "Select * from address";	 Catch:{ Exception -> 0x005c }
        r4 = 0;	 Catch:{ Exception -> 0x005c }
        r2 = r2.rawQuery(r3, r4);	 Catch:{ Exception -> 0x005c }
        r3 = r2.moveToFirst();	 Catch:{ Exception -> 0x005c }
        if (r3 == 0) goto L_0x0035;	 Catch:{ Exception -> 0x005c }
    L_0x0012:
        r3 = r5.name;	 Catch:{ Exception -> 0x005c }
        r4 = r2.getString(r1);	 Catch:{ Exception -> 0x005c }
        r3.add(r4);	 Catch:{ Exception -> 0x005c }
        r3 = r5.mobile;	 Catch:{ Exception -> 0x005c }
        r4 = 1;	 Catch:{ Exception -> 0x005c }
        r4 = r2.getString(r4);	 Catch:{ Exception -> 0x005c }
        r3.add(r4);	 Catch:{ Exception -> 0x005c }
        r3 = r5.address;	 Catch:{ Exception -> 0x005c }
        r4 = 2;	 Catch:{ Exception -> 0x005c }
        r4 = r2.getString(r4);	 Catch:{ Exception -> 0x005c }
        r3.add(r4);	 Catch:{ Exception -> 0x005c }
        r3 = r2.moveToNext();	 Catch:{ Exception -> 0x005c }
        if (r3 != 0) goto L_0x0012;	 Catch:{ Exception -> 0x005c }
    L_0x0035:
        r2 = new android.support.v7.widget.LinearLayoutManager;	 Catch:{ Exception -> 0x005c }
        r2.<init>(r5);	 Catch:{ Exception -> 0x005c }
        r3 = r5.recyclerView;	 Catch:{ Exception -> 0x005c }
        r3.setLayoutManager(r2);	 Catch:{ Exception -> 0x005c }
        r2 = new in.skaipal.kushalm.cuisinicuser.adapter.AddressAdapter;	 Catch:{ Exception -> 0x005c }
        r3 = r5.getDataSet();	 Catch:{ Exception -> 0x005c }
        r2.<init>(r5, r3);	 Catch:{ Exception -> 0x005c }
        r5.mAdapter = r2;	 Catch:{ Exception -> 0x005c }
        r2 = r5.recyclerView;	 Catch:{ Exception -> 0x005c }
        r3 = new android.support.v7.widget.DefaultItemAnimator;	 Catch:{ Exception -> 0x005c }
        r3.<init>();	 Catch:{ Exception -> 0x005c }
        r2.setItemAnimator(r3);	 Catch:{ Exception -> 0x005c }
        r2 = r5.recyclerView;	 Catch:{ Exception -> 0x005c }
        r3 = r5.mAdapter;	 Catch:{ Exception -> 0x005c }
        r2.setAdapter(r3);	 Catch:{ Exception -> 0x005c }
        goto L_0x006d;
    L_0x005c:
        r2 = r5.noAddress;
        r2.setVisibility(r1);
        r2 = r5.recyclerView;
        r2.setVisibility(r0);
        r2 = r5.count;
        r3 = "0";
        r2.setText(r3);
    L_0x006d:
        r2 = r5.name;
        r2 = r2.size();
        if (r2 <= 0) goto L_0x009c;
    L_0x0075:
        r2 = r5.noAddress;
        r2.setVisibility(r0);
        r0 = r5.recyclerView;
        r0.setVisibility(r1);
        r0 = r5.count;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "";
        r1.append(r2);
        r2 = r5.name;
        r2 = r2.size();
        r1.append(r2);
        r1 = r1.toString();
        r0.setText(r1);
        goto L_0x00ad;
    L_0x009c:
        r2 = r5.noAddress;
        r2.setVisibility(r1);
        r1 = r5.recyclerView;
        r1.setVisibility(r0);
        r0 = r5.count;
        r1 = "0";
        r0.setText(r1);
    L_0x00ad:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.AddressActivity.getAddressFromSQLite():void");
    }

    private void initialiseViews() {
        this.addAddressButton = (Button) findViewById(R.id.addAddressButton);
        this.count = (TextView) findViewById(R.id.addressCount);
        this.recyclerView = (RecyclerView) findViewById(R.id.profile_address_recycler);
        this.noAddress = (TextView) findViewById(R.id.noAddress);
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        this.name = new ArrayList();
        this.mobile = new ArrayList();
        this.address = new ArrayList();
        this.noAddress.setVisibility(8);
    }

    private ArrayList<addressModel> getDataSet() {
        ArrayList<addressModel> arrayList = new ArrayList();
        for (int i = 0; i < this.name.size(); i++) {
            arrayList.add(i, new addressModel((String) this.name.get(i), (String) this.mobile.get(i), (String) this.address.get(i)));
        }
        return arrayList;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            if (i2 == 0) {
                this.name.clear();
                this.address.clear();
                this.mobile.clear();
                try {
                    i2 = this.sdb.rawQuery("Select * from address", null);
                    if (i2.moveToFirst()) {
                        do {
                            this.name.add(i2.getString(0));
                            this.mobile.add(i2.getString(1));
                            this.address.add(i2.getString(2));
                        } while (i2.moveToNext());
                    }
                    this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    this.mAdapter = new AddressAdapter(this, getDataSet());
                    this.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    this.recyclerView.setAdapter(this.mAdapter);
                } catch (int i22) {
                    i22.printStackTrace();
                }
            }
            if (this.name.size() > 0) {
                this.noAddress.setVisibility(8);
                this.recyclerView.setVisibility(0);
                i = this.count;
                i22 = new StringBuilder();
                i22.append("");
                i22.append(this.name.size());
                i.setText(i22.toString());
                return;
            }
            this.noAddress.setVisibility(0);
            this.recyclerView.setVisibility(8);
            this.count.setText("0");
        }
    }
}
