package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners.OnGroupClickListener;

public abstract class GroupViewHolder extends ViewHolder implements OnClickListener {
    private OnGroupClickListener listener;

    public void collapse() {
    }

    public void expand() {
    }

    public GroupViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.listener != null) {
            this.listener.onGroupClick(getAdapterPosition());
        }
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.listener = onGroupClickListener;
    }
}
