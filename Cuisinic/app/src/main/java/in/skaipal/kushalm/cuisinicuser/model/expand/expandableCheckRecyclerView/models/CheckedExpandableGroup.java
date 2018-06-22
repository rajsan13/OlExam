package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models;

import android.os.Parcel;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CheckedExpandableGroup extends ExpandableGroup {
    public String Additional;
    public String Price;
    public String Quantity;
    public boolean[] selectedChildren;

    public int describeContents() {
        return 0;
    }

    public abstract void onChildClicked(int i, boolean z);

    public CheckedExpandableGroup(String str, String str2, String str3, String str4, String str5, ArrayList arrayList) {
        super(str, str2, str3, str4, str5, arrayList);
        this.Price = str3;
        this.Quantity = str4;
        this.Additional = str5;
        this.selectedChildren = new boolean[arrayList.size()];
        for (str2 = null; str2 < arrayList.size(); str2++) {
            this.selectedChildren[str2] = null;
        }
    }

    public void checkChild(int i) {
        this.selectedChildren[i] = true;
    }

    public void unCheckChild(int i) {
        this.selectedChildren[i] = false;
    }

    public boolean isChildChecked(int i) {
        return this.selectedChildren[i];
    }

    public int getCheckedCount() {
        int i = 0;
        int i2 = 0;
        while (i < this.selectedChildren.length) {
            if (this.selectedChildren[i]) {
                i2++;
            }
            i++;
        }
        return i2;
    }

    public String getQuantity() {
        return this.Quantity;
    }

    public void clearSelections() {
        if (this.selectedChildren != null) {
            Arrays.fill(this.selectedChildren, false);
        }
    }

    protected CheckedExpandableGroup(Parcel parcel) {
        super(parcel);
        this.selectedChildren = parcel.createBooleanArray();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBooleanArray(this.selectedChildren);
    }
}
