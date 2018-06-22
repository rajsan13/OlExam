package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class MultiCheckExpandableGroup extends CheckedExpandableGroup {
    public static final Creator<MultiCheckExpandableGroup> CREATOR = new Creator<MultiCheckExpandableGroup>() {
        public MultiCheckExpandableGroup createFromParcel(Parcel parcel) {
            return new MultiCheckExpandableGroup(parcel);
        }

        public MultiCheckExpandableGroup[] newArray(int i) {
            return new MultiCheckExpandableGroup[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public MultiCheckExpandableGroup(String str, String str2, String str3, String str4, String str5, ArrayList arrayList) {
        super(str, str2, str3, str4, str5, arrayList);
    }

    public void onChildClicked(int i, boolean z) {
        if (z) {
            checkChild(i);
        } else {
            unCheckChild(i);
        }
    }

    protected MultiCheckExpandableGroup(Parcel parcel) {
        super(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
