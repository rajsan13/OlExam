package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.adapter.DrawerAdapter;
import in.skaipal.kushalm.cuisinicuser.adapter.DrawerAdapter.OnItemSelectedListener;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.fragment.AccountFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.CouponFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.ForumFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.ReviewFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.SettingFragment;
import in.skaipal.kushalm.cuisinicuser.fragment.TravelFragment;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import in.skaipal.kushalm.cuisinicuser.model.drawer.DrawerItem;
import in.skaipal.kushalm.cuisinicuser.model.drawer.SimpleItem;
import java.util.Arrays;

public class DrawerActivity extends AppCompatActivity implements OnItemSelectedListener {
    public static final String KEY_MAIL = "mail";
    private static final int POS_ACCOUNT = 0;
    private static final int POS_COUPON = 1;
    private static final int POS_FORUM = 4;
    private static final int POS_LOGOUT = 6;
    private static final int POS_REVIEW = 3;
    private static final int POS_SETTING = 5;
    private static final int POS_TRAVEL = 2;
    private static final String PREFER_NAME = "SessionSharedPreference";
    String MAIL;
    String NAME;
    AppBarLayout appBar;
    CartDatabase db;
    TextView name;
    private Drawable[] screenIcons;
    private String[] screenTitles;
    SQLiteDatabase sdb;
    UserSessionManager session;
    private SlidingRootNav slidingRootNav;
    private SharedPreferences sp;
    Toolbar toolbar;

    @RequiresApi(api = 23)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.drawer_layout);
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.MAIL = this.sp.getString("mail", null);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.appBar = (AppBarLayout) findViewById(R.id.appBar);
        this.appBar.setElevation(0.0f);
        this.toolbar.setElevation(0.0f);
        this.session = new UserSessionManager(this);
        setSupportActionBar(this.toolbar);
        this.screenIcons = loadScreenIcons();
        this.screenTitles = loadScreenTitles();
        Adapter drawerAdapter = new DrawerAdapter(Arrays.asList(new DrawerItem[]{createItemFor(0).setChecked(true), createItemFor(1), createItemFor(2), createItemFor(3), createItemFor(4), createItemFor(5)}));
        drawerAdapter.setListener(this);
        this.slidingRootNav = new SlidingRootNavBuilder(this).withToolbarMenuToggle(this.toolbar).withMenuOpened(false).withContentClickableWhenMenuOpened(false).withSavedState(bundle).withMenuLayout(R.layout.menu_left_drawer).inject();
        this.slidingRootNav.openMenu(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        this.name = (TextView) findViewById(R.id.drawerName);
        ((Button) findViewById(R.id.logout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                DrawerActivity.this.session.logoutUser();
                DrawerActivity.this.finish();
            }
        });
        try {
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Select name from user where mail='");
            stringBuilder.append(this.MAIL);
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                do {
                    this.NAME = rawQuery.getString(0);
                } while (rawQuery.moveToNext());
            }
            if (!this.NAME.equals("null")) {
                this.name.setText(this.NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drawerAdapter);
        showFragment(AccountFragment.createFor(this.screenTitles[0]));
    }

    @RequiresApi(api = 23)
    public void onItemSelected(int i) {
        if (i == 1) {
            this.appBar.setAlpha(1.0f);
            showFragment(CouponFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        } else if (i == 0) {
            this.appBar.setElevation(0.0f);
            this.toolbar.setElevation(0.0f);
            showFragment(AccountFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        } else if (i == 3) {
            this.appBar.setAlpha(1.0f);
            showFragment(ReviewFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        } else if (i == 4) {
            this.appBar.setAlpha(1.0f);
            showFragment(ForumFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        } else if (i == 2) {
            this.appBar.setAlpha(1.0f);
            showFragment(TravelFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        } else if (i == 5) {
            this.appBar.setAlpha(1.0f);
            showFragment(SettingFragment.createFor(this.screenTitles[i]));
            this.slidingRootNav.closeMenu();
        }
    }

    public static void setOverflowButtonColor(Toolbar toolbar, int i) {
        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon = DrawableCompat.wrap(overflowIcon);
            DrawableCompat.setTint(overflowIcon.mutate(), i);
            toolbar.setOverflowIcon(overflowIcon);
        }
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private DrawerItem createItemFor(int i) {
        return new SimpleItem(this.screenIcons[i], this.screenTitles[i]).withIconTint(color(R.color.textColorPrimary)).withTextTint(color(R.color.textColorPrimary)).withSelectedIconTint(color(17170443)).withSelectedTextTint(color(17170443));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] drawableArr = new Drawable[obtainTypedArray.length()];
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            int resourceId = obtainTypedArray.getResourceId(i, 0);
            if (resourceId != 0) {
                drawableArr[i] = ContextCompat.getDrawable(this, resourceId);
            }
        }
        obtainTypedArray.recycle();
        return drawableArr;
    }

    @ColorInt
    private int color(@ColorRes int i) {
        return ContextCompat.getColor(this, i);
    }

    public void onResume() {
        super.onResume();
        try {
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Select name from user where mail='");
            stringBuilder.append(this.MAIL);
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                do {
                    this.NAME = rawQuery.getString(0);
                } while (rawQuery.moveToNext());
            }
            if (!this.NAME.equals("null")) {
                this.name.setText(this.NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (this.slidingRootNav.isMenuOpened()) {
            startActivity(new Intent(this, CuisinicMenuActivity.class));
            finish();
            return;
        }
        this.slidingRootNav.openMenu(true);
    }
}
