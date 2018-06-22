package in.skaipal.kushalm.cuisinicuser.model;

public class LiveMusicOther {
    private String bookingId;
    private String date;
    private String endTime;
    private String reason;
    private String startTime;
    private String status;

    public LiveMusicOther(String str, String str2, String str3, String str4, String str5, String str6) {
        this.bookingId = str;
        this.date = str2;
        this.startTime = str3;
        this.endTime = str4;
        this.status = str5;
        this.reason = str6;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(String str) {
        this.bookingId = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
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
}
