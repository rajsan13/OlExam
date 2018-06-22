package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableListPosition;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.ChildViewHolder;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.viewholders.GroupViewHolder;
import java.util.List;

public abstract class MultiTypeExpandableRecyclerViewAdapter<GVH extends GroupViewHolder, CVH extends ChildViewHolder> extends ExpandableRecyclerViewAdapter<GVH, CVH> {
    public boolean isChild(int i) {
        return i == 1;
    }

    public boolean isGroup(int i) {
        return i == 2;
    }

    public MultiTypeExpandableRecyclerViewAdapter(List<? extends ExpandableGroup> list) {
        super(list);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (isGroup(i)) {
            viewGroup = onCreateGroupViewHolder(viewGroup, i);
            viewGroup.setOnGroupClickListener(this);
            return viewGroup;
        } else if (isChild(i)) {
            return onCreateChildViewHolder(viewGroup, i);
        } else {
            throw new IllegalArgumentException("viewType is not valid");
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ExpandableListPosition unflattenedPosition = expandableList.getUnflattenedPosition(i);
        ExpandableGroup expandableGroup = expandableList.getExpandableGroup(unflattenedPosition);
        if (isGroup(getItemViewType(i))) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
            onBindGroupViewHolder(groupViewHolder, i, expandableGroup);
            if (isGroupExpanded(expandableGroup) != 0) {
                groupViewHolder.expand();
            } else {
                groupViewHolder.collapse();
            }
        } else if (isChild(getItemViewType(i))) {
            onBindChildViewHolder((ChildViewHolder) viewHolder, i, expandableGroup, unflattenedPosition.childPos);
        }
    }

    public int getItemViewType(int i) {
        ExpandableListPosition unflattenedPosition = expandableList.getUnflattenedPosition(i);
        ExpandableGroup expandableGroup = expandableList.getExpandableGroup(unflattenedPosition);
        int i2 = unflattenedPosition.type;
        switch (i2) {
            case 1:
                return getChildViewType(i, expandableGroup, unflattenedPosition.childPos);
            case 2:
                return getGroupViewType(i, expandableGroup);
            default:
                return i2;
        }
    }

    public int getChildViewType(int i, ExpandableGroup expandableGroup, int i2) {
        return super.getItemViewType(i);
    }

    public int getGroupViewType(int i, ExpandableGroup expandableGroup) {
        return super.getItemViewType(i);
    }
}
