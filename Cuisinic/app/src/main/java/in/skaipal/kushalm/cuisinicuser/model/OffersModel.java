package in.skaipal.kushalm.cuisinicuser.model;

import android.support.media.ExifInterface;
import java.io.Serializable;

public class OffersModel implements Serializable {
    private String condition;
    private String description;
    private String name;
    private String nearby;
    private String status;
    private String total;
    private String type;
    private String valid_upto;
    private String value;

    public OffersModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.name = str;
        this.valid_upto = str2;
        this.condition = str3;
        this.type = str4;
        this.value = str5;
        this.status = str6;
        this.nearby = str7;
        this.description = str8;
        this.total = str9;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getValid_upto() {
        return this.valid_upto;
    }

    public void setValid_upto(String str) {
        this.valid_upto = str;
    }

    public String getCondition() {
        if (this.condition.equalsIgnoreCase("1")) {
            return "greater than";
        }
        return this.condition.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) ? "equals to" : null;
    }

    public void setCondition(String str) {
        this.condition = str;
    }

    public String getType() {
        if (this.type.equalsIgnoreCase("1")) {
            return "%";
        }
        return this.type.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) ? "₹" : null;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getNearby() {
        return this.nearby;
    }

    public void setNearby(String str) {
        this.nearby = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getTotal() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("₹");
        stringBuilder.append(this.total);
        return stringBuilder.toString();
    }

    public void setTotal(String str) {
        this.total = str;
    }
}
