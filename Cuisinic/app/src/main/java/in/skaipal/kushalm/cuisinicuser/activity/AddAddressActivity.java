package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;

public class AddAddressActivity extends AppCompatActivity {
    String aa;
    Button add;
    EditText address;
    CartDatabase db;
    Button delete;
    String mm;
    EditText mobile;
    EditText name;
    String nn;
    SQLiteDatabase sdb;
    String toDo;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.add_address_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.db = new CartDatabase(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.toDo = bundle.getString("do");
            if (this.toDo.equals("edit")) {
                this.nn = bundle.getString("name");
                this.mm = bundle.getString("mobile");
                this.aa = bundle.getString("address");
            }
        }
        initializeViews();
        this.add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = AddAddressActivity.this.name.getText().toString();
                String obj = AddAddressActivity.this.mobile.getText().toString();
                String obj2 = AddAddressActivity.this.address.getText().toString();
                if (view.equals("")) {
                    AddAddressActivity.this.name.setError("This filed cannot be left empty");
                } else if (obj.equals("")) {
                    AddAddressActivity.this.mobile.setError("This filed cannot be left empty");
                } else if (obj2.equals("")) {
                    AddAddressActivity.this.address.setError("This filed cannot be left empty");
                } else if (AddAddressActivity.this.toDo.equals("add")) {
                    AddAddressActivity.this.sdb = AddAddressActivity.this.db.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", view);
                    contentValues.put("mobile", obj);
                    contentValues.put("address", obj2);
                    AddAddressActivity.this.sdb.insert("address", null, contentValues);
                    Toast.makeText(AddAddressActivity.this, "Address Added", 0).show();
                    AddAddressActivity.this.setResult(0, new Intent());
                    AddAddressActivity.this.finish();
                } else if (AddAddressActivity.this.toDo.equals("edit")) {
                    String[] strArr = new String[]{AddAddressActivity.this.nn, AddAddressActivity.this.mm, AddAddressActivity.this.aa};
                    AddAddressActivity.this.sdb = AddAddressActivity.this.db.getWritableDatabase();
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("name", view);
                    contentValues2.put("mobile", obj);
                    contentValues2.put("address", obj2);
                    AddAddressActivity.this.sdb.update("address", contentValues2, "name=? AND mobile=? AND address=?", strArr);
                    AddAddressActivity.this.onBackPressed();
                    AddAddressActivity.this.finish();
                }
            }
        });
        this.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new String[]{AddAddressActivity.this.nn, AddAddressActivity.this.mm, AddAddressActivity.this.aa};
                AddAddressActivity.this.sdb = AddAddressActivity.this.db.getWritableDatabase();
                view = AddAddressActivity.this.sdb;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Delete from address where name='");
                stringBuilder.append(AddAddressActivity.this.nn);
                stringBuilder.append("' AND mobile='");
                stringBuilder.append(AddAddressActivity.this.mm);
                stringBuilder.append("' AND address='");
                stringBuilder.append(AddAddressActivity.this.aa);
                stringBuilder.append("'");
                view.execSQL(stringBuilder.toString());
                Toast.makeText(AddAddressActivity.this, "Address Deleted", 0).show();
                AddAddressActivity.this.onBackPressed();
                AddAddressActivity.this.finish();
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

    private void initializeViews() {
        this.name = (EditText) findViewById(R.id.addNameET);
        this.mobile = (EditText) findViewById(R.id.addMobileET);
        this.address = (EditText) findViewById(R.id.addAddressET);
        this.add = (Button) findViewById(R.id.addAddressNew);
        this.delete = (Button) findViewById(R.id.deleteAddressNew);
        if (this.toDo.equals("add")) {
            this.delete.setVisibility(8);
            return;
        }
        this.name.setText(this.nn);
        this.mobile.setText(this.mm);
        this.address.setText(this.aa);
        this.delete.setVisibility(0);
    }
}
