package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class XPecInfo {

    /**
     * id : 1
     * carnumber : 鲁B10001
     * pcode : 1001A　　
     * pdatetime : 08:19:21
     * paddr : 学院路
     */

    private int id;
    private String carnumber;
    private String pcode;
    private String pdatetime;
    private String paddr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPdatetime() {
        return pdatetime;
    }

    public void setPdatetime(String pdatetime) {
        this.pdatetime = pdatetime;
    }

    public String getPaddr() {
        return paddr;
    }

    public void setPaddr(String paddr) {
        this.paddr = paddr;
    }

    @Override
    public String toString() {
        return "XPecInfo{" +
                "id=" + id +
                ", carnumber='" + carnumber + '\'' +
                ", pcode='" + pcode + '\'' +
                ", pdatetime='" + pdatetime + '\'' +
                ", paddr='" + paddr + '\'' +
                '}';
    }
}
