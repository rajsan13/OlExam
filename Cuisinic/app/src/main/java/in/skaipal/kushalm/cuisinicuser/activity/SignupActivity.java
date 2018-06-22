package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    private static String signupUrl = APIUrls.signupUrl;
    EditText Email;
    EditText Mobile;
    EditText Name;
    EditText Password;
    EditText RePassword;
    TextView SignIn;
    Button SignUp;
    FirebaseAuth auth;
    SharedPreferences pref;
    LinearLayout progress;
    FirebaseUser user;

    @RequiresApi(api = 21)
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT >= 19) {
            getWindow().setStatusBarColor(ViewCompat.MEASURED_STATE_MASK);
        }
        setContentView((int) R.layout.activity_signup);
        this.auth = FirebaseAuth.getInstance();
        this.pref = getSharedPreferences("Valid", 0);
        initialiseViews();
        this.SignUp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    SignupActivity.this.getEditTextData(SignupActivity.this.Name, SignupActivity.this.Mobile, SignupActivity.this.Password, SignupActivity.this.RePassword, SignupActivity.this.Email);
                } catch (View view2) {
                    view2.printStackTrace();
                }
            }
        });
        this.SignIn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void getEditTextData(EditText editText, EditText editText2, EditText editText3, EditText editText4, EditText editText5) throws JSONException {
        final String trim = editText.getText().toString().trim();
        final String trim2 = editText3.getText().toString().trim();
        String trim3 = editText4.getText().toString().trim();
        final String trim4 = editText2.getText().toString().trim();
        final String trim5 = editText5.getText().toString().trim();
        if (trim.equals("")) {
            editText.setError("This field cannot be left blank");
        } else if (trim5.equals("") != null) {
            editText5.setError("This field cannot be left blank");
        } else if (trim4.equals("") != null) {
            editText2.setError("This field cannot be left blank");
        } else if (trim2.equals("") != null) {
            editText3.setError("This field cannot be left blank");
        } else if (trim3.equals("") != null) {
            editText4.setError("This field cannot be left blank");
        } else if (isValidEmail(trim5) == null) {
            editText5.setError("Invalid Email id");
        } else if (trim4.length() < 10) {
            editText2.setError("Invalid mobile number");
        } else if (trim2.equals(trim3) != null) {
            this.progress.setVisibility(0);
            this.auth.createUserWithEmailAndPassword(trim5, trim2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        SignupActivity.this.networkAction(trim5, trim4, trim2, trim);
                    } catch (Task<AuthResult> task2) {
                        task2.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Password do not match", 0).show();
        }
    }

    private void networkAction(String str, String str2, String str3, String str4) throws JSONException {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        arrayList.add(str4);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("mobile");
        arrayList2.add("password");
        arrayList2.add("name");
        str2 = new int[1];
        str = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, signupUrl, new Listener<String>() {
            public void onResponse(String str) {
                SignupActivity.this.progress.setVisibility(8);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    str2[0] = jSONObject.getInt("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (str2[0] == 1) {
                        Toast.makeText(SignupActivity.this, "User successfully registered, please verify your account by clicking on the link present in the verification mail ", 1).show();
                        SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    } else if (str2[0] == null) {
                        Toast.makeText(SignupActivity.this, str[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                SignupActivity.this.progress.setVisibility(8);
                Toast.makeText(SignupActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "Signup");
    }

    private boolean isValidEmail(String str) {
        return str == null ? null : Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    private void initialiseViews() {
        this.Mobile = (EditText) findViewById(R.id.SignMobile);
        this.Name = (EditText) findViewById(R.id.SignName);
        this.Email = (EditText) findViewById(R.id.SignEmail);
        this.Password = (EditText) findViewById(R.id.SignPassword);
        this.RePassword = (EditText) findViewById(R.id.SignRePassword);
        this.SignUp = (Button) findViewById(R.id.SignUpMain);
        this.SignIn = (TextView) findViewById(R.id.SignupLogin);
        this.progress = (LinearLayout) findViewById(R.id.progress_layout);
    }
}
