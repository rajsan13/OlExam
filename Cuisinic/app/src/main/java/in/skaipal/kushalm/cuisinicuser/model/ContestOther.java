package in.skaipal.kushalm.cuisinicuser.model;

public class ContestOther {
    private String contest1stPrize;
    private String contest2ndPrize;
    private String contest3rdPrize;
    private String contestDate;
    private String contestDescription;
    private String contestId;

    public ContestOther(String str, String str2, String str3, String str4, String str5, String str6) {
        this.contestId = str;
        this.contestDate = str2;
        this.contestDescription = str6;
        this.contest1stPrize = str3;
        this.contest2ndPrize = str4;
        this.contest3rdPrize = str5;
    }

    public String getContestId() {
        return this.contestId;
    }

    public void setContestId(String str) {
        this.contestId = str;
    }

    public String getContestDate() {
        return this.contestDate;
    }

    public void setContestDate(String str) {
        this.contestDate = str;
    }

    public String getContestDescription() {
        return this.contestDescription;
    }

    public void setContestDescription(String str) {
        this.contestDescription = str;
    }

    public String getContest1stPrize() {
        return this.contest1stPrize;
    }

    public void setContest1stPrize(String str) {
        this.contest1stPrize = str;
    }

    public String getContest2ndPrize() {
        return this.contest2ndPrize;
    }

    public void setContest2ndPrize(String str) {
        this.contest2ndPrize = str;
    }

    public String getContest3rdPrize() {
        return this.contest3rdPrize;
    }

    public void setContest3rdPrize(String str) {
        this.contest3rdPrize = str;
    }
}
