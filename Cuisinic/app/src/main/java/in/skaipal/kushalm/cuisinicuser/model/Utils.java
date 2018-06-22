package in.skaipal.kushalm.cuisinicuser.model;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class Utils {
    private static final String TAG = "Utils";

    public static List<Image> loadImages(Context context) {
        try {
            Gson create = new GsonBuilder().create();
            JSONArray jSONArray = new JSONArray(loadJSONFromAsset(context, "images.json"));
            context = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                context.add((Image) create.fromJson(jSONArray.getString(i), Image.class));
            }
            return context;
        } catch (Context context2) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("seedGames parseException ");
            stringBuilder.append(context2);
            Log.d(str, stringBuilder.toString());
            context2.printStackTrace();
            return null;
        }
    }

    private static String loadJSONFromAsset(Context context, String str) {
        try {
            context = context.getAssets();
            String str2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("path ");
            stringBuilder.append(str);
            Log.d(str2, stringBuilder.toString());
            context = context.open(str);
            str = new byte[context.available()];
            context.read(str);
            context.close();
            return new String(str, "UTF-8");
        } catch (Context context2) {
            context2.printStackTrace();
            return null;
        }
    }
}
