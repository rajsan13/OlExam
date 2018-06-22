package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.OffersSelectActivity;
import in.skaipal.kushalm.cuisinicuser.model.OffersModel;
import java.util.ArrayList;

public class OfferSelectedAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    private ArrayList<Boolean> Selected;
    private ArrayList<OffersModel> contest;
    private Context mContext;
    boolean select;
    private SharedPreferences sp;

    public class MyViewHolder extends ViewHolder {
        public TextView desc;
        public TextView name;
        public TextView o_desc;
        public CheckBox selectCheckBox;
        public TextView valid;

        public MyViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.offer_name);
            this.valid = (TextView) view.findViewById(R.id.offer_valid);
            this.desc = (TextView) view.findViewById(R.id.description);
            this.o_desc = (TextView) view.findViewById(R.id.offer_description);
            OfferSelectedAdapter.this.sp = OfferSelectedAdapter.this.mContext.getSharedPreferences(OfferSelectedAdapter.PREFER_NAME, 0);
            OfferSelectedAdapter.this.Email = OfferSelectedAdapter.this.sp.getString("mail", null);
            this.selectCheckBox = (CheckBox) view.findViewById(R.id.offerSelected);
            this.selectCheckBox.setVisibility(8);
        }
    }

    public OfferSelectedAdapter(Context context, ArrayList<OffersModel> arrayList, boolean z) {
        this.mContext = context;
        this.contest = arrayList;
        this.select = z;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_offer, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        if (this.select) {
            myViewHolder.selectCheckBox.setVisibility(0);
        }
        myViewHolder.name.setText(((OffersModel) this.contest.get(i)).getName());
        TextView textView = myViewHolder.valid;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valid Upto: ");
        stringBuilder.append(((OffersModel) this.contest.get(i)).getValid_upto());
        textView.setText(stringBuilder.toString());
        if (((OffersModel) this.contest.get(i)).getType().equalsIgnoreCase("₹")) {
            textView = myViewHolder.desc;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((OffersModel) this.contest.get(i)).getType());
            stringBuilder.append("");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getValue());
            stringBuilder.append(" off on orders placed ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getCondition());
            stringBuilder.append(" ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getTotal());
            textView.setText(stringBuilder.toString());
            textView = myViewHolder.o_desc;
            stringBuilder = new StringBuilder();
            stringBuilder.append("End Time: ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getDescription());
            textView.setText(stringBuilder.toString());
        } else if (((OffersModel) this.contest.get(i)).getType().equalsIgnoreCase("%")) {
            textView = myViewHolder.desc;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((OffersModel) this.contest.get(i)).getValue());
            stringBuilder.append("");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getType());
            stringBuilder.append(" off on orders placed ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getCondition());
            stringBuilder.append(" ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getTotal());
            textView.setText(stringBuilder.toString());
            textView = myViewHolder.o_desc;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Description: ");
            stringBuilder.append(((OffersModel) this.contest.get(i)).getDescription());
            textView.setText(stringBuilder.toString());
        }
        myViewHolder.selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                for (int i = 0; i < OffersSelectActivity.selected.size(); i++) {
                    OffersSelectActivity.selected.set(i, Boolean.valueOf(false));
                }
                if (z) {
                    OffersSelectActivity.selected.set(i, Boolean.valueOf(true));
                }
            }
        });
    }

    public int getItemCount() {
        return this.contest.size();
    }
}
