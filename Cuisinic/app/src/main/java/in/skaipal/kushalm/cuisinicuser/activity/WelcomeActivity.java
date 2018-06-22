package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.adapter.ViewPagerAdapter;

public class WelcomeActivity extends AppCompatActivity implements OnPageChangeListener {
    int Current;
    private int delay = 2000;
    private ImageView[] dots;
    private int dotsCount;
    private Handler handler;
    private ViewPager intro_images;
    Button login;
    private ViewPagerAdapter mAdapter;
    private int[] mImageResources = new int[]{R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3};
    private int page = 0;
    private LinearLayout pager_indicator;
    Runnable runnable = new Runnable() {
        public void run() {
            if (WelcomeActivity.this.mAdapter.getCount() == WelcomeActivity.this.page) {
                WelcomeActivity.this.page = 0;
            } else {
                WelcomeActivity.this.page = WelcomeActivity.this.page + 1;
            }
            WelcomeActivity.this.intro_images.setCurrentItem(WelcomeActivity.this.page, true);
            WelcomeActivity.this.handler.postDelayed(this, (long) WelcomeActivity.this.delay);
        }
    };
    Button signup;
    protected View view;

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
            try {
                getWindow().setStatusBarColor(0);
            } catch (Bundle bundle2) {
                bundle2.printStackTrace();
            }
        }
        setContentView((int) R.layout.activity_welcome);
        this.handler = new Handler();
        intializeViews();
        setPager();
        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                WelcomeActivity.this.finish();
            }
        });
        this.signup.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, SignupActivity.class));
                WelcomeActivity.this.finish();
            }
        });
    }

    private void setPager() {
        this.dotsCount = this.mAdapter.getCount();
        this.dots = new ImageView[this.dotsCount];
        for (int i = 0; i < this.dotsCount; i++) {
            this.dots[i] = new ImageView(this);
            this.dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(4, 0, 4, 0);
            this.pager_indicator.addView(this.dots[i], layoutParams);
        }
        this.dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    private void intializeViews() {
        this.login = (Button) findViewById(R.id.welcomeLogin);
        this.signup = (Button) findViewById(R.id.welcomeSignup);
        this.intro_images = (ViewPager) findViewById(R.id.welcomePager);
        this.pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        this.mAdapter = new ViewPagerAdapter(this, this.mImageResources);
        this.intro_images.setAdapter(this.mAdapter);
        this.intro_images.setCurrentItem(0);
        this.intro_images.setOnPageChangeListener(this);
    }

    public void onPageSelected(int i) {
        this.page = i;
        for (int i2 = 0; i2 < this.dotsCount; i2++) {
            this.dots[i2].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        this.dots[i].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    protected void onResume() {
        super.onResume();
        this.handler.postDelayed(this.runnable, (long) this.delay);
    }

    protected void onPause() {
        super.onPause();
        this.handler.removeCallbacks(this.runnable);
    }

    public void onBackPressed() {
        finish();
    }
}
