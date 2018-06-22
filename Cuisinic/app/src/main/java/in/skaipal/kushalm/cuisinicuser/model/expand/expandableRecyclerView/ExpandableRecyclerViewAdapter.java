package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners.ExpandCollapseListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners.GroupExpandCollapseListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners.OnGroupClickListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableList;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableListPosition;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.ChildViewHolder;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.GroupViewHolder;
import java.util.List;

public abstract class ExpandableRecyclerViewAdapter<GVH extends GroupViewHolder, CVH extends ChildViewHolder> extends Adapter implements ExpandCollapseListener, OnGroupClickListener {
    private static final String EXPAND_STATE_MAP = "expandable_recyclerview_adapter_expand_state_map";
    public static ExpandCollapseController expandCollapseController;
    public static ExpandableList expandableList;
    private GroupExpandCollapseListener expandCollapseListener;
    private OnGroupClickListener groupClickListener;

    public abstract void onBindChildViewHolder(CVH cvh, int i, ExpandableGroup expandableGroup, int i2);

    public abstract void onBindGroupViewHolder(GVH gvh, int i, ExpandableGroup expandableGroup);

    public abstract CVH onCreateChildViewHolder(ViewGroup viewGroup, int i);

    public abstract GVH onCreateGroupViewHolder(ViewGroup viewGroup, int i);

    public ExpandableRecyclerViewAdapter(List<? extends ExpandableGroup> list) {
        expandableList = new ExpandableList(list);
        expandCollapseController = new ExpandCollapseController(expandableList, this);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                return onCreateChildViewHolder(viewGroup, i);
            case 2:
                viewGroup = onCreateGroupViewHolder(viewGroup, i);
                viewGroup.setOnGroupClickListener(this);
                return viewGroup;
            default:
                throw new IllegalArgumentException("viewType is not valid");
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ExpandableListPosition unflattenedPosition = expandableList.getUnflattenedPosition(i);
        ExpandableGroup expandableGroup = expandableList.getExpandableGroup(unflattenedPosition);
        switch (unflattenedPosition.type) {
            case 1:
                onBindChildViewHolder((ChildViewHolder) viewHolder, i, expandableGroup, unflattenedPosition.childPos);
                return;
            case 2:
                GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
                onBindGroupViewHolder(groupViewHolder, i, expandableGroup);
                if (isGroupExpanded(expandableGroup) != 0) {
                    groupViewHolder.expand();
                    return;
                } else {
                    groupViewHolder.collapse();
                    return;
                }
            default:
                return;
        }
    }

    public int getItemCount() {
        return expandableList.getVisibleItemCount();
    }

    public int getItemViewType(int i) {
        return expandableList.getUnflattenedPosition(i).type;
    }

    public void onGroupExpanded(int i, int i2) {
        notifyItemChanged(i - 1);
        if (i2 > 0) {
            notifyItemRangeInserted(i, i2);
            if (this.expandCollapseListener != 0) {
                this.expandCollapseListener.onGroupExpanded((ExpandableGroup) getGroups().get(expandableList.getUnflattenedPosition(i).groupPos));
            }
        }
    }

    public void onGroupCollapsed(int i, int i2) {
        int i3 = i - 1;
        notifyItemChanged(i3);
        if (i2 > 0) {
            notifyItemRangeRemoved(i, i2);
            if (this.expandCollapseListener != 0) {
                this.expandCollapseListener.onGroupCollapsed((ExpandableGroup) getGroups().get(expandableList.getUnflattenedPosition(i3).groupPos));
            }
        }
    }

    public boolean onGroupClick(int i) {
        if (this.groupClickListener != null) {
            this.groupClickListener.onGroupClick(i);
        }
        return expandCollapseController.toggleGroup(i);
    }

    public boolean toggleGroup(int i) {
        return expandCollapseController.toggleGroup(i);
    }

    public boolean toggleGroup(ExpandableGroup expandableGroup) {
        return expandCollapseController.toggleGroup(expandableGroup);
    }

    public boolean isGroupExpanded(int i) {
        return expandCollapseController.isGroupExpanded(i);
    }

    public boolean isGroupExpanded(ExpandableGroup expandableGroup) {
        return expandCollapseController.isGroupExpanded(expandableGroup);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBooleanArray(EXPAND_STATE_MAP, expandableList.expandedGroupIndexes);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(EXPAND_STATE_MAP)) {
                expandableList.expandedGroupIndexes = bundle.getBooleanArray(EXPAND_STATE_MAP);
                notifyDataSetChanged();
            }
        }
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.groupClickListener = onGroupClickListener;
    }

    public void setOnGroupExpandCollapseListener(GroupExpandCollapseListener groupExpandCollapseListener) {
        this.expandCollapseListener = groupExpandCollapseListener;
    }

    public List<? extends ExpandableGroup> getGroups() {
        return expandableList.groups;
    }
}
