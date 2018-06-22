package in.skaipal.kushalm.cuisinicuser.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    SharedPreferences sp;

    private void sendRegistrationToServer(String str) {
    }

    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refreshed token: ");
        stringBuilder.append(token);
        Log.d(str, stringBuilder.toString());
        if (token != null) {
            this.sp = getSharedPreferences("fcm", 0);
            Editor edit = this.sp.edit();
            edit.clear();
            edit.putString("id", token);
            edit.putString("success", "true");
            edit.apply();
            edit.commit();
        }
        Intent intent = new Intent("tokenReceiver");
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token", token);
        instance.sendBroadcast(intent);
    }
}
