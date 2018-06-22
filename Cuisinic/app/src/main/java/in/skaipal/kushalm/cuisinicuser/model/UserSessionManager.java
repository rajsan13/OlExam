package in.skaipal.kushalm.cuisinicuser.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import in.skaipal.kushalm.cuisinicuser.activity.WelcomeActivity;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import java.util.HashMap;
import org.json.JSONObject;

public class UserSessionManager {
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TYPE = "type";
    private static final String PREFER_NAME = "SessionSharedPreference";
    int PRIVATE_MODE = 0;
    Context _context;
    CartDatabase db;
    Editor editor;
    SharedPreferences pref;
    SQLiteDatabase sdb;

    public UserSessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREFER_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createUserLoginSession(String str, String str2, JSONObject jSONObject) {
        this.editor.putBoolean(IS_USER_LOGIN, true);
        this.editor.putString("mail", str);
        this.editor.putString("password", str2);
        this.editor.commit();
        try {
            this.db = new CartDatabase(this._context);
            this.sdb = this.db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("mail", str);
            contentValues.put("password", str2);
            contentValues.put("name", jSONObject.getString("name"));
            contentValues.put("mobile", jSONObject.getString("mobile"));
            contentValues.put(KEY_TYPE, jSONObject.getString(KEY_TYPE));
            contentValues.put("image", jSONObject.getString("image"));
            this.sdb.insert("user", null, contentValues);
        } catch (String str3) {
            str3.printStackTrace();
        }
    }

    public boolean checkLogin() {
        if (isUserLoggedIn()) {
            return false;
        }
        Intent intent = new Intent(this._context, WelcomeActivity.class);
        intent.addFlags(67108864);
        intent.setFlags(ErrorDialogData.BINDER_CRASH);
        this._context.startActivity(intent);
        return true;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("mail", this.pref.getString("mail", null));
        hashMap.put("password", this.pref.getString("password", null));
        return hashMap;
    }

    public void logoutUser() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r6 = this;
        r0 = r6.editor;
        r0.clear();
        r0 = r6.editor;
        r0.commit();
        r0 = new in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
        r1 = r6._context;
        r0.<init>(r1);
        r6.db = r0;
        r0 = r6.db;
        r0 = r0.getWritableDatabase();
        r6.sdb = r0;
        r0 = r6.sdb;
        r1 = "Delete from user";
        r0.execSQL(r1);
        r0 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r1 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r2 = 67108864; // 0x4000000 float:1.5046328E-36 double:3.31561842E-316;
        r3 = new android.content.Intent;	 Catch:{ Exception -> 0x0041 }
        r4 = r6._context;	 Catch:{ Exception -> 0x0041 }
        r5 = in.skaipal.kushalm.cuisinicuser.activity.WelcomeActivity.class;	 Catch:{ Exception -> 0x0041 }
        r3.<init>(r4, r5);	 Catch:{ Exception -> 0x0041 }
        r3.addFlags(r2);	 Catch:{ Exception -> 0x0041 }
        r3.setFlags(r1);	 Catch:{ Exception -> 0x0041 }
        r3.setFlags(r0);	 Catch:{ Exception -> 0x0041 }
        r4 = r6._context;	 Catch:{ Exception -> 0x0041 }
        r4.startActivity(r3);	 Catch:{ Exception -> 0x0041 }
        goto L_0x0058;
    L_0x0041:
        r3 = new android.content.Intent;
        r4 = r6._context;
        r5 = in.skaipal.kushalm.cuisinicuser.activity.WelcomeActivity.class;
        r3.<init>(r4, r5);
        r3.addFlags(r2);
        r3.setFlags(r1);
        r3.setFlags(r0);
        r0 = r6._context;
        r0.startActivity(r3);
    L_0x0058:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.skaipal.kushalm.cuisinicuser.model.UserSessionManager.logoutUser():void");
    }

    public boolean isUserLoggedIn() {
        return this.pref.getBoolean(IS_USER_LOGIN, false);
    }
}
