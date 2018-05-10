package com.mad.trafficclient.model;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RechargeLog {

    String loginName;
    String userName;
    int money;
    int balance;
    long date;
    int number;

    public RechargeLog(String loginName, String userName, int money, int balance, long date, int number) {
        this.loginName = loginName;
        this.userName = userName;
        this.money = money;
        this.balance = balance;
        this.date = date;
        this.number = number;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
