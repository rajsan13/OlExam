package in.skaipal.kushalm.cuisinicuser.model.expand;

import in.skaipal.kushalm.cuisinicuser.model.expand.expandableCheckRecyclerView.models.MultiCheckExpandableGroup;
import java.util.ArrayList;

public class MultiCheckGenre extends MultiCheckExpandableGroup {
    private String Additional;
    private String Description;
    private ArrayList<Artist> Item;
    private String Price;
    private String Quantity;
    private String Title;

    public MultiCheckGenre(String str, String str2, String str3, String str4, String str5, ArrayList<Artist> arrayList) {
        super(str, str2, str3, str4, str5, arrayList);
        this.Title = str;
        this.Description = str2;
        this.Item = arrayList;
        this.Price = str3;
        this.Quantity = str4;
        this.Additional = str5;
    }

    public void setTitle(String str) {
        this.Title = str;
    }

    public void setDescription(String str) {
        this.Description = str;
    }

    public String getDescription() {
        return this.Description;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getPrice() {
        return this.Price;
    }

    public String getQuantity() {
        return this.Quantity;
    }

    public String getAdditional() {
        return this.Additional;
    }

    public void setItem(ArrayList<Artist> arrayList) {
        this.Item = arrayList;
    }

    public ArrayList<Artist> getItem() {
        return this.Item;
    }
}
