package in.skaipal.kushalm.cuisinicuser.model;

public class TableBooking {
    private String Members;
    private String Mobile;
    private String Name;
    private String OrderCoupon;
    private String OrderDate;
    private String OrderDiscount;
    private String OrderItems;
    private String OrderNo;
    private String OrderPaidPrice;
    private String OrderPrice;
    private String OrderTime;
    private String TableNo;
    private String Timestamp;
    private String TotalItem;

    public TableBooking(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.Timestamp = str;
        this.OrderNo = str2;
        this.TotalItem = str3;
        this.Name = str4;
        this.Mobile = str5;
        this.TableNo = str6;
        this.Members = str7;
        this.OrderDate = str9;
        this.OrderTime = str8;
        this.OrderItems = str10;
        this.OrderPrice = str11;
        this.OrderPaidPrice = str12;
        this.OrderCoupon = str13;
        this.OrderDiscount = str14;
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

    public String getOrderTime() {
        return this.OrderTime;
    }

    public String getOrderDate() {
        return this.OrderDate;
    }

    public String getTableNo() {
        return this.TableNo;
    }

    public String getMembers() {
        return this.Members;
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

    public void setOrderTime(String str) {
        this.OrderTime = str;
    }

    public void setOrderDate(String str) {
        this.OrderDate = str;
    }

    public void setTableNo(String str) {
        this.TableNo = str;
    }

    public void setMembers(String str) {
        this.Members = str;
    }
}
