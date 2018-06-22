package in.skaipal.kushalm.cuisinicuser.model;

public class HomeDelivery {
    private String Address;
    private String Mobile;
    private String Name;
    private String OrderCoupon;
    private String OrderDiscount;
    private String OrderItems;
    private String OrderNo;
    private String OrderPaidPrice;
    private String OrderPrice;
    private String Timestamp;
    private String TotalItem;

    public HomeDelivery(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.Timestamp = str;
        this.OrderNo = str2;
        this.TotalItem = str3;
        this.Name = str4;
        this.Mobile = str5;
        this.Address = str6;
        this.OrderItems = str7;
        this.OrderPrice = str8;
        this.OrderPaidPrice = str9;
        this.OrderCoupon = str10;
        this.OrderDiscount = str11;
    }

    public String getTimestamp() {
        return this.Timestamp;
    }

    public String getOrderNo() {
        return this.OrderNo;
    }

    public String getTotalItem() {
        return this.TotalItem;
    }

    public String getName() {
        return this.Name;
    }

    public String getMobile() {
        return this.Mobile;
    }

    public String getAddress() {
        return this.Address;
    }

    public String getOrderItems() {
        return this.OrderItems;
    }

    public String getOrderPrice() {
        return this.OrderPrice;
    }

    public String getOrderPaidPrice() {
        return this.OrderPaidPrice;
    }

    public String getOrderCoupon() {
        return this.OrderCoupon;
    }

    public String getOrderDiscount() {
        return this.OrderDiscount;
    }

    public void setTimestamp(String str) {
        this.Timestamp = str;
    }

    public void setOrderNo(String str) {
        this.OrderNo = str;
    }

    public void setTotalItem(String str) {
        this.TotalItem = str;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public void setMobile(String str) {
        this.Mobile = str;
    }

    public void setAddress(String str) {
        this.Address = str;
    }
}
