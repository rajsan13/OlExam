package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.MultiCheckGenre;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnCheckChildClickListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnChildCheckChangedListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnChildrenCheckStateChangedListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.CheckedExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.viewholders.CheckableChildViewHolder;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.ExpandableRecyclerViewAdapter;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.GroupViewHolder;
import java.util.ArrayList;
import java.util.List;

public abstract class CheckableChildRecyclerViewAdapter<GVH extends GroupViewHolder, CCVH extends CheckableChildViewHolder> extends ExpandableRecyclerViewAdapter<GVH, CCVH> implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {
    private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";
    private ChildCheckController childCheckController = new ChildCheckController(expandableList, this);
    private OnCheckChildClickListener childClickListener;

    public abstract void onBindCheckChildViewHolder(CCVH ccvh, int i, CheckedExpandableGroup checkedExpandableGroup, int i2);

    public abstract CCVH onCreateCheckChildViewHolder(ViewGroup viewGroup, int i);

    public CheckableChildRecyclerViewAdapter(List<MultiCheckGenre> list) {
        super(list);
    }

    public CCVH onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        viewGroup = onCreateCheckChildViewHolder(viewGroup, i);
        viewGroup.setOnChildCheckedListener(this);
        return viewGroup;
    }

    public void onBindChildViewHolder(CCVH ccvh, int i, ExpandableGroup expandableGroup, int i2) {
        ccvh.onBindViewHolder(i, this.childCheckController.isChildChecked(expandableList.getUnflattenedPosition(i)));
        onBindCheckChildViewHolder(ccvh, i, (CheckedExpandableGroup) expandableGroup, i2);
    }

    public void onChildCheckChanged(View view, boolean z, int i) {
        i = expandableList.getUnflattenedPosition(i);
        this.childCheckController.onChildCheckChanged(z, i);
        if (this.childClickListener != null) {
            this.childClickListener.onCheckChildCLick(view, z, (CheckedExpandableGroup) expandableList.getExpandableGroup(i), i.childPos);
        }
    }

    public void updateChildrenCheckState(int i, int i2) {
        notifyItemRangeChanged(i, i2);
    }

    public void setChildClickListener(OnCheckChildClickListener onCheckChildClickListener) {
        this.childClickListener = onCheckChildClickListener;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(CHECKED_STATE_MAP, new ArrayList(expandableList.groups));
        super.onSaveInstanceState(bundle);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(CHECKED_STATE_MAP)) {
                expandableList.groups = bundle.getParcelableArrayList(CHECKED_STATE_MAP);
                super.onRestoreInstanceState(bundle);
            }
        }
    }

    public void checkChild(boolean z, int i, int i2) {
        this.childCheckController.checkChild(z, i, i2);
        if (this.childClickListener != null) {
            this.childClickListener.onCheckChildCLick(null, z, (CheckedExpandableGroup) expandableList.groups.get(i), i2);
        }
    }

    public void clearChoices() {
        this.childCheckController.clearCheckStates();
        for (int i = 0; i < getGroups().size(); i++) {
            ExpandableGroup expandableGroup = (ExpandableGroup) getGroups().get(i);
            if (isGroupExpanded(expandableGroup)) {
                notifyItemRangeChanged(expandableList.getFlattenedFirstChildIndex(i), expandableGroup.getItemCount());
            }
        }
    }

    public ArrayList<Boolean> getChoices() {
        return this.childCheckController.getCheckedPositionsCustom();
    }
}
