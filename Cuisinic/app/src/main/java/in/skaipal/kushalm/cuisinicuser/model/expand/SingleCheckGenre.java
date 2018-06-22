package in.skaipal.kushalm.cuisinicuser.model.expand;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.SingleCheckExpandableGroup;
import java.util.ArrayList;

public class SingleCheckGenre extends SingleCheckExpandableGroup {
    public static final Creator<SingleCheckGenre> CREATOR = new Creator<SingleCheckGenre>() {
        public SingleCheckGenre createFromParcel(Parcel parcel) {
            return new SingleCheckGenre(parcel);
        }

        public SingleCheckGenre[] newArray(int i) {
            return new SingleCheckGenre[i];
        }
    };
    private int iconResId;

    public int describeContents() {
        return 0;
    }

    public SingleCheckGenre(String str, String str2, String str3, String str4, String str5, ArrayList arrayList, int i) {
        super(str, str2, str3, str4, str5, arrayList);
        this.iconResId = i;
    }

    protected SingleCheckGenre(Parcel parcel) {
        super(parcel);
        this.iconResId = parcel.readInt();
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.iconResId);
    }
}
