package in.skaipal.kushalm.cuisinicuser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("url")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }
}
