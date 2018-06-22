package in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class SingleCheckExpandableGroup extends CheckedExpandableGroup {
    public static final Creator<SingleCheckExpandableGroup> CREATOR = new Creator<SingleCheckExpandableGroup>() {
        public SingleCheckExpandableGroup createFromParcel(Parcel parcel) {
            return new SingleCheckExpandableGroup(parcel);
        }

        public SingleCheckExpandableGroup[] newArray(int i) {
            return new SingleCheckExpandableGroup[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public SingleCheckExpandableGroup(String str, String str2, String str3, String str4, String str5, ArrayList arrayList) {
        super(str, str2, str3, str4, str5, arrayList);
    }

    public void onChildClicked(int i, boolean z) {
        if (z) {
            for (z = false; z < getItemCount(); z++) {
                unCheckChild(z);
            }
            checkChild(i);
        }
    }

    protected SingleCheckExpandableGroup(Parcel parcel) {
        super(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
