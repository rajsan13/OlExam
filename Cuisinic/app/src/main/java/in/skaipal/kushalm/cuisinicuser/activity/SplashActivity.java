package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class SplashActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView Logo;
    FirebaseAuth auth;
    private LocationManager locationManager;
    Handler mHandler = new Handler();
    boolean permission = false;
    String provider;
    UserSessionManager session;
    FirebaseUser user;

    protected void onCreate(@Nullable Bundle bundle) {
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
        super.onCreate(bundle);
        Fabric.with(this, new Kit[]{new Crashlytics()});
        setContentView((int) R.layout.activity_splash);
        this.auth = FirebaseAuth.getInstance();
        this.session = new UserSessionManager(this);
        this.Logo = (ImageView) findViewById(R.id.SplashLogo);
        this.Logo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_down));
        bundle = new Handler();
        this.locationManager = (LocationManager) getSystemService("location");
        this.provider = this.locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();
        if (this.permission != null) {
            doStuff();
        }
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
                new Builder(this).setCancelable(false).setTitle((CharSequence) "Location Permission").setMessage((CharSequence) "Grant permission to the app to access device location").setPositiveButton((CharSequence) "OK", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                    }
                }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            }
            this.permission = false;
            return;
        }
        this.permission = true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 99) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                finish();
            } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                doStuff();
            }
        }
    }

    public void doStuff() {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                if (SplashActivity.this.session.isUserLoggedIn()) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, CuisinicMenuActivity.class));
                    SplashActivity.this.finish();
                    return;
                }
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
