package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class PeccancyTypeInfo {

    /**
     * pcode : 1001A
     * premarks : A驾驶拼装的非汽车类机动车上道路行驶的
     * pmoney : 1000
     * pscore : 0
     */

    private String pcode;
    private String premarks;
    private int pmoney;
    private int pscore;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPremarks() {
        return premarks;
    }

    public void setPremarks(String premarks) {
        this.premarks = premarks;
    }

    public int getPmoney() {
        return pmoney;
    }

    public void setPmoney(int pmoney) {
        this.pmoney = pmoney;
    }

    public int getPscore() {
        return pscore;
    }

    public void setPscore(int pscore) {
        this.pscore = pscore;
    }

    @Override
    public String toString() {
        return "PeccancyTypeInfo{" +
                "pcode='" + pcode + '\'' +
                ", premarks='" + premarks + '\'' +
                ", pmoney=" + pmoney +
                ", pscore=" + pscore +
                '}';
    }
}
