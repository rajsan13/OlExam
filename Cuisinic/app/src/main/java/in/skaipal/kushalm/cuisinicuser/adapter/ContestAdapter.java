package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.Volley.AppSingleton;
import in.skaipal.kushalm.cuisinicuser.activity.CuisinicMenuActivity;
import in.skaipal.kushalm.cuisinicuser.model.APIUrls;
import in.skaipal.kushalm.cuisinicuser.model.ContestOther;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ContestAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    private ArrayList<ContestOther> contest;
    private Context mContext;
    private SharedPreferences sp;
    String url = APIUrls.applyContestUrl;

    public class MyViewHolder extends ViewHolder {
        public Button apply;
        public TextView date;
        public TextView description;
        public TextView one;
        public LinearLayout progress;
        public TextView three;
        public TextView two;

        public MyViewHolder(View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.contestDate);
            this.description = (TextView) view.findViewById(R.id.description);
            this.one = (TextView) view.findViewById(R.id.one);
            this.two = (TextView) view.findViewById(R.id.two);
            this.three = (TextView) view.findViewById(R.id.three);
            this.apply = (Button) view.findViewById(R.id.contestApply);
            this.progress = (LinearLayout) view.findViewById(R.id.progress);
            ContestAdapter.this.sp = ContestAdapter.this.mContext.getSharedPreferences(ContestAdapter.PREFER_NAME, 0);
            ContestAdapter.this.Email = ContestAdapter.this.sp.getString("mail", null);
        }
    }

    public ContestAdapter(Context context, ArrayList<ContestOther> arrayList) {
        this.mContext = context;
        this.contest = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_contest_layout, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        myViewHolder.date.setText(((ContestOther) this.contest.get(i)).getContestDate());
        myViewHolder.description.setText(((ContestOther) this.contest.get(i)).getContestDescription());
        myViewHolder.one.setText(((ContestOther) this.contest.get(i)).getContest1stPrize());
        myViewHolder.two.setText(((ContestOther) this.contest.get(i)).getContest2ndPrize());
        myViewHolder.three.setText(((ContestOther) this.contest.get(i)).getContest3rdPrize());
        i = ((ContestOther) this.contest.get(i)).getContestId();
        myViewHolder.apply.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ContestAdapter.this.networkAction(myViewHolder.progress, i);
            }
        });
    }

    private void networkAction(final LinearLayout linearLayout, String str) {
        linearLayout.setVisibility(0);
        final ArrayList arrayList = new ArrayList();
        arrayList.add(this.Email);
        arrayList.add(str);
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add("email");
        arrayList2.add("contestId");
        final String[] strArr = new String[1];
        str = new String[1];
        AppSingleton.getInstance(this.mContext).addToRequestQueue(new StringRequest(1, this.url, new Listener<String>() {
            public void onResponse(String str) {
                StringBuilder stringBuilder;
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    strArr[0] = jSONObject.getString("success");
                    str[0] = jSONObject.getString(SettingsJsonConstants.PROMPT_MESSAGE_KEY);
                    if (strArr[0].equals("1") != null) {
                        linearLayout.setVisibility(8);
                        str = ContestAdapter.this.mContext;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(str[0]);
                        Toast.makeText(str, stringBuilder.toString(), 1).show();
                        ContestAdapter.this.mContext.startActivity(new Intent(ContestAdapter.this.mContext, CuisinicMenuActivity.class));
                        return;
                    }
                    linearLayout.setVisibility(8);
                    Toast.makeText(ContestAdapter.this.mContext, str[0], 0).show();
                } catch (String str2) {
                    str2.printStackTrace();
                    linearLayout.setVisibility(8);
                    Context access$100 = ContestAdapter.this.mContext;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(str2.toString());
                    Toast.makeText(access$100, stringBuilder.toString(), 0).show();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if ((volleyError instanceof NoConnectionError) != null) {
                    Toast.makeText(ContestAdapter.this.mContext, "Network Connection Error", 0).show();
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
        }, "Apply Contest");
    }

    public int getItemCount() {
        return this.contest.size();
    }
}
