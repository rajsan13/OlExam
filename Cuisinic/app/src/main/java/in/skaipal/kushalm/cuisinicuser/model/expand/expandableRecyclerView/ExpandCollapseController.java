package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView;

import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners.ExpandCollapseListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableList;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableListPosition;

public class ExpandCollapseController {
    private ExpandableList expandableList;
    private ExpandCollapseListener listener;

    public ExpandCollapseController(ExpandableList expandableList, ExpandCollapseListener expandCollapseListener) {
        this.expandableList = expandableList;
        this.listener = expandCollapseListener;
    }

    private void collapseGroup(ExpandableListPosition expandableListPosition) {
        this.expandableList.expandedGroupIndexes[expandableListPosition.groupPos] = false;
        if (this.listener != null) {
            this.listener.onGroupCollapsed(this.expandableList.getFlattenedGroupIndex(expandableListPosition) + 1, ((ExpandableGroup) this.expandableList.groups.get(expandableListPosition.groupPos)).getItemCount());
        }
    }

    public void expandGroup(ExpandableListPosition expandableListPosition) {
        this.expandableList.expandedGroupIndexes[expandableListPosition.groupPos] = true;
        if (this.listener != null) {
            this.listener.onGroupExpanded(this.expandableList.getFlattenedGroupIndex(expandableListPosition) + 1, ((ExpandableGroup) this.expandableList.groups.get(expandableListPosition.groupPos)).getItemCount());
        }
    }

    public boolean isGroupExpanded(ExpandableGroup expandableGroup) {
        return this.expandableList.expandedGroupIndexes[this.expandableList.groups.indexOf(expandableGroup)];
    }

    public boolean isGroupExpanded(int i) {
        return this.expandableList.expandedGroupIndexes[this.expandableList.getUnflattenedPosition(i).groupPos];
    }

    public boolean toggleGroup(int i) {
        i = this.expandableList.getUnflattenedPosition(i);
        boolean z = this.expandableList.expandedGroupIndexes[i.groupPos];
        if (z) {
            collapseGroup(i);
        } else {
            expandGroup(i);
        }
        return z;
    }

    public boolean toggleGroup(ExpandableGroup expandableGroup) {
        expandableGroup = this.expandableList.getUnflattenedPosition(this.expandableList.getFlattenedGroupIndex(expandableGroup));
        boolean z = this.expandableList.expandedGroupIndexes[expandableGroup.groupPos];
        if (z) {
            collapseGroup(expandableGroup);
        } else {
            expandGroup(expandableGroup);
        }
        return z;
    }
}
