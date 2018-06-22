package in.skaipal.kushalm.cuisinicuser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.AddAddressActivity;
import in.skaipal.kushalm.cuisinicuser.model.addressModel;
import java.util.ArrayList;

public class AddressAdapter extends Adapter<MyViewHolder> {
    private ArrayList<addressModel> address;
    private Context mContext;

    public class MyViewHolder extends ViewHolder {
        TextView address;
        LinearLayout layout;
        TextView mobile;
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.addressName);
            this.mobile = (TextView) view.findViewById(R.id.addressMobile);
            this.address = (TextView) view.findViewById(R.id.addressMain);
            this.layout = (LinearLayout) view.findViewById(R.id.layout);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_recycler_child, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(((addressModel) this.address.get(i)).getName());
        myViewHolder.mobile.setText(((addressModel) this.address.get(i)).getMobile());
        myViewHolder.address.setText(((addressModel) this.address.get(i)).getAddress());
        myViewHolder.layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Activity activity = (Activity) AddressAdapter.this.mContext;
                Intent intent = new Intent(AddressAdapter.this.mContext, AddAddressActivity.class);
                intent.putExtra("do", "edit");
                intent.putExtra("name", myViewHolder.name.getText().toString().trim());
                intent.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                intent.putExtra("address", myViewHolder.address.getText().toString().trim());
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    public int getItemCount() {
        return this.address.size();
    }

    public AddressAdapter(Context context, ArrayList<addressModel> arrayList) {
        this.address = arrayList;
        this.mContext = context;
    }
}
