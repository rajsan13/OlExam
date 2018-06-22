package in.skaipal.kushalm.cuisinicuser.model.expand;

import in.skaipal.kushalm.cuisinicuser.model.expand.expandableRecyclerView.models.ExpandableGroup;
import java.util.ArrayList;

public class Genre extends ExpandableGroup<Artist> {
    public Genre(String str, String str2, String str3, String str4, String str5, ArrayList<Artist> arrayList) {
        super(str, str2, str3, str4, str5, arrayList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Genre)) {
            return null;
        }
        Genre genre = (Genre) obj;
        return true;
    }
}
