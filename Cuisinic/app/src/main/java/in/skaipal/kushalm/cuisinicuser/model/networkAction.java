package in.skaipal.kushalm.cuisinicuser.model;

import android.content.Context;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class networkAction {
    private static String signupUrl = "http://ikvox.esy.es/skaipal/cuisinic/set_user.php";

    public static JSONObject signupAction(Context context, ArrayList<String> arrayList) throws JSONException {
        try {
            return new JSONObject(network(context, signupUrl, arrayList));
        } catch (Context context2) {
            context2.printStackTrace();
            return null;
        }
    }

    private static String network(Context context, String str, ArrayList<String> arrayList) {
        return new String[1][null];
    }
}
