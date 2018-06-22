package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.saladIndividualModel;
import java.util.ArrayList;

public class saladViewItemAdapter extends Adapter<MyViewHolder> {
    private Context mContext;
    private ArrayList<saladIndividualModel> salad;

    public class MyViewHolder extends ViewHolder {
        TextView categoryName;
        LinearLayout saladLayout;

        public MyViewHolder(View view) {
            super(view);
            this.categoryName = (TextView) view.findViewById(R.id.saladCategoryName);
            this.saladLayout = (LinearLayout) view.findViewById(R.id.saladItemLayout);
        }
    }

    public saladViewItemAdapter(Context context, ArrayList<saladIndividualModel> arrayList) {
        this.mContext = context;
        this.salad = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salad_item_layout, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        TextView textView = myViewHolder.categoryName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((saladIndividualModel) this.salad.get(i)).getCategory().replace("-", " "));
        stringBuilder.append(" :");
        textView.setText(stringBuilder.toString());
        TextView[] textViewArr = new TextView[((saladIndividualModel) this.salad.get(i)).getItems().size()];
        for (int i2 = 0; i2 < ((saladIndividualModel) this.salad.get(i)).getItems().size(); i2++) {
            textViewArr[i2] = new TextView(this.mContext);
            textViewArr[i2].setText(((String) ((saladIndividualModel) this.salad.get(i)).getItems().get(i2)).replace("-", " "));
            myViewHolder.saladLayout.addView(textViewArr[i2]);
        }
    }

    public int getItemCount() {
        return this.salad.size();
    }
}
