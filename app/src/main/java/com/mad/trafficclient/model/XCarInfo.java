package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class XCarInfo {

    /**
     * carnumber : 鲁A10001
     * number : 101
     * pcardid : 370214197107271055
     * carbrand : audi
     * buydate : 2016-06-01
     */

    private String carnumber;
    private int number;
    private String pcardid;
    private String carbrand;
    private String buydate;

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPcardid() {
        return pcardid;
    }

    public void setPcardid(String pcardid) {
        this.pcardid = pcardid;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    @Override
    public String toString() {
        return "XCarInfo{" +
                "carnumber='" + carnumber + '\'' +
                ", number=" + number +
                ", pcardid='" + pcardid + '\'' +
                ", carbrand='" + carbrand + '\'' +
                ", buydate='" + buydate + '\'' +
                '}';
    }
}
