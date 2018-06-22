package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.CuisinicItemActivity;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.StarterOther;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;

public class StarterOtherAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String CATEGORY;
    private String Email;
    private String SUB_CATEGORY;
    private CartDatabase db;
    private ArrayList<String> itemList = new ArrayList();
    private ArrayList<StarterOther> itemName;
    private Context mContext;
    private ArrayList<String> priceList = new ArrayList();
    private ArrayList<String> qntyList = new ArrayList();
    private SQLiteDatabase sdb;
    private SharedPreferences sp;

    public class MyViewHolder extends ViewHolder {
        public Button AddtoCart;
        public ImageView add;
        public LinearLayout countLayout;
        public TextView item;
        public LinearLayout itemLayout;
        public TextView itemPrice;
        public EditText quantity;
        public ImageView subs;
        public ImageView type;
        public View view1;

        public MyViewHolder(View view) {
            super(view);
            this.item = (TextView) view.findViewById(R.id.itemName);
            this.itemPrice = (TextView) view.findViewById(R.id.itemPrice);
            this.itemLayout = (LinearLayout) view.findViewById(R.id.ItemDetails);
            this.countLayout = (LinearLayout) view.findViewById(R.id.countLayout);
            this.quantity = (EditText) view.findViewById(R.id.quantity);
            this.add = (ImageView) view.findViewById(R.id.add);
            this.subs = (ImageView) view.findViewById(R.id.subs);
            this.AddtoCart = (Button) view.findViewById(R.id.addToCart);
            this.type = (ImageView) view.findViewById(R.id.itemType);
            this.view1 = view.findViewById(R.id.view);
            StarterOtherAdapter.this.db = new CartDatabase(StarterOtherAdapter.this.mContext);
            StarterOtherAdapter.this.sdb = StarterOtherAdapter.this.db.getWritableDatabase();
            StarterOtherAdapter.this.sp = StarterOtherAdapter.this.mContext.getSharedPreferences(StarterOtherAdapter.PREFER_NAME, 0);
            StarterOtherAdapter.this.Email = StarterOtherAdapter.this.sp.getString("mail", null);
            try {
                view = StarterOtherAdapter.this.sdb;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("select item_name , item_quantity, item_price from cart where rest_name ='");
                stringBuilder.append(StarterOtherAdapter.this.Email);
                stringBuilder.append("' and item_category ='");
                stringBuilder.append(StarterOtherAdapter.this.CATEGORY);
                stringBuilder.append("' and item_sub_category ='");
                stringBuilder.append(StarterOtherAdapter.this.SUB_CATEGORY);
                stringBuilder.append("'");
                view = view.rawQuery(stringBuilder.toString(), null);
                if (view.moveToFirst()) {
                    do {
                        StarterOtherAdapter.this.itemList.add(view.getString(0).replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, " "));
                        StarterOtherAdapter.this.qntyList.add(view.getString(1));
                        StarterOtherAdapter.this.priceList.add(view.getString(2));
                    } while (view.moveToNext());
                }
            } catch (View view2) {
                Context access$100 = StarterOtherAdapter.this.mContext;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(view2.toString());
                Toast.makeText(access$100, stringBuilder2.toString(), 0).show();
                view2.printStackTrace();
                StarterOtherAdapter.this.itemList.add("null");
                StarterOtherAdapter.this.qntyList.add("0");
            }
        }
    }

    public int getItemViewType(int i) {
        return i;
    }

    public StarterOtherAdapter(Context context, String str, String str2, ArrayList<StarterOther> arrayList) {
        this.itemName = arrayList;
        this.mContext = context;
        this.CATEGORY = str;
        this.SUB_CATEGORY = str2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_child_layout, viewGroup, false));
    }

    @RequiresApi(api = 23)
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        if (i == this.itemName.size() - 1) {
            myViewHolder.view1.setVisibility(0);
        } else {
            myViewHolder.view1.setVisibility(8);
        }
        if (((StarterOther) this.itemName.get(i)).getItemType().equals("veg")) {
            myViewHolder.type.setImageDrawable(this.mContext.getDrawable(R.drawable.cuisinic_veg));
        } else if (((StarterOther) this.itemName.get(i)).getItemType().equals("non-veg")) {
            myViewHolder.type.setImageDrawable(this.mContext.getDrawable(R.drawable.cuisinic_non_veg));
        }
        myViewHolder.item.setText(((StarterOther) this.itemName.get(i)).getItemName());
        int i2 = 0;
        while (i2 < this.itemList.size()) {
            if (((StarterOther) this.itemName.get(i)).getItemName().trim().equals(this.itemList.get(i2)) && ((StarterOther) this.itemName.get(i)).getItemPrice().replace(this.mContext.getResources().getString(R.string.Rupee), "").trim().equals(this.priceList.get(i2))) {
                myViewHolder.quantity.setText((CharSequence) this.qntyList.get(i2));
                myViewHolder.AddtoCart.setText("Remove");
                myViewHolder.AddtoCart.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            }
            i2++;
        }
        myViewHolder.itemPrice.setText(((StarterOther) this.itemName.get(i)).getItemPrice());
        myViewHolder.itemLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (myViewHolder.countLayout.getVisibility() == null) {
                    myViewHolder.countLayout.setVisibility(8);
                } else {
                    myViewHolder.countLayout.setVisibility(0);
                }
            }
        });
        if (myViewHolder.quantity.getText().toString().trim().equals("0") != 0) {
            myViewHolder.subs.setEnabled(false);
        } else {
            myViewHolder.subs.setEnabled(true);
        }
        myViewHolder.quantity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.equals("0") != null) {
                    myViewHolder.subs.setEnabled(0);
                } else {
                    myViewHolder.subs.setEnabled(1);
                }
            }
        });
        myViewHolder.add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                myViewHolder.AddtoCart.setText("Add");
                if (VERSION.SDK_INT >= 23) {
                    myViewHolder.AddtoCart.setBackgroundColor(StarterOtherAdapter.this.mContext.getColor(R.color.colorPrimary));
                } else {
                    myViewHolder.AddtoCart.setBackgroundColor(StarterOtherAdapter.this.mContext.getResources().getColor(R.color.colorPrimary));
                }
                view = Integer.parseInt(myViewHolder.quantity.getText().toString().trim()) + 1;
                EditText editText = myViewHolder.quantity;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(view);
                editText.setText(stringBuilder.toString());
            }
        });
        myViewHolder.subs.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = myViewHolder.quantity.getText().toString().trim();
                if (Integer.parseInt(view) != 0) {
                    view = Integer.parseInt(view) - 1;
                    EditText editText = myViewHolder.quantity;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    editText.setText(stringBuilder.toString());
                    return;
                }
                myViewHolder.subs.setEnabled(false);
            }
        });
        myViewHolder.AddtoCart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int parseInt;
                view = myViewHolder.item.getText().toString().trim();
                String trim = myViewHolder.quantity.getText().toString().trim();
                String replace = myViewHolder.itemPrice.getText().toString().trim().replace("₹ ", "");
                try {
                    parseInt = Integer.parseInt(trim) * Integer.parseInt(replace);
                } catch (Exception e) {
                    e.printStackTrace();
                    parseInt = 0;
                }
                view = view.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                try {
                    StarterOtherAdapter.this.open();
                    SQLiteDatabase access$200 = StarterOtherAdapter.this.sdb;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Select * from cart where rest_name ='");
                    stringBuilder.append(StarterOtherAdapter.this.Email);
                    stringBuilder.append("' and item_name ='");
                    stringBuilder.append(view);
                    stringBuilder.append("'and item_category ='");
                    stringBuilder.append(StarterOtherAdapter.this.CATEGORY);
                    stringBuilder.append("' and item_sub_category ='");
                    stringBuilder.append(StarterOtherAdapter.this.SUB_CATEGORY);
                    stringBuilder.append("'");
                    String str = null;
                    if (access$200.rawQuery(stringBuilder.toString(), null).getCount() > 0) {
                        access$200 = StarterOtherAdapter.this.sdb;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("select item_quantity from cart where rest_name ='");
                        stringBuilder2.append(StarterOtherAdapter.this.Email);
                        stringBuilder2.append("' and item_name ='");
                        stringBuilder2.append(view);
                        stringBuilder2.append("' and item_category ='");
                        stringBuilder2.append(StarterOtherAdapter.this.CATEGORY);
                        stringBuilder2.append("' and item_sub_category ='");
                        stringBuilder2.append(StarterOtherAdapter.this.SUB_CATEGORY);
                        stringBuilder2.append("'");
                        Cursor rawQuery = access$200.rawQuery(stringBuilder2.toString(), null);
                        if (rawQuery.moveToFirst()) {
                            do {
                                str = rawQuery.getString(0);
                            } while (rawQuery.moveToNext());
                        }
                        if (str.trim().equals(trim)) {
                            Toast.makeText(StarterOtherAdapter.this.mContext, "Item with same quantity already exists", 0).show();
                            return;
                        }
                        if (trim.trim().equals("0")) {
                            myViewHolder.AddtoCart.setText("Add");
                            if (VERSION.SDK_INT >= 23) {
                                myViewHolder.AddtoCart.setBackgroundColor(StarterOtherAdapter.this.mContext.getColor(R.color.colorPrimary));
                            } else {
                                myViewHolder.AddtoCart.setBackgroundColor(StarterOtherAdapter.this.mContext.getResources().getColor(R.color.colorPrimary));
                            }
                        } else {
                            myViewHolder.AddtoCart.setText("Remove");
                            myViewHolder.AddtoCart.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                        }
                        StarterOtherAdapter.this.updateDB(StarterOtherAdapter.this.Email, view, trim, parseInt);
                    } else if (trim.trim().equals("0")) {
                        Toast.makeText(StarterOtherAdapter.this.mContext, "Please Increase the Quantity", 0).show();
                    } else {
                        myViewHolder.AddtoCart.setText("Remove");
                        myViewHolder.AddtoCart.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                        StarterOtherAdapter.this.insertToDB(StarterOtherAdapter.this.Email, view, replace, trim, parseInt);
                    }
                } catch (NullPointerException e2) {
                    Context access$100 = StarterOtherAdapter.this.mContext;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("");
                    stringBuilder3.append(e2.toString());
                    Toast.makeText(access$100, stringBuilder3.toString(), 0).show();
                    StarterOtherAdapter.this.insertToDB(StarterOtherAdapter.this.Email, view, replace, trim, parseInt);
                }
            }
        });
    }

    private void updateDB(String str, String str2, String str3, int i) {
        String[] strArr = new String[]{str, str2, this.CATEGORY, this.SUB_CATEGORY};
        str2 = new ContentValues();
        str2.put("item_quantity", str3);
        str2.put("item_total", Integer.valueOf(i));
        this.sdb.update("cart", str2, "rest_name=? AND item_name=? AND item_category=? AND item_sub_category=?", strArr);
        updateUI(str);
        close();
    }

    private void updateUI(String str) {
        SQLiteDatabase sQLiteDatabase = this.sdb;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT item_name FROM  cart where item_quantity !=0 and rest_name ='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        CuisinicItemActivity.fab.setCount(sQLiteDatabase.rawQuery(stringBuilder.toString(), null).getCount());
        sQLiteDatabase = this.sdb;
        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT SUM(item_total) FROM cart where rest_name ='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        str = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        int i = 0;
        if (str.moveToFirst()) {
            i = str.getInt(0);
        }
        str = CuisinicItemActivity.menuItem;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.mContext.getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(i);
        str.setTitle(stringBuilder2.toString());
    }

    private void insertToDB(String str, String str2, String str3, String str4, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("rest_name", str);
        contentValues.put("item_category", this.CATEGORY);
        contentValues.put("item_sub_category", this.SUB_CATEGORY);
        contentValues.put("item_name", str2);
        contentValues.put("item_price", str3);
        contentValues.put("item_quantity", str4);
        contentValues.put("item_total", Integer.valueOf(i));
        this.sdb.insert("cart", null, contentValues);
        updateUI(str);
        close();
    }

    public void open() {
        this.sdb = this.db.getWritableDatabase();
        Log.i("databse", "Database Opened");
    }

    public void close() {
        Log.i("databse", "Database Closed");
        this.sdb.close();
    }

    public int getItemCount() {
        return this.itemName.size();
    }
}
