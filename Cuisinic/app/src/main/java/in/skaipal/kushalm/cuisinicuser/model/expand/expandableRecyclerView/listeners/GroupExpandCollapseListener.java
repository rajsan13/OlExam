package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.listeners;

import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;

public interface GroupExpandCollapseListener {
    void onGroupCollapsed(ExpandableGroup expandableGroup);

    void onGroupExpanded(ExpandableGroup expandableGroup);
}
