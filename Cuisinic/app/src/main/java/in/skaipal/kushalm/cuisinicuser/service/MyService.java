package in.skaipal.kushalm.cuisinicuser.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.DrawerActivity;
import java.io.PrintStream;

public class MyService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final float LOCATION_DISTANCE = 10.0f;
    private static final int LOCATION_INTERVAL = 1000;
    private static final LatLng POINTA = new LatLng(20.2865d, 85.8473d);
    private static final String PREFERENCE = "service";
    private static final String TAG = "BOOMBOOMTESTGPS";
    LocationListener[] mLocationListeners = new LocationListener[]{new LocationListener("gps"), new LocationListener("network")};
    private LocationManager mLocationManager = null;
    private boolean notify = false;
    SharedPreferences sp;

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String str) {
            MyService myService = MyService.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("LocationListener ");
            stringBuilder.append(str);
            Log.e(myService, stringBuilder.toString());
            myService = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append("LocationListener ");
            stringBuilder.append(str);
            myService.print(stringBuilder.toString());
            this.mLastLocation = new Location(str);
        }

        public void onLocationChanged(Location location) {
            String str = MyService.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLocationChanged: ");
            stringBuilder.append(location);
            Log.e(str, stringBuilder.toString());
            PrintStream printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onLocationChanged: ");
            stringBuilder.append(location);
            printStream.print(stringBuilder.toString());
            Location location2 = new Location("target");
            LatLng[] latLngArr = new LatLng[1];
            int i = 0;
            latLngArr[0] = MyService.POINTA;
            int length = latLngArr.length;
            while (i < length) {
                LatLng latLng = latLngArr[i];
                location2.setLatitude(latLng.latitude);
                location2.setLongitude(latLng.longitude);
                if (location.distanceTo(location2) < 500.0f) {
                    if (!MyService.this.notify) {
                        MyService.this.addNotification();
                    }
                    String str2 = MyService.TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Inside: ");
                    stringBuilder2.append(location2);
                    Log.e(str2, stringBuilder2.toString());
                }
                i++;
            }
            this.mLastLocation.set(location);
        }

        public void onProviderDisabled(String str) {
            String str2 = MyService.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onProviderDisabled: ");
            stringBuilder.append(str);
            Log.e(str2, stringBuilder.toString());
        }

        public void onProviderEnabled(String str) {
            String str2 = MyService.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onProviderEnabled: ");
            stringBuilder.append(str);
            Log.e(str2, stringBuilder.toString());
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            i = MyService.TAG;
            bundle = new StringBuilder();
            bundle.append("onStatusChanged: ");
            bundle.append(str);
            Log.e(i, bundle.toString());
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.e(TAG, "onStartCommand");
        System.out.print("Start");
        super.onStartCommand(intent, i, i2);
        return 1;
    }

    public void onCreate() {
        this.sp = getApplicationContext().getSharedPreferences("service", 0);
        Editor edit = this.sp.edit();
        edit.putBoolean("serviceRunning", true);
        edit.apply();
        edit.commit();
        Log.d(TAG, "onCreate");
        System.out.print("Started");
        initializeLocationManager();
        try {
            this.mLocationManager.requestLocationUpdates("network", 1000, LOCATION_DISTANCE, this.mLocationListeners[1]);
        } catch (Throwable e) {
            Log.i(TAG, "fail to request location update, ignore", e);
        } catch (IllegalArgumentException e2) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("network provider does not exist, ");
            stringBuilder.append(e2.getMessage());
            Log.d(str, stringBuilder.toString());
        }
        try {
            this.mLocationManager.requestLocationUpdates("gps", 1000, LOCATION_DISTANCE, this.mLocationListeners[0]);
        } catch (Throwable e3) {
            Log.i(TAG, "fail to request location update, ignore", e3);
        } catch (IllegalArgumentException e22) {
            str = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("gps provider does not exist ");
            stringBuilder2.append(e22.getMessage());
            Log.d(str, stringBuilder2.toString());
        }
    }

    public void onDestroy() {
        int i = 0;
        this.sp = getApplicationContext().getSharedPreferences("service", 0);
        Editor edit = this.sp.edit();
        edit.putBoolean("serviceRunning", false);
        edit.apply();
        edit.commit();
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (this.mLocationManager != null) {
            while (i < this.mLocationListeners.length) {
                try {
                    this.mLocationManager.removeUpdates(this.mLocationListeners[i]);
                } catch (Throwable e) {
                    Log.i(TAG, "fail to remove location listners, ignore", e);
                }
                i++;
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (this.mLocationManager == null) {
            this.mLocationManager = (LocationManager) getApplicationContext().getSystemService("location");
        }
    }

    private void addNotification() {
        this.notify = true;
        long[] jArr = new long[]{0, 100, 200, 300};
        Builder contentText = new Builder(this).setSmallIcon(R.drawable.cuisinic_logo).setContentTitle("Nearby Restaurant").setSound(RingtoneManager.getDefaultUri(2)).setVibrate(jArr).setContentText("As you are inside the radius of our Restaurant premises, you are getting this notification");
        contentText.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, DrawerActivity.class), 134217728));
        ((NotificationManager) getSystemService("notification")).notify(0, contentText.build());
    }
}
