package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.media.ExifInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static String loginUrl = APIUrls.loginUrl;
    TextView ForgotPassword;
    EditText Password;
    Button SignIn;
    TextView SignUp;
    EditText UserName;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    AVLoadingIndicatorView progress;
    UserSessionManager session;
    FirebaseUser user = this.auth.getCurrentUser();

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.session = new UserSessionManager(this);
        setContentView((int) R.layout.activity_login);
        this.UserName = (EditText) findViewById(R.id.LoginUserName);
        this.Password = (EditText) findViewById(R.id.LoginPassword);
        this.ForgotPassword = (TextView) findViewById(R.id.LoginForgotPassword);
        this.SignUp = (TextView) findViewById(R.id.LoginSignUp);
        this.SignIn = (Button) findViewById(R.id.LoginSignIn);
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        this.progress.hide();
        this.SignIn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.getEditTextData(LoginActivity.this.UserName, LoginActivity.this.Password);
            }
        });
        this.ForgotPassword.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        this.SignUp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void getEditTextData(EditText editText, EditText editText2) {
        String trim = editText.getText().toString().trim();
        String trim2 = editText2.getText().toString().trim();
        if (!(trim.equals("") || trim.isEmpty())) {
            if (!trim.equals(null)) {
                if (trim2.equals("") != null) {
                    editText2.setError("This field cannot be empty");
                    return;
                }
                hideSoftKeyboard();
                this.progress.show();
                networkAction(trim, trim2);
                return;
            }
        }
        editText.setError("This field cannot be empty");
    }

    private void networkAction(String str, String str2) {
        final String str3 = str;
        final String str4 = str2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(str3);
        arrayList.add(str4);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("password");
        final String[] strArr = new String[1];
        final String[] strArr2 = new String[1];
        final View findViewById = findViewById(16908290);
        String str5 = loginUrl;
        final String str6 = str3;
        final String str7 = str4;
        final View view = findViewById;
        AnonymousClass4 anonymousClass4 = new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    strArr[0] = jSONObject.getString("success");
                    strArr2[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (strArr[0].equals("1") != null) {
                        str = jSONObject.getJSONArray("product").getJSONObject(0);
                        LoginActivity.this.progress.hide();
                        LoginActivity.this.session.createUserLoginSession(str6, str7, str);
                        Toast.makeText(LoginActivity.this, "Successfully LoggedIN", 0).show();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, CuisinicMenuActivity.class));
                        LoginActivity.this.finish();
                    } else if (strArr[0].equals("0") != null) {
                        LoginActivity.this.progress.hide();
                        Snackbar.make(view, strArr2[0], 0).setAction((CharSequence) "SIGN UP", new OnClickListener() {
                            public void onClick(View view) {
                                LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                                LoginActivity.this.finish();
                            }
                        }).show();
                    } else if (strArr[0].equals(ExifInterface.GPS_MEASUREMENT_2D) != null) {
                        LoginActivity.this.progress.hide();
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", 0).show();
                    } else if (strArr[0].equals(ExifInterface.GPS_MEASUREMENT_3D) != null) {
                        LoginActivity.this.progress.hide();
                        Toast.makeText(LoginActivity.this, strArr2[0], 1).show();
                    }
                } catch (String str2) {
                    LoginActivity.this.progress.hide();
                    str2.printStackTrace();
                    Toast.makeText(LoginActivity.this, str2.toString(), 0).show();
                }
            }
        };
        ErrorListener anonymousClass5 = new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                LoginActivity.this.progress.hide();
                if ((volleyError instanceof NoConnectionError) != null) {
                    Snackbar.make(findViewById, (CharSequence) "Connection Error", 0).setAction((CharSequence) "RETRY", new OnClickListener() {
                        public void onClick(View view) {
                            LoginActivity.this.networkAction(str3, str4);
                        }
                    }).show();
                }
            }
        };
        final ArrayList arrayList3 = arrayList;
        final ArrayList arrayList4 = arrayList2;
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, str5, anonymousClass4, anonymousClass5) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList3.size(); i++) {
                    hashMap.put(arrayList4.get(i), arrayList3.get(i));
                }
                return hashMap;
            }
        }, "Login");
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
