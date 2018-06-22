package in.skaipal.kushalm.cuisinicuser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.service.MyService;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CuisinicMenuActivity extends AppCompatActivity {
    public static final String KEY_MAIL = "mail";
    private static final String PREFERENCE = "service";
    private static final String PREFER_NAME = "SessionSharedPreference";
    public static MenuItem menuItem;
    String Email = null;
    CardView appetizerCard;
    CardView beveragesCard;
    CardView breakfastCard;
    CardView burgerCard;
    CardView chineseCard;
    TextView contactUs;
    CartDatabase db;
    String device = null;
    boolean doubleBackToExitPressedOnce = false;
    CardView grilledCard;
    CardView juiceCard;
    CardView makeSadal;
    CardView mealsCard;
    private Menu menu;
    CardView omeletteCard;
    CardView pastaCard;
    String refreshedToken = null;
    CardView saladCard;
    CardView sandwichCard;
    SQLiteDatabase sdb;
    CardView sharedCard;
    CardView soupCard;
    SharedPreferences sp;
    SharedPreferences sp1;
    SharedPreferences sp2;
    CardView toastCard;
    int total = 0;
    String url = APIUrls.fcmUrl;
    CardView waffleCard;
    CardView wrapCard;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cuisinic_menu);
        this.sp = getSharedPreferences("service", 0);
        if (this.sp.getBoolean("serviceRunning", true) != null) {
            startService();
        }
        initializeViews();
        this.sp2 = getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp2.getString("mail", null);
        this.db = new CartDatabase(this);
        this.sdb = this.db.getWritableDatabase();
        updateFabAndCart();
        this.mealsCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, MealsActivity.class);
                view.putExtra("category", "Meals");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.beveragesCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, BeveragesActivity.class);
                view.putExtra("category", "Beverages");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.juiceCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, JuiceActivity.class);
                view.putExtra("category", "Juice");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.breakfastCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Breakfast");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.saladCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Salad");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.sandwichCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Sandwich");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.wrapCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Wrap");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.soupCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Soup");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.appetizerCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Appetizers");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.grilledCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Grilled Continental");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.chineseCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Chinese");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.omeletteCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Omelette");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.burgerCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Burger");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.pastaCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Pasta");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.toastCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Toast");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.waffleCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, CuisinicItemActivity.class);
                view.putExtra("category", "Waffle");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.makeSadal.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CuisinicMenuActivity.this.startActivity(new Intent(CuisinicMenuActivity.this, MakeSaladActivity.class));
            }
        });
        this.sharedCard.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = new Intent(CuisinicMenuActivity.this, IndianActivity.class);
                view.putExtra("category", "Indian");
                view.putExtra("sub-category", "none");
                CuisinicMenuActivity.this.startActivity(view);
            }
        });
        this.contactUs.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CuisinicMenuActivity.this.startActivity(new Intent(CuisinicMenuActivity.this, ContactUsActivity.class));
            }
        });
        updateFCMtoServer();
    }

    private void updateFabAndCart() {
        try {
            SQLiteDatabase sQLiteDatabase = this.sdb;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT item_name FROM cart where item_quantity!=0 and rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            sQLiteDatabase.rawQuery(stringBuilder.toString(), null).getCount();
            sQLiteDatabase = this.sdb;
            stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT SUM(item_total) FROM cart where rest_name='");
            stringBuilder.append(this.Email);
            stringBuilder.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
            if (rawQuery.moveToFirst()) {
                this.total = rawQuery.getInt(0);
            }
            MenuItem menuItem = menuItem;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getString(R.string.Rupee));
            stringBuilder.append(" ");
            stringBuilder.append(this.total);
            menuItem.setTitle(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFCMtoServer() {
        this.sp1 = getSharedPreferences("fcm", 0);
        if (this.sp1.getString("success", "false").equalsIgnoreCase("true")) {
            this.refreshedToken = this.sp1.getString("id", null);
        } else {
            this.refreshedToken = FirebaseInstanceId.getInstance().getToken();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.BRAND);
        stringBuilder.append(" ");
        stringBuilder.append(Build.DEVICE);
        this.device = stringBuilder.toString();
        startFCMnetworkAction(this.Email, this.refreshedToken, this.device, "user");
    }

    private void startFCMnetworkAction(String str, String str2, String str3, String str4) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(str3);
        arrayList.add(str4);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("fcm");
        arrayList2.add("device");
        arrayList2.add("user");
        str2 = new String[1];
        str = new String[1];
        str3 = findViewById(16908290);
        AppSingleton.getInstance(this).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    str2[0] = jSONObject.getString("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (str2[0].equals("1") != null) {
                        Toast.makeText(CuisinicMenuActivity.this, "Notification Registration Successfull", 0).show();
                    } else if (str2[0].equals("0") != null) {
                        Toast.makeText(CuisinicMenuActivity.this, "Error registering for Notification", 0).show();
                    }
                } catch (String str2) {
                    str2.printStackTrace();
                    Context context = CuisinicMenuActivity.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error registering for Notification");
                    stringBuilder.append(str2.toString());
                    Toast.makeText(context, stringBuilder.toString(), 0).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if ((volleyError instanceof NoConnectionError) != null) {
                    Snackbar.make(str3, (CharSequence) "Connection Error", 0).setAction((CharSequence) "RETRY", new OnClickListener() {
                        public void onClick(View view) {
                            CuisinicMenuActivity.this.startFCMnetworkAction(CuisinicMenuActivity.this.Email, CuisinicMenuActivity.this.refreshedToken, CuisinicMenuActivity.this.device, "admin");
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
        }, "fcm Register");
    }

    public void startService() {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    private void initializeViews() {
        this.mealsCard = (CardView) findViewById(R.id.mealsCard);
        this.beveragesCard = (CardView) findViewById(R.id.beveragesCard);
        this.juiceCard = (CardView) findViewById(R.id.juiceCard);
        this.breakfastCard = (CardView) findViewById(R.id.breakfastCard);
        this.saladCard = (CardView) findViewById(R.id.saladCard);
        this.sandwichCard = (CardView) findViewById(R.id.sandwichCard);
        this.wrapCard = (CardView) findViewById(R.id.wrapCard);
        this.soupCard = (CardView) findViewById(R.id.soupCard);
        this.appetizerCard = (CardView) findViewById(R.id.appetizersCard);
        this.grilledCard = (CardView) findViewById(R.id.grilledContinentalCard);
        this.chineseCard = (CardView) findViewById(R.id.chineseCard);
        this.omeletteCard = (CardView) findViewById(R.id.omletteCard);
        this.burgerCard = (CardView) findViewById(R.id.burgerCard);
        this.pastaCard = (CardView) findViewById(R.id.pastaCard);
        this.toastCard = (CardView) findViewById(R.id.toastCard);
        this.waffleCard = (CardView) findViewById(R.id.waffleCard);
        this.makeSadal = (CardView) findViewById(R.id.makeSaladCard);
        this.sharedCard = (CardView) findViewById(R.id.sharedCard);
        this.contactUs = (TextView) findViewById(R.id.contact_us);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        menuItem = menu.findItem(R.id.menuCart);
        MenuItem menuItem = menuItem;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(this.total);
        menuItem.setTitle(stringBuilder.toString());
        this.menu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.menuCart) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (itemId != R.id.settings) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            startActivity(new Intent(this, DrawerActivity.class));
            finish();
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", 0).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                CuisinicMenuActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void onResume() {
        super.onResume();
        updateFabAndCart();
    }
}
