package in.skaipal.kushalm.cuisinicuser.model;

public class addressModel {
    private String address;
    private String mobile;
    private String name;

    public addressModel(String str, String str2, String str3) {
        this.name = str;
        this.address = str3;
        this.mobile = str2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getName() {
        return this.name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getAddress() {
        return this.address;
    }
}
