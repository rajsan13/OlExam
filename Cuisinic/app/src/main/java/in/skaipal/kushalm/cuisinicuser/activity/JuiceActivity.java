package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;

public class JuiceActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static MenuItem menuItem;
    private String Email = null;
    CardView acvCard;
    CartDatabase db;
    CardView detoxCard;
    CardView freshJuiceCard;
    private Menu menu;
    CardView milkshakeCard;
    CardView mintCoolerCard;
    CardView mocktailCard;
    SQLiteDatabase sdb;
    private SharedPreferences sp;
    int total = 0;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cuisinic_menu_juice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Juice");
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        initialiseViews();
        updateCart();
        this.detoxCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "Detox");
                JuiceActivity.this.startActivity(view);
            }
        });
        this.acvCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "ACV");
                JuiceActivity.this.startActivity(view);
            }
        });
        this.mintCoolerCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "Mint Cooler");
                JuiceActivity.this.startActivity(view);
            }
        });
        this.mocktailCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "Mocktails");
                JuiceActivity.this.startActivity(view);
            }
        });
        this.milkshakeCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "Milk Shake");
                JuiceActivity.this.startActivity(view);
            }
        });
        this.freshJuiceCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(JuiceActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Juice");
                view.putExtra("sub-category", "Fresh Juice");
                JuiceActivity.this.startActivity(view);
            }
        });
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

    private void initialiseViews() {
        this.detoxCard = (CardView) findViewById(R.id.detoxCard);
        this.acvCard = (CardView) findViewById(R.id.acvCard);
        this.mintCoolerCard = (CardView) findViewById(R.id.mintCoolerCard);
        this.mocktailCard = (CardView) findViewById(R.id.mocktailCard);
        this.milkshakeCard = (CardView) findViewById(R.id.milkshakeCard);
        this.freshJuiceCard = (CardView) findViewById(R.id.freshJuiceCard);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
    }

    private void updateCart() {
        try {
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT item_name FROM cart where item_quantity!=0 and rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            sQLiteDatabase.rawQuery(stringBuilder.toString(), null).getCount();
            sQLiteDatabase = this.sdb;
            stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT SUM(item_total) FROM cart where rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                this.total = rawQuery.getInt(0);
            }
            MenuItem menuItem = menuItem;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.Rupee));
            stringBuilder.append(" ");
            stringBuilder.append(this.total);
            menuItem.setTitle(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        updateCart();
    }
}
