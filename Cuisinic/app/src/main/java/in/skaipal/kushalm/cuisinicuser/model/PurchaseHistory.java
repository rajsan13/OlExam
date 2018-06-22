package in.skaipal.kushalm.cuisinicuser.model;

public class PurchaseHistory {
    private String amount;
    private String date;
    private String items;
    private String restName;

    public PurchaseHistory(String str, String str2, String str3, String str4) {
        this.restName = str;
        this.amount = str2;
        this.items = str3;
        this.date = str4;
    }

    public String getRestName() {
        return this.restName;
    }

    public void setrestname(String str) {
        this.restName = str;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public String getItems() {
        return this.items;
    }

    public void setItems(String str) {
        this.items = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }
}
