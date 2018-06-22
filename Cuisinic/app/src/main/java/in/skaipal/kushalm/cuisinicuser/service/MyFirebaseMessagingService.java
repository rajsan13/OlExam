package in.skaipal.kushalm.cuisinicuser.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.OrderActivity;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String BROADCAST_ACTION = "in.skaipal.kushalm.cuisinicuser.getdata";
    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ");
        stringBuilder.append(remoteMessage.getFrom());
        Log.d(str, stringBuilder.toString());
        if (remoteMessage.getData().size() > 0) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message data payload: ");
            stringBuilder.append(remoteMessage.getData());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message data payload(message): ");
            stringBuilder.append((String) remoteMessage.getData().get(SettingsJsonConstants.PROMPT_MESSAGE_KEY));
            Log.d(str, stringBuilder.toString());
        }
        sendNotification((String) remoteMessage.getData().get(SettingsJsonConstants.PROMPT_MESSAGE_KEY));
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message Notification Body: ");
            stringBuilder.append(remoteMessage.getNotification().getBody());
            Log.d(str, stringBuilder.toString());
        }
    }

    private void sendNotification(String str) {
        createNotificationChannel();
        Intent intent = new Intent(this, OrderActivity.class);
        intent.addFlags(67108864);
        ((NotificationManager) getSystemService("notification")).notify((int) ((new Date().getTime() / 1000) % 2147483647L), new Builder(this, "0").setSmallIcon(R.drawable.cuisinic_logo).setContentTitle("Order Status").setStyle(new BigTextStyle().bigText(str)).setContentText(str).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(PendingIntent.getActivity(this, 0, intent, ErrorDialogData.SUPPRESSED)).setPriority(0).build());
    }

    private void createNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            CharSequence string = getString(R.string.channel_name);
            String string2 = getString(R.string.channel_description);
            NotificationChannel notificationChannel = new NotificationChannel("0", string, 3);
            notificationChannel.setDescription(string2);
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
        }
    }
}
