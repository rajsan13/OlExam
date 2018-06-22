package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.media.ExifInterface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.ShareRecipeOther;
import java.util.ArrayList;

public class ShareRecipeAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    private Context mContext;
    private ArrayList<ShareRecipeOther> recipe;
    private SharedPreferences sp;

    public class MyViewHolder extends ViewHolder {
        public TextView description;
        public TextView name;
        public TextView price;
        public TextView reason;
        public TextView status;

        public MyViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.recipeName);
            this.description = (TextView) view.findViewById(R.id.recipeDescription);
            this.price = (TextView) view.findViewById(R.id.recipePrice);
            this.status = (TextView) view.findViewById(R.id.recipeStatus);
            this.reason = (TextView) view.findViewById(R.id.recipeReason);
            ShareRecipeAdapter.this.sp = ShareRecipeAdapter.this.mContext.getSharedPreferences(ShareRecipeAdapter.PREFER_NAME, 0);
            ShareRecipeAdapter.this.Email = ShareRecipeAdapter.this.sp.getString("mail", null);
        }
    }

    public ShareRecipeAdapter(Context context, ArrayList<ShareRecipeOther> arrayList) {
        this.mContext = context;
        this.recipe = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_share_recipe, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(((ShareRecipeOther) this.recipe.get(i)).getName());
        TextView textView = myViewHolder.price;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mContext.getResources().getString(R.string.Rupee));
        stringBuilder.append(" ");
        stringBuilder.append(((ShareRecipeOther) this.recipe.get(i)).getPrice());
        textView.setText(stringBuilder.toString());
        myViewHolder.description.setText(((ShareRecipeOther) this.recipe.get(i)).getDes());
        if (((ShareRecipeOther) this.recipe.get(i)).getStatus().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            myViewHolder.status.setText("Pending");
        } else if (((ShareRecipeOther) this.recipe.get(i)).getStatus().equalsIgnoreCase("1")) {
            myViewHolder.status.setText("Accepted");
        } else if (((ShareRecipeOther) this.recipe.get(i)).getStatus().equalsIgnoreCase("0")) {
            myViewHolder.status.setText("Rejected");
            myViewHolder.reason.setVisibility(0);
        }
        if (!((ShareRecipeOther) this.recipe.get(i)).getReason().equalsIgnoreCase("nill")) {
            myViewHolder.reason.setText(((ShareRecipeOther) this.recipe.get(i)).getReason());
            myViewHolder.reason.setVisibility(0);
        }
    }

    public int getItemCount() {
        return this.recipe.size();
    }
}
