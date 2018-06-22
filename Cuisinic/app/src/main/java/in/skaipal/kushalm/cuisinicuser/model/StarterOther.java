package in.skaipal.kushalm.cuisinicuser.model;

public class StarterOther {
    private String itemName;
    private String itemPrice;
    private String itemType;

    public StarterOther(String str, String str2, String str3) {
        this.itemName = str;
        this.itemPrice = str2;
        this.itemType = str3;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String str) {
        this.itemName = str;
    }

    public String getItemPrice() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("₹ ");
        stringBuilder.append(this.itemPrice);
        return stringBuilder.toString();
    }

    public void setItemPrice(String str) {
        this.itemPrice = str;
    }

    public String getItemType() {
        return this.itemType;
    }

    public void setItemType(String str) {
        this.itemType = str;
    }
}
