package in.skaipal.kushalm.cuisinicuser.activity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.adapter.TabsPagerAdapter;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;

public class OrderActivity extends AppCompatActivity {
    public static String RestaurantName;
    public static MenuItem menuItem;
    private TabsPagerAdapter adapter;
    CartDatabase db;
    private Menu menu;
    SQLiteDatabase sdb;
    private String[] tabs = new String[]{"Open", "Processed", "Completed", "Cancelled"};
    int total = 0;
    private ViewPager viewPager;

    @SuppressLint({"ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_restaurant_items);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(this.tabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(this.tabs[1]));
        tabLayout.addTab(tabLayout.newTab().setText(this.tabs[2]));
        tabLayout.addTab(tabLayout.newTab().setText(this.tabs[3]));
        tabLayout.setTabGravity(0);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabReselected(Tab tab) {
            }

            public void onTabUnselected(Tab tab) {
            }

            public void onTabSelected(Tab tab) {
                OrderActivity.this.viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
