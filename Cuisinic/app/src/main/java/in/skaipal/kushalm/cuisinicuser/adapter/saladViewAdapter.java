package in.skaipal.kushalm.cuisinicuser.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.activity.MakeSaladActivity;
import in.skaipal.kushalm.cuisinicuser.database.CartDatabase;
import in.skaipal.kushalm.cuisinicuser.model.saladIndividualModel;
import in.skaipal.kushalm.cuisinicuser.model.saladModel;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;

public class saladViewAdapter extends Adapter<MyViewHolder> {
    public static final String KEY_MAIL = "mail";
    private static final String PREFER_NAME = "SessionSharedPreference";
    private String Email;
    CartDatabase db;
    private Context mContext;
    private ArrayList<saladModel> salad;
    SQLiteDatabase sdb;
    private SharedPreferences sp;

    public class MyViewHolder extends ViewHolder {
        ImageView add;
        public EditText quantity;
        TextView removeSalad;
        TextView saladName;
        TextView saladPrice;
        RecyclerView saladRecycler;
        TextView showItem;
        ImageView sub;

        public MyViewHolder(View view) {
            super(view);
            this.saladName = (TextView) view.findViewById(R.id.saladName);
            this.saladPrice = (TextView) view.findViewById(R.id.saladPrice);
            this.showItem = (TextView) view.findViewById(R.id.showItem);
            this.saladRecycler = (RecyclerView) view.findViewById(R.id.saladContentRecycler);
            this.sub = (ImageView) view.findViewById(R.id.subs1);
            this.add = (ImageView) view.findViewById(R.id.add1);
            this.removeSalad = (TextView) view.findViewById(R.id.removeSalad);
            saladViewAdapter.this.db = new CartDatabase(saladViewAdapter.this.mContext);
            saladViewAdapter.this.sdb = saladViewAdapter.this.db.getWritableDatabase();
            this.quantity = (EditText) view.findViewById(R.id.quantity1);
        }
    }

    public saladViewAdapter(Context context, ArrayList<saladModel> arrayList) {
        this.mContext = context;
        this.salad = arrayList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewGroup = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_salad, viewGroup, false);
        this.sp = this.mContext.getSharedPreferences(PREFER_NAME, 0);
        this.Email = this.sp.getString("mail", null);
        return new MyViewHolder(viewGroup);
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        try {
            myViewHolder.saladName.setText(((saladModel) this.salad.get(i)).getName());
            TextView textView = myViewHolder.saladPrice;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mContext.getResources().getString(R.string.Rupee));
            stringBuilder.append(" ");
            stringBuilder.append(((saladModel) this.salad.get(i)).getPrice());
            textView.setText(stringBuilder.toString());
            myViewHolder.quantity.setText(((saladModel) this.salad.get(i)).getQuantity());
            JSONArray jSONArray = null;
            try {
                jSONArray = new JSONArray(((saladModel) this.salad.get(i)).getItems().replace(" _", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                try {
                    String[] split = jSONArray.getString(i2).split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                    arrayList.add(split[0]);
                    arrayList2.add(split[1]);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            myViewHolder.saladRecycler.setLayoutManager(new LinearLayoutManager(this.mContext));
            ArrayList arrayList3 = new ArrayList(new HashSet(arrayList));
            Collections.reverse(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                ArrayList arrayList5 = new ArrayList();
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    if (((String) arrayList3.get(i3)).equalsIgnoreCase((String) arrayList.get(i4))) {
                        arrayList5.add(arrayList2.get(i4));
                    }
                }
                arrayList4.add(arrayList5);
            }
            myViewHolder.saladRecycler.setAdapter(new saladViewItemAdapter(this.mContext, getDataset(arrayList3, arrayList4)));
            myViewHolder.showItem.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (myViewHolder.saladRecycler.getVisibility() == null) {
                        myViewHolder.saladRecycler.setVisibility(8);
                    } else if (myViewHolder.saladRecycler.getVisibility() == 8) {
                        myViewHolder.saladRecycler.setVisibility(0);
                    }
                }
            });
            myViewHolder.removeSalad.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        view = saladViewAdapter.this.sdb;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Delete from salad where name ='");
                        stringBuilder.append(((saladModel) saladViewAdapter.this.salad.get(i)).getName());
                        stringBuilder.append("'");
                        view.execSQL(stringBuilder.toString());
                        view = saladViewAdapter.this.sdb;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Delete from cart where item_name ='");
                        stringBuilder.append(((saladModel) saladViewAdapter.this.salad.get(i)).getName());
                        stringBuilder.append("' and rest_name ='");
                        stringBuilder.append(saladViewAdapter.this.Email);
                        stringBuilder.append("'");
                        view.execSQL(stringBuilder.toString());
                    } catch (View view2) {
                        view2.printStackTrace();
                    }
                    MakeSaladActivity.adapter1 = new saladViewAdapter(saladViewAdapter.this.mContext, MakeSaladActivity.getDataSet());
                    MakeSaladActivity.recycler.setAdapter(MakeSaladActivity.adapter1);
                    saladViewAdapter.this.notifyDataSetChanged();
                }
            });
            myViewHolder.add.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view = Integer.parseInt(myViewHolder.quantity.getText().toString().trim()) + 1;
                    EditText editText = myViewHolder.quantity;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    editText.setText(stringBuilder.toString());
                    saladModel saladmodel = (saladModel) saladViewAdapter.this.salad.get(i);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    saladmodel.setQuantity(stringBuilder.toString());
                    view = Integer.valueOf(Integer.parseInt(((saladModel) saladViewAdapter.this.salad.get(i)).getPrice().trim()) * view);
                    TextView textView = myViewHolder.saladPrice;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(saladViewAdapter.this.mContext.getResources().getString(R.string.Rupee));
                    stringBuilder.append(" ");
                    stringBuilder.append(view);
                    textView.setText(stringBuilder.toString());
                }
            });
            myViewHolder.sub.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view = myViewHolder.quantity.getText().toString().trim();
                    if (Integer.parseInt(view) != 1) {
                        view = Integer.parseInt(view) - 1;
                        EditText editText = myViewHolder.quantity;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(view);
                        editText.setText(stringBuilder.toString());
                        saladModel saladmodel = (saladModel) saladViewAdapter.this.salad.get(i);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(view);
                        saladmodel.setQuantity(stringBuilder.toString());
                        TextView textView = myViewHolder.saladPrice;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(saladViewAdapter.this.mContext.getResources().getString(R.string.Rupee));
                        stringBuilder.append(" ");
                        stringBuilder.append(Integer.parseInt(((saladModel) saladViewAdapter.this.salad.get(i)).getPrice().trim()) * view);
                        textView.setText(stringBuilder.toString());
                        return;
                    }
                    Toast.makeText(saladViewAdapter.this.mContext, "Minimum quantity is 1", 0).show();
                }
            });
        } catch (MyViewHolder myViewHolder2) {
            myViewHolder2.printStackTrace();
            Toast.makeText(this.mContext, "No items Added", 0).show();
        }
    }

    public ArrayList<saladIndividualModel> getDataset(ArrayList<String> arrayList, ArrayList<ArrayList<String>> arrayList2) {
        ArrayList<saladIndividualModel> arrayList3 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList3.add(new saladIndividualModel((String) arrayList.get(i), (ArrayList) arrayList2.get(i)));
        }
        return arrayList3;
    }

    public int getItemCount() {
        return this.salad.size();
    }

    public ArrayList<saladModel> getAll() {
        return this.salad;
    }
}
