package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class CarPeccancyInfo {
    private String carnumber;
    private String times;
    private String pmoney;
    private String pscore;

    public CarPeccancyInfo() {
    }

    public CarPeccancyInfo(String carnumber, String times, String pmoney, String pscore) {
        this.carnumber = carnumber;
        this.times = times;
        this.pmoney = pmoney;
        this.pscore = pscore;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPmoney() {
        return pmoney;
    }

    public void setPmoney(String pmoney) {
        this.pmoney = pmoney;
    }

    public String getPscore() {
        return pscore;
    }

    public void setPscore(String pscore) {
        this.pscore = pscore;
    }

    @Override
    public String toString() {
        return "CarPeccancyInfo{" +
                "carnumber='" + carnumber + '\'' +
                ", times='" + times + '\'' +
                ", pmoney='" + pmoney + '\'' +
                ", pscore='" + pscore + '\'' +
                '}';
    }
}
