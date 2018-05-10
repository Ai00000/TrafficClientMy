package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class TrafficLightInfo {
    private int lightId;
    private int red;
    private int green;
    private int yellow;

    public TrafficLightInfo() {
    }

    public TrafficLightInfo(int lightId, int red, int green, int yellow) {
        this.lightId = lightId;
        this.red = red;
        this.green = green;
        this.yellow = yellow;
    }

    public int getLightId() {
        return lightId;
    }

    public void setLightId(int lightId) {
        this.lightId = lightId;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    @Override
    public String toString() {
        return "TrafficLightInfo{" +
                "lightId=" + lightId +
                ", red=" + red +
                ", green=" + green +
                ", yellow=" + yellow +
                '}';
    }
}
