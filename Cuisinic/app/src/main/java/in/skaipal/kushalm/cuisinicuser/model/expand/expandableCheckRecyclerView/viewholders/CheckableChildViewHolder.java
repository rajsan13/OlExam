package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.viewholders;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnChildCheckChangedListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.ChildViewHolder;

public abstract class CheckableChildViewHolder extends ChildViewHolder implements OnClickListener {
    private Checkable checkable;
    private OnChildCheckChangedListener listener;

    public abstract Checkable getCheckable();

    public CheckableChildViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);
    }

    public void onBindViewHolder(int i, boolean z) {
        this.checkable = getCheckable();
        this.checkable.setChecked(z);
    }

    public void onClick(View view) {
        this.checkable.toggle();
        if (this.listener != null) {
            this.listener.onChildCheckChanged(view, this.checkable.isChecked(), getAdapterPosition());
        }
    }

    public void setOnChildCheckedListener(OnChildCheckChangedListener onChildCheckChangedListener) {
        this.listener = onChildCheckChangedListener;
    }
}
