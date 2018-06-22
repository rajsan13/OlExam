package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.PurchaseHistory;
import java.util.ArrayList;

public class PurchaseHistoryAdapter extends Adapter<MyViewHolder> {
    private ArrayList<PurchaseHistory> history;
    private Context mContext;

    public class MyViewHolder extends ViewHolder {
        TextView amount;
        TextView date;
        TextView itemNumber;
        TextView restName;

        public MyViewHolder(View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.historyDate);
            this.restName = (TextView) view.findViewById(R.id.history_rest_name);
            this.amount = (TextView) view.findViewById(R.id.history_amount);
            this.itemNumber = (TextView) view.findViewById(R.id.history_number);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_history_recycler_child, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.date.setText(((PurchaseHistory) this.history.get(i)).getDate());
        TextView textView;
        StringBuilder stringBuilder;
        if (((PurchaseHistory) this.history.get(i)).getItems().equals("1")) {
            textView = myViewHolder.itemNumber;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((PurchaseHistory) this.history.get(i)).getItems());
            stringBuilder.append(" item");
            textView.setText(stringBuilder.toString());
        } else {
            textView = myViewHolder.itemNumber;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((PurchaseHistory) this.history.get(i)).getItems());
            stringBuilder.append(" items");
            textView.setText(stringBuilder.toString());
        }
        myViewHolder.restName.setText(((PurchaseHistory) this.history.get(i)).getRestName());
        myViewHolder = myViewHolder.amount;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.mContext.getResources().getString(R.string.Rupee));
        stringBuilder2.append(" ");
        stringBuilder2.append(((PurchaseHistory) this.history.get(i)).getAmount());
        myViewHolder.setText(stringBuilder2.toString());
    }

    public int getItemCount() {
        return this.history.size();
    }

    public PurchaseHistoryAdapter(Context context, ArrayList<PurchaseHistory> arrayList) {
        this.history = arrayList;
        this.mContext = context;
    }
}
