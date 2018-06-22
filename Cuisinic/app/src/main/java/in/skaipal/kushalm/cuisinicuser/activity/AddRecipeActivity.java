package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class AddRecipeActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    EditText Description;
    EditText Details;
    private String Email;
    EditText Name;
    EditText Price;
    Button Share;
    LinearLayout progress;
    RadioGroup rg;
    private SharedPreferences sp;
    String url = APIUrls.shareRecipeUrl;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.sp = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        initaliseViews();
        this.Share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String trim = AddRecipeActivity.this.Name.getText().toString().trim();
                String trim2 = AddRecipeActivity.this.Price.getText().toString().trim();
                String trim3 = AddRecipeActivity.this.Description.getText().toString().trim();
                String trim4 = AddRecipeActivity.this.Details.getText().toString().trim();
                switch (AddRecipeActivity.this.rg.getCheckedRadioButtonId()) {
                    case R.id.typeNonVeg:
                        view = "non-veg";
                        break;
                    case R.id.typeVeg:
                        view = "veg";
                        break;
                    default:
                        view = null;
                        break;
                }
                View view2 = view;
                if (trim.isEmpty() != null) {
                    AddRecipeActivity.this.Name.setError("Enter recipe name");
                } else if (trim2.isEmpty() != null) {
                    AddRecipeActivity.this.Price.setError("Enter recipe price");
                } else if (trim3.isEmpty() != null) {
                    AddRecipeActivity.this.Description.setError("Enter recipe description");
                } else if (trim4.isEmpty() != null) {
                    AddRecipeActivity.this.Details.setError("Enter recipe details");
                } else {
                    AddRecipeActivity.this.networkAction(trim, trim2, trim3, trim4, view2);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    private void networkAction(String str, String str2, String str3, String str4, String str5) {
        makeProgressVisible();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.Email);
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        arrayList.add(str4);
        arrayList.add(str5);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("name");
        arrayList2.add("price");
        arrayList2.add("description");
        arrayList2.add("details");
        arrayList2.add(UserSessionManager.KEY_TYPE);
        str2 = new int[1];
        str = new String[1];
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    System.out.println(str);
                    JSONObject jSONObject = new JSONObject(str);
                    str2[0] = jSONObject.getInt("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (str2[0] == 1) {
                        AddRecipeActivity.this.makeProgressInvisible();
                        Toast.makeText(AddRecipeActivity.this, str[0], 0).show();
                        AddRecipeActivity.this.startActivity(new Intent(AddRecipeActivity.this, CuisinicMenuActivity.class));
                    } else if (str2[0] == null) {
                        AddRecipeActivity.this.makeProgressInvisible();
                        Toast.makeText(AddRecipeActivity.this, str[0], 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                AddRecipeActivity.this.makeProgressInvisible();
                Toast.makeText(AddRecipeActivity.this, volleyError.toString(), 0).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList2.get(i), arrayList.get(i));
                }
                return hashMap;
            }
        }, "Add recipe");
    }

    private void initaliseViews() {
        this.Name = (EditText) findViewById(R.id.recipeName);
        this.Price = (EditText) findViewById(R.id.recipePrice);
        this.Description = (EditText) findViewById(R.id.recipeDescription);
        this.Details = (EditText) findViewById(R.id.recipeDetails);
        this.rg = (RadioGroup) findViewById(R.id.rg);
        this.Share = (Button) findViewById(R.id.buttonShare);
        this.progress = (LinearLayout) findViewById(R.id.progress);
    }

    public void makeProgressVisible() {
        this.progress.setVisibility(0);
        this.Share.setVisibility(8);
    }

    public void makeProgressInvisible() {
        this.progress.setVisibility(8);
        this.Share.setVisibility(0);
    }
}
