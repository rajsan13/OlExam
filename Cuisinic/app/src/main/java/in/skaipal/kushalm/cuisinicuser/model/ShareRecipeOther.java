package in.skaipal.kushalm.cuisinicuser.model;

public class ShareRecipeOther {
    private String des;
    private String details;
    private String name;
    private String price;
    private String reason;
    private String status;
    private String type;

    public ShareRecipeOther(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.name = str;
        this.price = str2;
        this.des = str3;
        this.details = str4;
        this.status = str6;
        this.reason = str7;
        this.type = str5;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String str) {
        this.des = str;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String str) {
        this.details = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
