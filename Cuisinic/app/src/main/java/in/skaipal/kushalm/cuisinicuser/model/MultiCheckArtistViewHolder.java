package in.skaipal.kushalm.cuisinicuser.model;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import in.skaipal.kushalm.cuisinicuser.R;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.viewholders.CheckableChildViewHolder;

public class MultiCheckArtistViewHolder extends CheckableChildViewHolder {
    private CheckedTextView childCheckedTextView;

    public MultiCheckArtistViewHolder(View view) {
        super(view);
        this.childCheckedTextView = (CheckedTextView) view.findViewById(R.id.list_item_multicheck_artist_name);
    }

    public Checkable getCheckable() {
        return this.childCheckedTextView;
    }

    public void setArtistName(String str) {
        this.childCheckedTextView.setText(str);
    }

    public void setChecked(boolean z) {
        this.childCheckedTextView.setChecked(z);
    }
}
