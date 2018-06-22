package in.skaipal.kushalm.cuisinicuser.model.drawer;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import in.skaipal.kushalm.cuisinicuser.R;

public class SimpleItem extends DrawerItem<ViewHolder> {
    private Drawable icon;
    private int normalItemIconTint;
    private int normalItemTextTint;
    private int selectedItemIconTint;
    private int selectedItemTextTint;
    private String title;

    static class ViewHolder extends in.skaipal.kushalm.cuisinicuser.adapter.DrawerAdapter.ViewHolder {
        private ImageView icon;
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.icon);
            this.title = (TextView) view.findViewById(R.id.title);
        }
    }

    public SimpleItem(Drawable drawable, String str) {
        this.icon = drawable;
        this.title = str;
    }

    public ViewHolder createViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_option, viewGroup, false));
    }

    public void bindViewHolder(ViewHolder viewHolder) {
        viewHolder.title.setText(this.title);
        viewHolder.icon.setImageDrawable(this.icon);
        viewHolder.title.setTextColor(this.isChecked ? this.selectedItemTextTint : this.normalItemTextTint);
        viewHolder.icon.setColorFilter(this.isChecked ? this.selectedItemIconTint : this.normalItemIconTint);
    }

    public SimpleItem withSelectedIconTint(int i) {
        this.selectedItemIconTint = i;
        return this;
    }

    public SimpleItem withSelectedTextTint(int i) {
        this.selectedItemTextTint = i;
        return this;
    }

    public SimpleItem withIconTint(int i) {
        this.normalItemIconTint = i;
        return this;
    }

    public SimpleItem withTextTint(int i) {
        this.normalItemTextTint = i;
        return this;
    }
}
