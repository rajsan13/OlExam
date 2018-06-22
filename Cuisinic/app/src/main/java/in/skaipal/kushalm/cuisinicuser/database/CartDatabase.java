package in.skaipal.kushalm.cuisinicuser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cartValue";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_COUPON = "coupon";
    private static final String KEY_DISCOUNT_PRICE = "order_type";
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM_CATEGORY = "item_category";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_SUB_CATEGORY = "item_sub_category";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORDER_TYPE = "order_type";
    private static final String KEY_PAYMENT_MODE = "payment_mode";
    private static final String KEY_PRICE = "item_price";
    private static final String KEY_QUANTITY = "item_quantity";
    private static final String KEY_REST_NAME = "rest_name";
    private static final String KEY_SALAD_ITEM = "item";
    private static final String KEY_SALAD_NAME = "name";
    private static final String KEY_SALAD_PRICE = "price";
    private static final String KEY_TOTAL = "item_total";
    private static final String KEY_USER_IMAGE = "image";
    private static final String KEY_USER_MAIL = "mail";
    private static final String KEY_USER_MOBILE = "mobile";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_TYPE = "type";
    private static final String TABLE_ADDRESS = "address";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_NEW_CART = "newCart";
    private static final String TABLE_ORDER = "order";
    private static final String TABLE_SALAD = "salad";
    private static final String TABLE_USER = "user";

    public CartDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE cart(rest_name TEXT,item_category TEXT,item_sub_category TEXT,item_name TEXT,item_price TEXT,item_quantity TEXT,item_total TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE address(name TEXT,mobile TEXT,address TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE user(name TEXT,mobile TEXT,mail TEXT,password TEXT,type TEXT,image TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE salad(name TEXT,item TEXT,price TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE newCart(item_id TEXT,item_category TEXT,item_sub_category TEXT,item_name TEXT,item_quantity TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS address");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS salad");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS newCart");
        onCreate(sQLiteDatabase);
    }
}
