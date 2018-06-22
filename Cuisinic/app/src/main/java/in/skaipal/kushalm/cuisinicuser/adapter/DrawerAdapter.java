package in.skaipal.kushalm.cuisinicuser.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.model.drawer.DrawerItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends Adapter<ViewHolder> {
    private SparseArray<DrawerItem> holderFactories = new SparseArray();
    private List<DrawerItem> items;
    private OnItemSelectedListener listener;
    private Map<Class<? extends DrawerItem>, Integer> viewTypes = new HashMap();

    public interface OnItemSelectedListener {
        void onItemSelected(int i);
    }

    public static abstract class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        private DrawerAdapter adapter;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            this.adapter.setSelected(getAdapterPosition());
        }
    }

    public DrawerAdapter(List<DrawerItem> list) {
        this.items = list;
        processViewTypes();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewGroup = ((DrawerItem) this.holderFactories.get(i)).createViewHolder(viewGroup);
        viewGroup.adapter = this;
        return viewGroup;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((DrawerItem) this.items.get(i)).bindViewHolder(viewHolder);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public int getItemViewType(int i) {
        return ((Integer) this.viewTypes.get(((DrawerItem) this.items.get(i)).getClass())).intValue();
    }

    private void processViewTypes() {
        int i = 0;
        for (DrawerItem drawerItem : this.items) {
            if (!this.viewTypes.containsKey(drawerItem.getClass())) {
                this.viewTypes.put(drawerItem.getClass(), Integer.valueOf(i));
                this.holderFactories.put(i, drawerItem);
                i++;
            }
        }
    }

    public void setSelected(int i) {
        DrawerItem drawerItem = (DrawerItem) this.items.get(i);
        if (drawerItem.isSelectable()) {
            for (int i2 = 0; i2 < this.items.size(); i2++) {
                DrawerItem drawerItem2 = (DrawerItem) this.items.get(i2);
                if (drawerItem2.isChecked()) {
                    drawerItem2.setChecked(false);
                    notifyItemChanged(i2);
                    break;
                }
            }
            drawerItem.setChecked(true);
            notifyItemChanged(i);
            if (this.listener != null) {
                this.listener.onItemSelected(i);
            }
        }
    }

    public void setListener(OnItemSelectedListener onItemSelectedListener) {
        this.listener = onItemSelectedListener;
    }
}
