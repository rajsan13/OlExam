package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.DeliveryActivity;
import in.skaipal.kushalm.cuisinicuser.model.addressModel;
import java.util.ArrayList;

public class DeliveryAddressSelectAdapter extends Adapter<MyViewHolder> {
    private ArrayList<addressModel> address;
    private Context mContext;
    int row = -1;

    public class MyViewHolder extends ViewHolder {
        TextView address;
        CardView card;
        LinearLayout layout;
        TextView mobile;
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.addressName);
            this.mobile = (TextView) view.findViewById(R.id.addressMobile);
            this.address = (TextView) view.findViewById(R.id.addressMain);
            this.layout = (LinearLayout) view.findViewById(R.id.layout);
            this.card = (CardView) view.findViewById(R.id.card);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_recycler_child, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(((addressModel) this.address.get(i)).getName());
        myViewHolder.mobile.setText(((addressModel) this.address.get(i)).getMobile());
        myViewHolder.address.setText(((addressModel) this.address.get(i)).getAddress());
        Drawable gradientDrawable = new GradientDrawable();
        Drawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable.setColor(-1);
        gradientDrawable.setStroke(3, ViewCompat.MEASURED_STATE_MASK);
        gradientDrawable2.setColor(-1);
        gradientDrawable2.setStroke(3, -1);
        if (this.row == i) {
            if (VERSION.SDK_INT < 16) {
                myViewHolder.card.setBackgroundDrawable(gradientDrawable);
            } else {
                myViewHolder.card.setBackground(gradientDrawable);
            }
        } else if (VERSION.SDK_INT < 16) {
            myViewHolder.card.setBackgroundDrawable(gradientDrawable2);
        } else {
            myViewHolder.card.setBackground(gradientDrawable2);
        }
        myViewHolder.layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DeliveryAddressSelectAdapter.this.row = i;
                DeliveryAddressSelectAdapter.this.notifyDataSetChanged();
                DeliveryActivity.selectName = myViewHolder.name.getText().toString().trim();
                DeliveryActivity.selectMobile = myViewHolder.mobile.getText().toString().trim();
                DeliveryActivity.selectAddress = myViewHolder.address.getText().toString().trim();
            }
        });
    }

    public int getItemCount() {
        return this.address.size();
    }

    public DeliveryAddressSelectAdapter(Context context, ArrayList<addressModel> arrayList) {
        this.address = arrayList;
        this.mContext = context;
    }
}
