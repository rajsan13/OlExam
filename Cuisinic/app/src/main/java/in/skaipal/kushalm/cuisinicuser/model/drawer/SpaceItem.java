package in.skaipal.kushalm.cuisinicuser.model.drawer;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class SpaceItem extends DrawerItem<ViewHolder> {
    private int spaceDp;

    static class ViewHolder extends in.skaipal.kushalm.cuisinicuser.adapter.DrawerAdapter.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public void bindViewHolder(ViewHolder viewHolder) {
    }

    public boolean isSelectable() {
        return false;
    }

    public SpaceItem(int i) {
        this.spaceDp = i;
    }

    public ViewHolder createViewHolder(ViewGroup viewGroup) {
        viewGroup = viewGroup.getContext();
        View view = new View(viewGroup);
        view.setLayoutParams(new LayoutParams(-1, (int) (viewGroup.getResources().getDisplayMetrics().density * ((float) this.spaceDp))));
        return new ViewHolder(view);
    }
}
