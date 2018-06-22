package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.Volley.VolleyMultipartRequest;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    public static final String KEY_PASSWORD = "password";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String PREFER_NAME = "SessionSharedPreference";
    private static String updateUrl = APIUrls.updateProfileUrl;
    String IMAGE;
    String MAIL;
    String MOBILE;
    String NAME;
    LinearLayout changeImage;
    TextView changePassword;
    CartDatabase db;
    CardView doneButton;
    CropImageView image;
    private Uri mCropImageUri;
    private ImageView mCropImageView;
    EditText mail;
    EditText mobile;
    EditText name;
    SharedPreferences pref;
    ProgressBar progress;
    LinearLayout progressLayout;
    AVLoadingIndicatorView progressUpload;
    SQLiteDatabase sdb;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialiseViews();
        bundle = getIntent().getExtras();
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        this.pref = getSharedPreferences(PREFER_NAME, 0);
        if (bundle != null) {
            this.NAME = bundle.getString("name");
            this.MOBILE = bundle.getString("mobile");
            this.MAIL = bundle.getString("mail");
            this.IMAGE = bundle.getString("pic");
        }
        if (this.NAME.equals("null") == null) {
            this.name.setText(this.NAME);
        }
        Glide.with((FragmentActivity) this).load(this.IMAGE).listener(new RequestListener<String, GlideDrawable>() {
            public boolean onException(Exception exception, String str, Target<GlideDrawable> target, boolean z) {
                str = ProfileActivity.this;
                target = new StringBuilder();
                target.append("");
                target.append(exception.toString());
                Toast.makeText(str, target.toString(), 0).show();
                ProfileActivity.this.progress.setVisibility(8);
                return false;
            }

            public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                ProfileActivity.this.progress.setVisibility(8);
                return null;
            }
        }).into(this.mCropImageView);
        this.mail.setText(this.MAIL);
        this.mobile.setText(this.MOBILE);
        this.changeImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = ProfileActivity.this.getLayoutInflater().inflate(R.layout.alert_upload_pic, null);
                Builder builder = new Builder(ProfileActivity.this);
                builder.setView(view);
                builder.setCancelable(true);
                ProfileActivity.this.image = (CropImageView) view.findViewById(R.id.CropImageView);
                Button button = (Button) view.findViewById(R.id.loadImage);
                Button button2 = (Button) view.findViewById(R.id.doneCrop);
                try {
                    ProfileActivity.this.image.setImageBitmap(((BitmapDrawable) ProfileActivity.this.mCropImageView.getDrawable().getCurrent()).getBitmap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ProfileActivity.this.startActivityForResult(ProfileActivity.this.getPickImageChooserIntent(), 200);
                    }
                });
                final AlertDialog show = builder.show();
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        view = ProfileActivity.this.image.getCroppedImage(640, 640);
                        if (view != null) {
                            view = Bitmap.createScaledBitmap(view, 256, (int) (((double) view.getHeight()) * (256.0d / ((double) view.getWidth()))), false);
                            ProfileActivity.this.mCropImageView.setImageBitmap(view);
                            show.dismiss();
                            ProfileActivity.this.progressUpload.show();
                            ProfileActivity.this.networkActionImage(view);
                        }
                    }
                });
            }
        });
        this.doneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ProfileActivity.this.mail.getText().toString().equals("") != null) {
                    ProfileActivity.this.mail.setError("This field cannot be empty");
                } else if (ProfileActivity.this.name.getText().toString().trim().equals("") != null) {
                    ProfileActivity.this.name.setError("This field cannot be empty");
                } else if (ProfileActivity.this.mobile.getText().toString().trim().equals("") != null) {
                    ProfileActivity.this.mobile.setError("This field cannot be empty");
                } else if (ProfileActivity.this.mobile.getText().toString().length() < 10) {
                    ProfileActivity.this.mobile.setError("Invalid Mobile number");
                } else {
                    view = new String[]{ProfileActivity.this.name.getText().toString().trim(), ProfileActivity.this.mobile.getText().toString().trim()};
                    ProfileActivity.this.progressUpload.show();
                    ProfileActivity.this.networkAction(view);
                }
            }
        });
        this.changePassword.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = ProfileActivity.this.getLayoutInflater().inflate(R.layout.alert_change_password, null);
                Builder builder = new Builder(ProfileActivity.this);
                builder.setView(view);
                builder.setCancelable(false);
                final EditText editText = (EditText) view.findViewById(R.id.currentPassword);
                final EditText editText2 = (EditText) view.findViewById(R.id.newPassword);
                final EditText editText3 = (EditText) view.findViewById(R.id.reNewPassword);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.showPassword);
                ProfileActivity.this.progressLayout = (LinearLayout) view.findViewById(R.id.progressLayout);
                Button button = (Button) view.findViewById(R.id.cancelButton);
                Button button2 = (Button) view.findViewById(R.id.doneButton);
                final AlertDialog show = builder.show();
                checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            ProfileActivity.this.showPasswordEditText(editText);
                            ProfileActivity.this.showPasswordEditText(editText2);
                            ProfileActivity.this.showPasswordEditText(editText3);
                            return;
                        }
                        ProfileActivity.this.hidePasswordEditText(editText);
                        ProfileActivity.this.hidePasswordEditText(editText2);
                        ProfileActivity.this.hidePasswordEditText(editText3);
                        editText.setTransformationMethod(new PasswordTransformationMethod());
                    }
                });
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        show.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().isEmpty() != null) {
                            editText.setError("This field cannot be blank");
                        } else if (editText2.getText().toString().isEmpty() != null) {
                            editText2.setError("This field cannot be blank");
                        } else if (editText3.getText().toString().isEmpty() != null) {
                            editText3.setError("This field cannot be blank");
                        } else {
                            view = ProfileActivity.this.sdb.rawQuery("select password, mail from user", null);
                            if (view.moveToFirst()) {
                                String string;
                                String string2;
                                do {
                                    string = view.getString(0);
                                    string2 = view.getString(1);
                                } while (view.moveToNext());
                                if (editText.getText().toString().trim().equals(string) == null) {
                                    editText.setError("Incorrect Password");
                                } else if (editText2.getText().toString().trim().equals(editText3.getText().toString().trim()) != null) {
                                    try {
                                        ProfileActivity.this.progressLayout.setVisibility(0);
                                        ProfileActivity.this.networkAction(string2, editText2.getText().toString().trim(), show);
                                    } catch (View view2) {
                                        view2.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Password Do not match", 1).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    private void networkActionImage(Bitmap bitmap) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.MAIL);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        final String[] strArr2 = new String[1];
        final Bitmap bitmap2 = bitmap;
        Volley.newRequestQueue(this).add(new VolleyMultipartRequest(1, updateUrl, new Listener<NetworkResponse>() {
            public void onResponse(NetworkResponse networkResponse) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(networkResponse.data));
                    iArr[0] = jSONObject.getInt("success");
                    strArr[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    strArr2[0] = jSONObject.getString("image");
                    if (iArr[0] == 1) {
                        ProfileActivity.this.progressUpload.hide();
                        ProfileActivity.this.updateDB(strArr2[0]);
                    } else if (iArr[0] == null) {
                        ProfileActivity.this.progressUpload.hide();
                        Toast.makeText(ProfileActivity.this, strArr[0], 0).show();
                    }
                } catch (NetworkResponse networkResponse2) {
                    ProfileActivity.this.progressUpload.hide();
                    networkResponse2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ProfileActivity.this.progressUpload.hide();
                Toast.makeText(ProfileActivity.this.getApplicationContext(), volleyError.getMessage(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }

            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> hashMap = new HashMap();
                long currentTimeMillis = System.currentTimeMillis();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(currentTimeMillis);
                stringBuilder.append(".png");
                hashMap.put("pic", new DataPart(stringBuilder.toString(), ProfileActivity.this.getFileDataFromDrawable(bitmap2)));
                return hashMap;
            }
        });
    }

    private void hidePasswordEditText(EditText editText) {
        editText.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void showPasswordEditText(EditText editText) {
        editText.setTransformationMethod(null);
    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        bitmap.compress(CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        return Uri.parse(Media.insertImage(context.getContentResolver(), bitmap, "Title", null));
    }

    public String compressImage(String str) {
        Bitmap decodeFile;
        str = getRealPathFromURI(str);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap decodeFile2 = BitmapFactory.decodeFile(str, options);
        int i = options.outHeight;
        int i2 = options.outWidth;
        float f = (float) (i2 / i);
        float f2 = (float) i;
        if (f2 > 816.0f || ((float) i2) > 612.0f) {
            if (f < 0.75f) {
                i2 = (int) ((816.0f / f2) * ((float) i2));
                i = (int) 1145831424;
            } else if (f > 0.75f) {
                i = (int) ((612.0f / ((float) i2)) * f2);
                i2 = (int) 1142489088;
            } else {
                i = (int) 1145831424;
                i2 = (int) 1142489088;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, i2, i);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16384];
        try {
            decodeFile = BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            decodeFile = decodeFile2;
        }
        try {
            decodeFile2 = Bitmap.createBitmap(i2, i, Config.ARGB_8888);
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            decodeFile2 = null;
        }
        float f3 = (float) i2;
        f2 = f3 / ((float) options.outWidth);
        float f4 = (float) i;
        float f5 = f4 / ((float) options.outHeight);
        f3 /= 2.0f;
        f4 /= 2.0f;
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f5, f3, f4);
        Canvas canvas = new Canvas(decodeFile2);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(decodeFile, f3 - ((float) (decodeFile.getWidth() / 2)), f4 - ((float) (decodeFile.getHeight() / 2)), new Paint(2));
        try {
            str = new ExifInterface(str).getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exif: ");
            stringBuilder.append(str);
            Log.d("EXIF", stringBuilder.toString());
            Matrix matrix2 = new Matrix();
            if (str == 6) {
                matrix2.postRotate(90.0f);
                stringBuilder = new StringBuilder();
                stringBuilder.append("Exif: ");
                stringBuilder.append(str);
                Log.d("EXIF", stringBuilder.toString());
            } else if (str == 3) {
                matrix2.postRotate(180.0f);
                stringBuilder = new StringBuilder();
                stringBuilder.append("Exif: ");
                stringBuilder.append(str);
                Log.d("EXIF", stringBuilder.toString());
            } else if (str == 8) {
                matrix2.postRotate(270.0f);
                stringBuilder = new StringBuilder();
                stringBuilder.append("Exif: ");
                stringBuilder.append(str);
                Log.d("EXIF", stringBuilder.toString());
            }
            str = Bitmap.createBitmap(decodeFile2, 0, 0, decodeFile2.getWidth(), decodeFile2.getHeight(), matrix2, true);
        } catch (String str2) {
            str2.printStackTrace();
            str2 = decodeFile2;
        }
        String filename = getFilename();
        try {
            str2.compress(CompressFormat.JPEG, 80, new FileOutputStream(filename));
        } catch (String str22) {
            str22.printStackTrace();
        }
        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getAbsolutePath());
        stringBuilder.append("/");
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append(".jpg");
        return stringBuilder.toString();
    }

    private String getRealPathFromURI(String str) {
        str = Uri.parse(str);
        Cursor query = getContentResolver().query(str, null, null, null, null);
        if (query == null) {
            return str.getPath();
        }
        query.moveToFirst();
        return query.getString(query.getColumnIndex("_data"));
    }

    public int calculateInSampleSize(Options options, int i, int i2) {
        int i3;
        int i4 = options.outHeight;
        options = options.outWidth;
        if (i4 <= i2) {
            if (options <= i) {
                i3 = 1;
                while (((float) (options * i4)) / ((float) (i3 * i3)) > ((float) ((i * i2) * 2))) {
                    i3++;
                }
                return i3;
            }
        }
        i3 = Math.round(((float) i4) / ((float) i2));
        int round = Math.round(((float) options) / ((float) i));
        if (i3 >= round) {
            i3 = round;
        }
        while (((float) (options * i4)) / ((float) (i3 * i3)) > ((float) ((i * i2) * 2))) {
            i3++;
        }
        return i3;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void updateDB(String[] strArr) {
        try {
            this.sdb = this.db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", strArr[0]);
            contentValues.put("mobile", strArr[1]);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mail='");
            stringBuilder.append(this.MAIL);
            stringBuilder.append("'");
            this.sdb.update("user", contentValues, stringBuilder.toString(), null);
            Toast.makeText(this, "Profile Updated", 0).show();
            onBackPressed();
        } catch (String[] strArr2) {
            strArr2.printStackTrace();
        }
    }

    private void updateDB(String str) {
        try {
            this.sdb = this.db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("image", str);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mail='");
            stringBuilder.append(this.MAIL);
            stringBuilder.append("'");
            this.sdb.update("user", contentValues, stringBuilder.toString(), null);
            Toast.makeText(this, "Profile pic Updated", 0).show();
        } catch (String str2) {
            str2.printStackTrace();
        }
    }

    private void updateDB(String str, String str2, AlertDialog alertDialog) {
        try {
            this.sdb = this.db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("password", str);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mail='");
            stringBuilder.append(str2);
            stringBuilder.append("'");
            this.sdb.update("user", contentValues, stringBuilder.toString(), null);
            str2 = this.pref.edit();
            str2.putString("password", str);
            str2.apply();
            str2.commit();
            alertDialog.dismiss();
        } catch (String str3) {
            str3.printStackTrace();
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            i = getPickImageResultUri(intent);
            int i3 = 1;
            if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 || isUriRequiresPermissions(i) == 0) {
                i3 = 0;
            } else {
                this.mCropImageUri = i;
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
            }
            if (i3 == 0) {
                this.image.setImageUriAsync(i);
            }
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            new Builder(this).setTitle((CharSequence) "Storage Permission").setMessage((CharSequence) "Grant permission to the app to access device storage").setPositiveButton((CharSequence) "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 99);
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 99);
        }
        return false;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (this.mCropImageUri == 0 || iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this, "Required permissions are not granted", 1).show();
        } else {
            this.image.setImageUriAsync(this.mCropImageUri);
        }
    }

    public Intent getPickImageChooserIntent() {
        Parcelable captureImageOutputUri = getCaptureImageOutputUri();
        List<Intent> arrayList = new ArrayList();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            intent2.setPackage(resolveInfo.activityInfo.packageName);
            if (captureImageOutputUri != null) {
                intent2.putExtra("output", captureImageOutputUri);
            }
            arrayList.add(intent2);
        }
        Intent intent3 = new Intent("android.intent.action.GET_CONTENT");
        intent3.setType("image/*");
        for (ResolveInfo resolveInfo2 : packageManager.queryIntentActivities(intent3, 0)) {
            Intent intent4 = new Intent(intent3);
            intent4.setComponent(new ComponentName(resolveInfo2.activityInfo.packageName, resolveInfo2.activityInfo.name));
            intent4.setPackage(resolveInfo2.activityInfo.packageName);
            arrayList.add(intent4);
        }
        intent3 = (Intent) arrayList.get(arrayList.size() - 1);
        for (Intent intent5 : arrayList) {
            if (intent5.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                intent3 = intent5;
                break;
            }
        }
        arrayList.remove(intent3);
        intent3 = Intent.createChooser(intent3, "Select source");
        intent3.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[arrayList.size()]));
        return intent3;
    }

    private Uri getCaptureImageOutputUri() {
        File externalCacheDir = getExternalCacheDir();
        return externalCacheDir != null ? Uri.fromFile(new File(externalCacheDir.getPath(), "pickImageResult.jpeg")) : null;
    }

    public Uri getPickImageResultUri(Intent intent) {
        Object obj = 1;
        if (!(intent == null || intent.getData() == null)) {
            String action = intent.getAction();
            if (action == null || !action.equals("android.media.action.IMAGE_CAPTURE")) {
                obj = null;
            }
        }
        return obj != null ? getCaptureImageOutputUri() : intent.getData();
    }

    public boolean isUriRequiresPermissions(android.net.Uri r3) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r2 = this;
        r0 = 0;
        r1 = r2.getContentResolver();	 Catch:{ FileNotFoundException -> 0x000d, Exception -> 0x0018 }
        r3 = r1.openInputStream(r3);	 Catch:{ FileNotFoundException -> 0x000d, Exception -> 0x0018 }
        r3.close();	 Catch:{ FileNotFoundException -> 0x000d, Exception -> 0x0018 }
        return r0;
    L_0x000d:
        r3 = move-exception;
        r3 = r3.getCause();
        r3 = r3 instanceof android.system.ErrnoException;
        if (r3 == 0) goto L_0x0018;
    L_0x0016:
        r3 = 1;
        return r3;
    L_0x0018:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.activity.ProfileActivity.isUriRequiresPermissions(android.net.Uri):boolean");
    }

    private void initialiseViews() {
        this.changeImage = (LinearLayout) findViewById(R.id.changeImage);
        this.mCropImageView = (ImageView) findViewById(R.id.profileImage);
        this.doneButton = (CardView) findViewById(R.id.doneButton);
        this.name = (EditText) findViewById(R.id.profileName);
        this.mail = (EditText) findViewById(R.id.profileEmail);
        this.mobile = (EditText) findViewById(R.id.profileMobile);
        this.progress = (ProgressBar) findViewById(R.id.progress);
        this.progressUpload = (AVLoadingIndicatorView) findViewById(R.id.progressUpload);
        this.changePassword = (TextView) findViewById(R.id.profileChangePassword);
        this.progressUpload.hide();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void networkAction(String str, String str2, AlertDialog alertDialog) throws JSONException {
        final ArrayList arrayList = new ArrayList();
        String str3 = str;
        arrayList.add(str3);
        String str4 = str2;
        arrayList.add(str4);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("password");
        final int[] iArr = new int[1];
        final String[] strArr = new String[1];
        final String str5 = str4;
        final String str6 = str3;
        final AlertDialog alertDialog2 = alertDialog;
        final ArrayList arrayList3 = arrayList2;
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, updateUrl, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    iArr[0] = jSONObject.getInt("success");
                    strArr[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (iArr[0] == 1) {
                        ProfileActivity.this.progressLayout.setVisibility(8);
                        Toast.makeText(ProfileActivity.this, "Password Updated", 0).show();
                        ProfileActivity.this.updateDB(str5, str6, alertDialog2);
                    } else if (iArr[0] == null) {
                        Toast.makeText(ProfileActivity.this, strArr[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProfileActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList3.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "Password");
    }

    private void networkAction(final String[] strArr) {
        final ArrayList arrayList = new ArrayList();
        for (Object add : strArr) {
            arrayList.add(add);
        }
        arrayList.add(this.MAIL);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("name");
        arrayList2.add("mobile");
        arrayList2.add("email");
        final int[] iArr = new int[1];
        final String[] strArr2 = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, updateUrl, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    iArr[0] = jSONObject.getInt("success");
                    strArr2[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (iArr[0] == 1) {
                        ProfileActivity.this.progressUpload.hide();
                        Toast.makeText(ProfileActivity.this, "User data Updated", 0).show();
                        ProfileActivity.this.updateDB(strArr);
                    } else if (iArr[0] == null) {
                        ProfileActivity.this.progressUpload.hide();
                        Toast.makeText(ProfileActivity.this, strArr2[0], 0).show();
                    }
                } catch (String str2) {
                    ProfileActivity.this.progressUpload.hide();
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProfileActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "User details");
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
