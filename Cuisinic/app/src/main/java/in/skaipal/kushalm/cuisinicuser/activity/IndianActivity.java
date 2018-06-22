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

public class IndianActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static MenuItem menuItem;
    private String Email = null;
    CardView daalCard;
    CartDatabase db;
    private Menu menu;
    CardView nvCourseCard;
    CardView nvStarterCard;
    CardView riceCard;
    CardView rotiCard;
    SQLiteDatabase sdb;
    CardView soupCard;
    private SharedPreferences sp;
    int total = 0;
    CardView vCourseCard;
    CardView vStarterCard;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cuisinic_menu_indian);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "Juice");
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        initialiseViews();
        updateCart();
        this.soupCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "Soups");
                IndianActivity.this.startActivity(view);
            }
        });
        this.rotiCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "ROTI");
                IndianActivity.this.startActivity(view);
            }
        });
        this.vStarterCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "Starters veg");
                IndianActivity.this.startActivity(view);
            }
        });
        this.nvStarterCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "Starters non veg");
                IndianActivity.this.startActivity(view);
            }
        });
        this.vCourseCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "Main Course veg");
                IndianActivity.this.startActivity(view);
            }
        });
        this.nvCourseCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "Main Course non veg");
                IndianActivity.this.startActivity(view);
            }
        });
        this.riceCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "RICE");
                IndianActivity.this.startActivity(view);
            }
        });
        this.daalCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(IndianActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "DAL");
                IndianActivity.this.startActivity(view);
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
        this.soupCard = (CardView) findViewById(R.id.soupsCard);
        this.rotiCard = (CardView) findViewById(R.id.rotiCard);
        this.vStarterCard = (CardView) findViewById(R.id.vstarterCard);
        this.nvStarterCard = (CardView) findViewById(R.id.nvStarterCard);
        this.vCourseCard = (CardView) findViewById(R.id.vCourseCard);
        this.nvCourseCard = (CardView) findViewById(R.id.nvCourseCard);
        this.riceCard = (CardView) findViewById(R.id.riceCard);
        this.daalCard = (CardView) findViewById(R.id.dalCard);
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
