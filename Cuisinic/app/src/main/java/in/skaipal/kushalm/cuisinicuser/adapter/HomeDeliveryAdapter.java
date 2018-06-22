package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.NewOrderViewActivity;
import in.skaipal.kushalm.cuisinicuser.model.HomeDelivery;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import java.util.ArrayList;

public class HomeDeliveryAdapter extends Adapter<MyViewHolder> {
    ArrayList<HomeDelivery> home;
    Context mContext;
    String mtype;

    public class MyViewHolder extends ViewHolder {
        TextView address;
        TextView cardHolder;
        TextView itemTotal;
        TextView mobile;
        TextView name;
        TextView orderNo;
        TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);
            this.orderNo = (TextView) view.findViewById(R.id.orderNumber);
            this.itemTotal = (TextView) view.findViewById(R.id.itemNumber);
            this.name = (TextView) view.findViewById(R.id.name);
            this.mobile = (TextView) view.findViewById(R.id.mobile);
            this.address = (TextView) view.findViewById(R.id.address);
            this.cardHolder = (TextView) view.findViewById(R.id.cardHolder);
        }
    }

    public HomeDeliveryAdapter(ArrayList<HomeDelivery> arrayList, Context context, String str) {
        this.home = arrayList;
        this.mContext = context;
        this.mtype = str;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_home_delivery, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.timestamp.setText(((HomeDelivery) this.home.get(i)).getTimestamp());
        myViewHolder.orderNo.setText(((HomeDelivery) this.home.get(i)).getOrderNo());
        myViewHolder.itemTotal.setText(((HomeDelivery) this.home.get(i)).getTotalItem());
        myViewHolder.name.setText(((HomeDelivery) this.home.get(i)).getName());
        myViewHolder.mobile.setText(((HomeDelivery) this.home.get(i)).getMobile());
        myViewHolder.address.setText(((HomeDelivery) this.home.get(i)).getAddress());
        myViewHolder.cardHolder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (HomeDeliveryAdapter.this.mtype.equals(AppSettingsData.STATUS_NEW) != null) {
                    view = new Intent(HomeDeliveryAdapter.this.mContext, NewOrderViewActivity.class);
                    view.putExtra(UserSessionManager.KEY_TYPE, "home");
                    view.putExtra("time", myViewHolder.timestamp.getText().toString().trim());
                    view.putExtra("address", myViewHolder.address.getText().toString().trim());
                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                    view.putExtra("itemId", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderItems());
                    view.putExtra("orderPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPrice());
                    view.putExtra("orderPaidPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPaidPrice());
                    view.putExtra("orderCoupon", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderCoupon());
                    view.putExtra("orderDiscount", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderDiscount());
                    view.putExtra("status", "open");
                    HomeDeliveryAdapter.this.mContext.startActivity(view);
                } else if (HomeDeliveryAdapter.this.mtype.equals("processed") != null) {
                    view = new Intent(HomeDeliveryAdapter.this.mContext, NewOrderViewActivity.class);
                    view.putExtra(UserSessionManager.KEY_TYPE, "home");
                    view.putExtra("time", myViewHolder.timestamp.getText().toString().trim());
                    view.putExtra("address", myViewHolder.address.getText().toString().trim());
                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                    view.putExtra("itemId", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderItems());
                    view.putExtra("orderPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPrice());
                    view.putExtra("orderPaidPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPaidPrice());
                    view.putExtra("orderCoupon", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderCoupon());
                    view.putExtra("orderDiscount", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderDiscount());
                    view.putExtra("status", "processed");
                    HomeDeliveryAdapter.this.mContext.startActivity(view);
                } else if (HomeDeliveryAdapter.this.mtype.equals("completed") != null) {
                    view = new Intent(HomeDeliveryAdapter.this.mContext, NewOrderViewActivity.class);
                    view.putExtra(UserSessionManager.KEY_TYPE, "home");
                    view.putExtra("time", myViewHolder.timestamp.getText().toString().trim());
                    view.putExtra("address", myViewHolder.address.getText().toString().trim());
                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                    view.putExtra("itemId", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderItems());
                    view.putExtra("orderPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPrice());
                    view.putExtra("orderPaidPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPaidPrice());
                    view.putExtra("orderCoupon", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderCoupon());
                    view.putExtra("orderDiscount", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderDiscount());
                    view.putExtra("status", "completed");
                    HomeDeliveryAdapter.this.mContext.startActivity(view);
                } else if (HomeDeliveryAdapter.this.mtype.equals("cancelled") != null) {
                    view = new Intent(HomeDeliveryAdapter.this.mContext, NewOrderViewActivity.class);
                    view.putExtra(UserSessionManager.KEY_TYPE, "home");
                    view.putExtra("time", myViewHolder.timestamp.getText().toString().trim());
                    view.putExtra("address", myViewHolder.address.getText().toString().trim());
                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                    view.putExtra("itemId", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderItems());
                    view.putExtra("orderPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPrice());
                    view.putExtra("orderPaidPrice", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderPaidPrice());
                    view.putExtra("orderCoupon", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderCoupon());
                    view.putExtra("orderDiscount", ((HomeDelivery) HomeDeliveryAdapter.this.home.get(i)).getOrderDiscount());
                    view.putExtra("status", "cancelled");
                    HomeDeliveryAdapter.this.mContext.startActivity(view);
                }
            }
        });
    }

    public int getItemCount() {
        return this.home.size();
    }
}
