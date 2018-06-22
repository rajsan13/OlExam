package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models;

import android.widget.ExpandableListView;
import java.util.ArrayList;

public class ExpandableListPosition {
    public static final int CHILD = 1;
    public static final int GROUP = 2;
    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<ExpandableListPosition> sPool = new ArrayList(5);
    public int childPos;
    int flatListPos;
    public int groupPos;
    public int type;

    private void resetState() {
        this.groupPos = 0;
        this.childPos = 0;
        this.flatListPos = 0;
        this.type = 0;
    }

    private ExpandableListPosition() {
    }

    public long getPackedPosition() {
        if (this.type == 1) {
            return ExpandableListView.getPackedPositionForChild(this.groupPos, this.childPos);
        }
        return ExpandableListView.getPackedPositionForGroup(this.groupPos);
    }

    static ExpandableListPosition obtainGroupPosition(int i) {
        return obtain(2, i, 0, 0);
    }

    static ExpandableListPosition obtainChildPosition(int i, int i2) {
        return obtain(1, i, i2, 0);
    }

    static ExpandableListPosition obtainPosition(long j) {
        if (j == 4294967295L) {
            return 0;
        }
        ExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.groupPos = ExpandableListView.getPackedPositionGroup(j);
        if (ExpandableListView.getPackedPositionType(j) == 1) {
            recycledOrCreate.type = 1;
            recycledOrCreate.childPos = ExpandableListView.getPackedPositionChild(j);
        } else {
            recycledOrCreate.type = 2;
        }
        return recycledOrCreate;
    }

    public static ExpandableListPosition obtain(int i, int i2, int i3, int i4) {
        ExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.type = i;
        recycledOrCreate.groupPos = i2;
        recycledOrCreate.childPos = i3;
        recycledOrCreate.flatListPos = i4;
        return recycledOrCreate;
    }

    private static ExpandableListPosition getRecycledOrCreate() {
        synchronized (sPool) {
            if (sPool.size() > 0) {
                ExpandableListPosition expandableListPosition = (ExpandableListPosition) sPool.remove(0);
                expandableListPosition.resetState();
                return expandableListPosition;
            }
            expandableListPosition = new ExpandableListPosition();
            return expandableListPosition;
        }
    }

    public void recycle() {
        synchronized (sPool) {
            if (sPool.size() < 5) {
                sPool.add(this);
            }
        }
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                ExpandableListPosition expandableListPosition = (ExpandableListPosition) obj;
                if (this.groupPos != expandableListPosition.groupPos || this.childPos != expandableListPosition.childPos || this.flatListPos != expandableListPosition.flatListPos) {
                    return false;
                }
                if (this.type != expandableListPosition.type) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (31 * ((((this.groupPos * 31) + this.childPos) * 31) + this.flatListPos)) + this.type;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExpandableListPosition{groupPos=");
        stringBuilder.append(this.groupPos);
        stringBuilder.append(", childPos=");
        stringBuilder.append(this.childPos);
        stringBuilder.append(", flatListPos=");
        stringBuilder.append(this.flatListPos);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
