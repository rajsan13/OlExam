package in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class ExpandableGroup<T extends Parcelable> implements Parcelable {
    public static final Creator<ExpandableGroup> CREATOR = new Creator<ExpandableGroup>() {
        public ExpandableGroup createFromParcel(Parcel parcel) {
            return new ExpandableGroup(parcel);
        }

        public ExpandableGroup[] newArray(int i) {
            return new ExpandableGroup[i];
        }
    };
    private String additional;
    private String description;
    private ArrayList<T> items;
    private String price;
    private String quantity;
    private String title;

    public int describeContents() {
        return 0;
    }

    public ExpandableGroup(String str, String str2, String str3, String str4, String str5, ArrayList<T> arrayList) {
        this.title = str;
        this.items = arrayList;
        this.price = str3;
        this.quantity = str4;
        this.additional = str5;
        this.description = str2;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPrice() {
        return this.price;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public String getAdditional() {
        return this.additional;
    }

    public ArrayList<T> getItems() {
        return this.items;
    }

    public int getItemCount() {
        return this.items == null ? 0 : this.items.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExpandableGroup{title='");
        stringBuilder.append(this.title);
        stringBuilder.append('\'');
        stringBuilder.append(", items=");
        stringBuilder.append(this.items.toString());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    protected ExpandableGroup(Parcel parcel) {
        this.title = parcel.readString();
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        if (readByte == (byte) 1) {
            this.items = new ArrayList(readInt);
            parcel.readList(this.items, ((Class) parcel.readSerializable()).getClassLoader());
            return;
        }
        this.items = null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        if (this.items == 0) {
            parcel.writeByte((byte) 0);
            parcel.writeInt(0);
            return;
        }
        parcel.writeByte(1);
        parcel.writeInt(this.items.size());
        parcel.writeSerializable(((Parcelable) this.items.get(0)).getClass());
        parcel.writeList(this.items);
    }
}
