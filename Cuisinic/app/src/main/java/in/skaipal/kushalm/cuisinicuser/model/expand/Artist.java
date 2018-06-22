package in.skaipal.kushalm.cuisinicuser.model.expand;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Artist implements Parcelable {
    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel parcel) {
            return new Artist(parcel);
        }

        public Artist[] newArray(int i) {
            return new Artist[i];
        }
    };
    private boolean isFavorite;
    private String name;

    public int describeContents() {
        return 0;
    }

    public Artist(String str, boolean z) {
        this.name = str;
        this.isFavorite = z;
    }

    protected Artist(Parcel parcel) {
        this.name = parcel.readString();
    }

    public String getName() {
        return this.name;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Artist)) {
            return false;
        }
        Artist artist = (Artist) obj;
        if (isFavorite() != artist.isFavorite()) {
            return false;
        }
        if (getName() != null) {
            z = getName().equals(artist.getName());
        } else if (artist.getName() != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (31 * (getName() != null ? getName().hashCode() : 0)) + isFavorite();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
    }
}
