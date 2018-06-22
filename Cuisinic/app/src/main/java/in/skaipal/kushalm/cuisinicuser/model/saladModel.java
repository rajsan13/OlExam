package in.skaipal.kushalm.cuisinicuser.model;

public class saladModel {
    private String items;
    private String name;
    private String price;
    private String quantity;

    public saladModel(String str, String str2, String str3, String str4) {
        this.name = str;
        this.price = str2;
        this.items = str3;
        this.quantity = str4;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public void setQuantity(String str) {
        this.quantity = str;
    }

    public void setItems(String str) {
        this.items = str;
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public String getItems() {
        return this.items;
    }

    public String getQuantity() {
        return this.quantity;
    }
}
