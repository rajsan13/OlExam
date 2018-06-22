package in.skaipal.kushalm.cuisinicuser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import java.io.File;

public class GalleryUtil extends Activity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int RESULT_SELECT_IMAGE = 100;
    private static final String TAG = "GalleryUtil";
    String mCurrentPhotoPath;
    File photoFile = null;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 100);
        } catch (Bundle bundle2) {
            bundle2.printStackTrace();
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100) {
            if (i2 != -1 || intent == null || intent.getData() == 0) {
                Log.i(TAG, "RESULT_CANCELED");
                setResult(0, new Intent());
                finish();
                return;
            }
            try {
                i2 = new String[]{"_data"};
                intent = getContentResolver().query(intent.getData(), i2, null, null, null);
                intent.moveToFirst();
                i2 = intent.getString(intent.getColumnIndex(i2[0]));
                intent.close();
                intent = new Intent();
                intent.putExtra("picturePath", i2);
                setResult(-1, intent);
                finish();
            } catch (int i3) {
                i3.printStackTrace();
                setResult(0, new Intent());
                finish();
            }
        }
    }
}
