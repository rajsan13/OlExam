package in.skaipal.kushalm.cuisinicuser.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.adapter.AddressAdapter;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.addressModel;
import java.util.ArrayList;

public class MainFragmentProfile extends Fragment {
    Button addAddress;
    ArrayList<String> address;
    TextView count;
    CartDatabase db;
    AddressAdapter mAdapter;
    ArrayList<String> mobile;
    ArrayList<String> name;
    TextView noAddress;
    RecyclerView recyclerView;
    SQLiteDatabase sdb;

    public static MainFragmentProfile newInstance() {
        return new MainFragmentProfile();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @android.support.annotation.Nullable
    public android.view.View onCreateView(android.view.LayoutInflater r4, @android.support.annotation.Nullable android.view.ViewGroup r5, @android.support.annotation.Nullable android.os.Bundle r6) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r3 = this;
        r6 = 0;
        r0 = 2131427432; // 0x7f0b0068 float:1.847648E38 double:1.053065071E-314;
        r4 = r4.inflate(r0, r5, r6);
        r5 = 2131296587; // 0x7f09014b float:1.8211095E38 double:1.0530004247E-314;
        r5 = r4.findViewById(r5);
        r5 = (android.support.v7.widget.RecyclerView) r5;
        r3.recyclerView = r5;
        r5 = 2131296530; // 0x7f090112 float:1.821098E38 double:1.0530003966E-314;
        r5 = r4.findViewById(r5);
        r5 = (android.widget.TextView) r5;
        r3.noAddress = r5;
        r5 = 2131296324; // 0x7f090044 float:1.8210561E38 double:1.053000295E-314;
        r5 = r4.findViewById(r5);
        r5 = (android.widget.TextView) r5;
        r3.count = r5;
        r5 = r3.noAddress;
        r0 = 8;
        r5.setVisibility(r0);
        r5 = 2131296313; // 0x7f090039 float:1.821054E38 double:1.0530002894E-314;
        r5 = r4.findViewById(r5);
        r5 = (android.widget.Button) r5;
        r3.addAddress = r5;
        r5 = new java.util.ArrayList;
        r5.<init>();
        r3.name = r5;
        r5 = new java.util.ArrayList;
        r5.<init>();
        r3.mobile = r5;
        r5 = new java.util.ArrayList;
        r5.<init>();
        r3.address = r5;
        r5 = new in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
        r1 = r3.getContext();
        r5.<init>(r1);
        r3.db = r5;
        r5 = r3.db;
        r5 = r5.getWritableDatabase();
        r3.sdb = r5;
        r5 = r3.sdb;	 Catch:{ Exception -> 0x00c4 }
        r1 = "Select * from address";	 Catch:{ Exception -> 0x00c4 }
        r2 = 0;	 Catch:{ Exception -> 0x00c4 }
        r5 = r5.rawQuery(r1, r2);	 Catch:{ Exception -> 0x00c4 }
        r1 = r5.moveToFirst();	 Catch:{ Exception -> 0x00c4 }
        if (r1 == 0) goto L_0x0095;	 Catch:{ Exception -> 0x00c4 }
    L_0x0072:
        r1 = r3.name;	 Catch:{ Exception -> 0x00c4 }
        r2 = r5.getString(r6);	 Catch:{ Exception -> 0x00c4 }
        r1.add(r2);	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.mobile;	 Catch:{ Exception -> 0x00c4 }
        r2 = 1;	 Catch:{ Exception -> 0x00c4 }
        r2 = r5.getString(r2);	 Catch:{ Exception -> 0x00c4 }
        r1.add(r2);	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.address;	 Catch:{ Exception -> 0x00c4 }
        r2 = 2;	 Catch:{ Exception -> 0x00c4 }
        r2 = r5.getString(r2);	 Catch:{ Exception -> 0x00c4 }
        r1.add(r2);	 Catch:{ Exception -> 0x00c4 }
        r1 = r5.moveToNext();	 Catch:{ Exception -> 0x00c4 }
        if (r1 != 0) goto L_0x0072;	 Catch:{ Exception -> 0x00c4 }
    L_0x0095:
        r5 = new android.support.v7.widget.LinearLayoutManager;	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.getContext();	 Catch:{ Exception -> 0x00c4 }
        r5.<init>(r1);	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.recyclerView;	 Catch:{ Exception -> 0x00c4 }
        r1.setLayoutManager(r5);	 Catch:{ Exception -> 0x00c4 }
        r5 = new in.skaipal.kushalm.cuisinicuser.adapter.AddressAdapter;	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.getContext();	 Catch:{ Exception -> 0x00c4 }
        r2 = r3.getDataSet();	 Catch:{ Exception -> 0x00c4 }
        r5.<init>(r1, r2);	 Catch:{ Exception -> 0x00c4 }
        r3.mAdapter = r5;	 Catch:{ Exception -> 0x00c4 }
        r5 = r3.recyclerView;	 Catch:{ Exception -> 0x00c4 }
        r1 = new android.support.v7.widget.DefaultItemAnimator;	 Catch:{ Exception -> 0x00c4 }
        r1.<init>();	 Catch:{ Exception -> 0x00c4 }
        r5.setItemAnimator(r1);	 Catch:{ Exception -> 0x00c4 }
        r5 = r3.recyclerView;	 Catch:{ Exception -> 0x00c4 }
        r1 = r3.mAdapter;	 Catch:{ Exception -> 0x00c4 }
        r5.setAdapter(r1);	 Catch:{ Exception -> 0x00c4 }
        goto L_0x00d5;
    L_0x00c4:
        r5 = r3.noAddress;
        r5.setVisibility(r6);
        r5 = r3.recyclerView;
        r5.setVisibility(r0);
        r5 = r3.count;
        r1 = "0";
        r5.setText(r1);
    L_0x00d5:
        r5 = r3.name;
        r5 = r5.size();
        if (r5 <= 0) goto L_0x0104;
    L_0x00dd:
        r5 = r3.noAddress;
        r5.setVisibility(r0);
        r5 = r3.recyclerView;
        r5.setVisibility(r6);
        r5 = r3.count;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r0 = "";
        r6.append(r0);
        r0 = r3.name;
        r0 = r0.size();
        r6.append(r0);
        r6 = r6.toString();
        r5.setText(r6);
        goto L_0x0115;
    L_0x0104:
        r5 = r3.noAddress;
        r5.setVisibility(r6);
        r5 = r3.recyclerView;
        r5.setVisibility(r0);
        r5 = r3.count;
        r6 = "0";
        r5.setText(r6);
    L_0x0115:
        r5 = r3.addAddress;
        r6 = new in.skaipal.kushalm.cuisinicuser.fragment.MainFragmentProfile$1;
        r6.<init>();
        r5.setOnClickListener(r6);
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.fragment.MainFragmentProfile.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle):android.view.View");
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
                    this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    this.mAdapter = new AddressAdapter(getContext(), getDataSet());
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
