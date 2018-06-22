package in.skaipal.kushalm.cuisinicuser.model;

import java.util.ArrayList;

public class saladIndividualModel {
    private String category;
    private ArrayList<String> items;

    public saladIndividualModel(String str, ArrayList<String> arrayList) {
        this.category = str;
        this.items = arrayList;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public void setItems(ArrayList<String> arrayList) {
        this.items = arrayList;
    }

    public String getCategory() {
        return this.category;
    }

    public ArrayList<String> getItems() {
        return this.items;
    }
}
