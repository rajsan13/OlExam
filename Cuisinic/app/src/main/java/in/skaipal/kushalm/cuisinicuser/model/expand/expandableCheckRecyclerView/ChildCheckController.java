package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView;

import android.widget.ExpandableListView;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.listeners.OnChildrenCheckStateChangedListener;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.CheckedExpandableGroup;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableList;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableListPosition;
import java.util.ArrayList;
import java.util.List;

public class ChildCheckController {
    private OnChildrenCheckStateChangedListener childrenUpdateListener;
    private ExpandableList expandableList;
    private List<Integer> initialCheckedPositions = getCheckedPositions();

    public ChildCheckController(ExpandableList expandableList, OnChildrenCheckStateChangedListener onChildrenCheckStateChangedListener) {
        this.expandableList = expandableList;
        this.childrenUpdateListener = onChildrenCheckStateChangedListener;
    }

    public void onChildCheckChanged(boolean z, ExpandableListPosition expandableListPosition) {
        ((CheckedExpandableGroup) this.expandableList.groups.get(expandableListPosition.groupPos)).onChildClicked(expandableListPosition.childPos, z);
        if (this.childrenUpdateListener) {
            this.childrenUpdateListener.updateChildrenCheckState(this.expandableList.getFlattenedFirstChildIndex(expandableListPosition), this.expandableList.getExpandableGroupItemCount(expandableListPosition));
        }
    }

    public void checkChild(boolean z, int i, int i2) {
        CheckedExpandableGroup checkedExpandableGroup = (CheckedExpandableGroup) this.expandableList.groups.get(i);
        checkedExpandableGroup.onChildClicked(i2, z);
        if (this.childrenUpdateListener && this.expandableList.expandedGroupIndexes[i]) {
            this.childrenUpdateListener.updateChildrenCheckState(this.expandableList.getFlattenedFirstChildIndex(i), checkedExpandableGroup.getItemCount());
        }
    }

    public boolean isChildChecked(ExpandableListPosition expandableListPosition) {
        return ((CheckedExpandableGroup) this.expandableList.groups.get(expandableListPosition.groupPos)).isChildChecked(expandableListPosition.childPos);
    }

    public ArrayList<Integer> getCheckedPositions() {
        ArrayList<Integer> arrayList = new ArrayList();
        for (int i = 0; i < this.expandableList.groups.size(); i++) {
            if (this.expandableList.groups.get(i) instanceof CheckedExpandableGroup) {
                CheckedExpandableGroup checkedExpandableGroup = (CheckedExpandableGroup) this.expandableList.groups.get(i);
                for (int i2 = 0; i2 < checkedExpandableGroup.getItemCount(); i2++) {
                    if (checkedExpandableGroup.isChildChecked(i2)) {
                        arrayList.add(Integer.valueOf(this.expandableList.getFlattenedChildIndex(ExpandableListView.getPackedPositionForChild(i, i2))));
                    }
                }
            }
        }
        return arrayList;
    }

    public ArrayList<Boolean> getCheckedPositionsCustom() {
        ArrayList<Boolean> arrayList = new ArrayList();
        for (int i = 0; i < this.expandableList.groups.size(); i++) {
            if (this.expandableList.groups.get(i) instanceof CheckedExpandableGroup) {
                CheckedExpandableGroup checkedExpandableGroup = (CheckedExpandableGroup) this.expandableList.groups.get(i);
                for (int i2 = 0; i2 < checkedExpandableGroup.getItemCount(); i2++) {
                    if (checkedExpandableGroup.isChildChecked(i2)) {
                        arrayList.add(Boolean.valueOf(true));
                    } else {
                        arrayList.add(Boolean.valueOf(false));
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean checksChanged() {
        return this.initialCheckedPositions.equals(getCheckedPositions()) ^ 1;
    }

    public void clearCheckStates() {
        for (int i = 0; i < this.expandableList.groups.size(); i++) {
            ((CheckedExpandableGroup) this.expandableList.groups.get(i)).clearSelections();
        }
    }
}
