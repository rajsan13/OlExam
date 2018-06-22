package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static String loginUrl = APIUrls.loginUrl;
    EditText Code;
    EditText Email;
    EditText Password;
    EditText RePassword;
    TextView Resend;
    Button ResetButton;
    AVLoadingIndicatorView progress;

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable Bundle bundle) {
        if (VERSION.SDK_INT >= 19) {
            getWindow().setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        }
        super.onCreate(bundle);
        setContentView((int) R.layout.forgot_password_layout);
        init_views();
        this.ResetButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ForgotPasswordActivity.this.resetButtonFunction(ForgotPasswordActivity.this.ResetButton.getText().toString().trim().toLowerCase());
            }
        });
    }

    private void resetButtonFunction(String str) {
        int hashCode = str.hashCode();
        if (hashCode != -891535336) {
            if (hashCode != -819951495) {
                if (hashCode == 108404047) {
                    if (str.equals("reset") != null) {
                        str = 2;
                        switch (str) {
                            case null:
                                str = this.Email.getText().toString().trim();
                                if (!TextUtils.isEmpty(str)) {
                                    this.Email.setError("This field cannot be left blank");
                                    return;
                                } else if (isValidEmail(str)) {
                                    this.Email.setError("Invalid Email id");
                                    return;
                                } else {
                                    sendVerificationCode(str);
                                    return;
                                }
                            case 1:
                                return;
                            default:
                                return;
                        }
                    }
                }
            } else if (str.equals("verify") != null) {
                str = true;
                switch (str) {
                    case null:
                        str = this.Email.getText().toString().trim();
                        if (!TextUtils.isEmpty(str)) {
                            this.Email.setError("This field cannot be left blank");
                            return;
                        } else if (isValidEmail(str)) {
                            sendVerificationCode(str);
                            return;
                        } else {
                            this.Email.setError("Invalid Email id");
                            return;
                        }
                    case 1:
                        return;
                    default:
                        return;
                }
            }
        } else if (str.equals("submit") != null) {
            str = null;
            switch (str) {
                case null:
                    str = this.Email.getText().toString().trim();
                    if (!TextUtils.isEmpty(str)) {
                        this.Email.setError("This field cannot be left blank");
                        return;
                    } else if (isValidEmail(str)) {
                        this.Email.setError("Invalid Email id");
                        return;
                    } else {
                        sendVerificationCode(str);
                        return;
                    }
                case 1:
                    return;
                default:
                    return;
            }
        }
        str = -1;
        switch (str) {
            case null:
                str = this.Email.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    this.Email.setError("This field cannot be left blank");
                    return;
                } else if (isValidEmail(str)) {
                    sendVerificationCode(str);
                    return;
                } else {
                    this.Email.setError("Invalid Email id");
                    return;
                }
            case 1:
                return;
            default:
                return;
        }
    }

    private void sendVerificationCode(final String str) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add("1");
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("reset");
        final String[] strArr = new String[1];
        final String[] strArr2 = new String[1];
        final View findViewById = findViewById(16908290);
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, loginUrl, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    strArr[0] = jSONObject.getString("success");
                    strArr2[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (strArr[0].equals("1") != null) {
                        Toast.makeText(ForgotPasswordActivity.this, strArr2[0], 1).show();
                        ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        ForgotPasswordActivity.this.finish();
                    } else if (strArr[0].equals("0") != null) {
                        ForgotPasswordActivity.this.progress.hide();
                        Snackbar.make(findViewById, strArr2[0], 0).setAction((CharSequence) "SIGN UP", new OnClickListener() {
                            public void onClick(View view) {
                                ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, SignupActivity.class));
                                ForgotPasswordActivity.this.finish();
                            }
                        }).show();
                    }
                } catch (String str2) {
                    ForgotPasswordActivity.this.progress.hide();
                    str2.printStackTrace();
                    Toast.makeText(ForgotPasswordActivity.this, str2.toString(), 0).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                ForgotPasswordActivity.this.progress.hide();
                if ((volleyError instanceof NoConnectionError) != null) {
                    Snackbar.make(findViewById, (CharSequence) "Connection Error", 0).setAction((CharSequence) "RETRY", new OnClickListener() {
                        public void onClick(View view) {
                            ForgotPasswordActivity.this.sendVerificationCode(str);
                        }
                    }).show();
                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "reset");
    }

    private void init_views() {
        this.Email = (EditText) findViewById(R.id.forgot_email);
        this.Code = (EditText) findViewById(R.id.forgot_verification);
        this.Password = (EditText) findViewById(R.id.forgot_password);
        this.RePassword = (EditText) findViewById(R.id.forgot_re_password);
        this.ResetButton = (Button) findViewById(R.id.reset_password);
        this.Resend = (TextView) findViewById(R.id.resent_verification);
        this.Code.setVisibility(8);
        this.Password.setVisibility(8);
        this.RePassword.setVisibility(8);
        this.Resend.setVisibility(8);
        this.ResetButton.setText("submit");
        this.progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        this.progress.hide();
    }

    private boolean isValidEmail(String str) {
        return str == null ? null : Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }
}
