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
import in.skaipal.kushalm.cuisinicuser.model.TableBooking;
import in.skaipal.kushalm.cuisinicuser.model.UserSessionManager;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import java.util.ArrayList;

public class TableBookingAdapter extends Adapter<MyViewHolder> {
    Context mContext;
    String mtype;
    ArrayList<TableBooking> table;

    public class MyViewHolder extends ViewHolder {
        TextView address;
        TextView cardHolder;
        TextView itemTotal;
        TextView members;
        TextView mobile;
        TextView name;
        TextView orderDate;
        TextView orderNo;
        TextView orderTime;
        TextView tableno;
        TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);
            this.orderNo = (TextView) view.findViewById(R.id.orderNumber);
            this.itemTotal = (TextView) view.findViewById(R.id.itemNumber);
            this.name = (TextView) view.findViewById(R.id.name);
            this.mobile = (TextView) view.findViewById(R.id.mobile);
            this.tableno = (TextView) view.findViewById(R.id.tableNumber);
            this.members = (TextView) view.findViewById(R.id.memberNumber);
            this.cardHolder = (TextView) view.findViewById(R.id.cardHolder);
            this.orderDate = (TextView) view.findViewById(R.id.bookingDate);
            this.orderTime = (TextView) view.findViewById(R.id.bookingTime);
        }
    }

    public TableBookingAdapter(ArrayList<TableBooking> arrayList, Context context, String str) {
        this.table = arrayList;
        this.mContext = context;
        this.mtype = str;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_table_booking, viewGroup, false));
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.timestamp.setText(((TableBooking) this.table.get(i)).getTimestamp());
        myViewHolder.orderNo.setText(((TableBooking) this.table.get(i)).getOrderNo());
        myViewHolder.itemTotal.setText(((TableBooking) this.table.get(i)).getTotalItem());
        myViewHolder.name.setText(((TableBooking) this.table.get(i)).getName());
        myViewHolder.mobile.setText(((TableBooking) this.table.get(i)).getMobile());
        myViewHolder.tableno.setText(((TableBooking) this.table.get(i)).getTableNo());
        myViewHolder.members.setText(((TableBooking) this.table.get(i)).getMembers());
        myViewHolder.orderTime.setText(((TableBooking) this.table.get(i)).getOrderTime());
        myViewHolder.orderDate.setText(((TableBooking) this.table.get(i)).getOrderDate());
        myViewHolder.cardHolder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                view = TableBookingAdapter.this.mtype;
                int hashCode = view.hashCode();
                if (hashCode != -1402931637) {
                    if (hashCode != -1094759602) {
                        if (hashCode != 108960) {
                            if (hashCode == 476588369) {
                                if (view.equals("cancelled") != null) {
                                    view = 3;
                                    switch (view) {
                                        case null:
                                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                            view.putExtra("members", myViewHolder.members.getText().toString());
                                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                            view.putExtra("status", "open");
                                            TableBookingAdapter.this.mContext.startActivity(view);
                                            return;
                                        case 1:
                                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                            view.putExtra("members", myViewHolder.members.getText().toString());
                                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                            view.putExtra("status", "processed");
                                            TableBookingAdapter.this.mContext.startActivity(view);
                                            return;
                                        case 2:
                                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                            view.putExtra("members", myViewHolder.members.getText().toString());
                                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                            view.putExtra("status", "completed");
                                            TableBookingAdapter.this.mContext.startActivity(view);
                                            return;
                                        case 3:
                                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                            view.putExtra("members", myViewHolder.members.getText().toString());
                                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                            view.putExtra("status", "cancelled");
                                            TableBookingAdapter.this.mContext.startActivity(view);
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            }
                        } else if (view.equals(AppSettingsData.STATUS_NEW) != null) {
                            view = null;
                            switch (view) {
                                case null:
                                    view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                    view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                    view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                    view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                    view.putExtra("members", myViewHolder.members.getText().toString());
                                    view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                    view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                    view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                    view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                    view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                    view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                    view.putExtra("status", "open");
                                    TableBookingAdapter.this.mContext.startActivity(view);
                                    return;
                                case 1:
                                    view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                    view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                    view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                    view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                    view.putExtra("members", myViewHolder.members.getText().toString());
                                    view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                    view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                    view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                    view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                    view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                    view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                    view.putExtra("status", "processed");
                                    TableBookingAdapter.this.mContext.startActivity(view);
                                    return;
                                case 2:
                                    view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                    view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                    view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                    view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                    view.putExtra("members", myViewHolder.members.getText().toString());
                                    view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                    view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                    view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                    view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                    view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                    view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                    view.putExtra("status", "completed");
                                    TableBookingAdapter.this.mContext.startActivity(view);
                                    return;
                                case 3:
                                    view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                    view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                    view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                    view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                    view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                    view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                    view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                    view.putExtra("members", myViewHolder.members.getText().toString());
                                    view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                    view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                    view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                    view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                    view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                    view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                    view.putExtra("status", "cancelled");
                                    TableBookingAdapter.this.mContext.startActivity(view);
                                    return;
                                default:
                                    return;
                            }
                        }
                    } else if (view.equals("processed") != null) {
                        view = true;
                        switch (view) {
                            case null:
                                view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                view.putExtra("members", myViewHolder.members.getText().toString());
                                view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                view.putExtra("status", "open");
                                TableBookingAdapter.this.mContext.startActivity(view);
                                return;
                            case 1:
                                view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                view.putExtra("members", myViewHolder.members.getText().toString());
                                view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                view.putExtra("status", "processed");
                                TableBookingAdapter.this.mContext.startActivity(view);
                                return;
                            case 2:
                                view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                view.putExtra("members", myViewHolder.members.getText().toString());
                                view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                view.putExtra("status", "completed");
                                TableBookingAdapter.this.mContext.startActivity(view);
                                return;
                            case 3:
                                view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                                view.putExtra(UserSessionManager.KEY_TYPE, "table");
                                view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("name", myViewHolder.name.getText().toString().trim());
                                view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                                view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                                view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                                view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                                view.putExtra("members", myViewHolder.members.getText().toString());
                                view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                                view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                                view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                                view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                                view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                                view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                                view.putExtra("status", "cancelled");
                                TableBookingAdapter.this.mContext.startActivity(view);
                                return;
                            default:
                                return;
                        }
                    }
                } else if (view.equals("completed") != null) {
                    view = 2;
                    switch (view) {
                        case null:
                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                            view.putExtra("members", myViewHolder.members.getText().toString());
                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                            view.putExtra("status", "open");
                            TableBookingAdapter.this.mContext.startActivity(view);
                            return;
                        case 1:
                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                            view.putExtra("members", myViewHolder.members.getText().toString());
                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                            view.putExtra("status", "processed");
                            TableBookingAdapter.this.mContext.startActivity(view);
                            return;
                        case 2:
                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                            view.putExtra("members", myViewHolder.members.getText().toString());
                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                            view.putExtra("status", "completed");
                            TableBookingAdapter.this.mContext.startActivity(view);
                            return;
                        case 3:
                            view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                            view.putExtra(UserSessionManager.KEY_TYPE, "table");
                            view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("name", myViewHolder.name.getText().toString().trim());
                            view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                            view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                            view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                            view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                            view.putExtra("members", myViewHolder.members.getText().toString());
                            view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                            view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                            view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                            view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                            view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                            view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                            view.putExtra("status", "cancelled");
                            TableBookingAdapter.this.mContext.startActivity(view);
                            return;
                        default:
                            return;
                    }
                }
                view = -1;
                switch (view) {
                    case null:
                        view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                        view.putExtra(UserSessionManager.KEY_TYPE, "table");
                        view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("name", myViewHolder.name.getText().toString().trim());
                        view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                        view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                        view.putExtra("members", myViewHolder.members.getText().toString());
                        view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                        view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                        view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                        view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                        view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                        view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                        view.putExtra("status", "open");
                        TableBookingAdapter.this.mContext.startActivity(view);
                        return;
                    case 1:
                        view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                        view.putExtra(UserSessionManager.KEY_TYPE, "table");
                        view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("name", myViewHolder.name.getText().toString().trim());
                        view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                        view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                        view.putExtra("members", myViewHolder.members.getText().toString());
                        view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                        view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                        view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                        view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                        view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                        view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                        view.putExtra("status", "processed");
                        TableBookingAdapter.this.mContext.startActivity(view);
                        return;
                    case 2:
                        view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                        view.putExtra(UserSessionManager.KEY_TYPE, "table");
                        view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("name", myViewHolder.name.getText().toString().trim());
                        view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                        view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                        view.putExtra("members", myViewHolder.members.getText().toString());
                        view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                        view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                        view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                        view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                        view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                        view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                        view.putExtra("status", "completed");
                        TableBookingAdapter.this.mContext.startActivity(view);
                        return;
                    case 3:
                        view = new Intent(TableBookingAdapter.this.mContext, NewOrderViewActivity.class);
                        view.putExtra(UserSessionManager.KEY_TYPE, "table");
                        view.putExtra("time", ((TableBooking) TableBookingAdapter.this.table.get(i)).getTimestamp());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("name", myViewHolder.name.getText().toString().trim());
                        view.putExtra("mobile", myViewHolder.mobile.getText().toString().trim());
                        view.putExtra("orderNo", myViewHolder.orderNo.getText().toString().trim());
                        view.putExtra("itemId", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderItems());
                        view.putExtra("tableNumber", myViewHolder.tableno.getText().toString());
                        view.putExtra("members", myViewHolder.members.getText().toString());
                        view.putExtra("orderDate", myViewHolder.orderDate.getText().toString());
                        view.putExtra("orderTime", myViewHolder.orderTime.getText().toString());
                        view.putExtra("orderPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPrice());
                        view.putExtra("orderPaidPrice", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderPaidPrice());
                        view.putExtra("orderCoupon", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderCoupon());
                        view.putExtra("orderDiscount", ((TableBooking) TableBookingAdapter.this.table.get(i)).getOrderDiscount());
                        view.putExtra("status", "cancelled");
                        TableBookingAdapter.this.mContext.startActivity(view);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public int getItemCount() {
        return this.table.size();
    }
}
