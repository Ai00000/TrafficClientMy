package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class XActionInfo implements Comparable<XActionInfo> {
    private String action;
    private int count;

    public XActionInfo() {
    }

    public XActionInfo(String action, int count) {
        this.action = action;
        this.count = count;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "XActionInfo{" +
                "action='" + action + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(XActionInfo another) {
        return getCount() - another.getCount();
    }
}
