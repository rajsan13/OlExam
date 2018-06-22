package in.skaipal.kushalm.cuisinicuser.model.drawer;

import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.adapter.DrawerAdapter.ViewHolder;

public abstract class DrawerItem<T extends ViewHolder> {
    protected boolean isChecked;

    public abstract void bindViewHolder(T t);

    public abstract T createViewHolder(ViewGroup viewGroup);

    public boolean isSelectable() {
        return true;
    }

    public DrawerItem setChecked(boolean z) {
        this.isChecked = z;
        return this;
    }

    public boolean isChecked() {
        return this.isChecked;
    }
}
