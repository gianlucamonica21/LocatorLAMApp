package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.AP;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(tableName = "wifiAP",primaryKeys = {"id","mac"})
public class AP {
    //todo rendere id autoincrement
    @NonNull
    int id;
    @NonNull
    String mac;
    @NonNull
    String ssid;

    @Ignore
    public AP(){}

    public AP(int id, String mac, String ssid){
        this.id = id;
        this.mac = mac;
        this.ssid = ssid;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getMac() {
        return mac;
    }

    public void setMac(@NonNull String mac) {
        this.mac = mac;
    }

    @NonNull
    public String getSsid() {
        return ssid;
    }

    public void setSsid(@NonNull String ssid) {
        this.ssid = ssid;
    }

    /*public Coordinate getC() {
        return c;
    }

    public void setC(Coordinate c) {
        this.c = c;
    }*/

    @Override
    public String toString() {
        return "AP{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", ssid='" + ssid + '\'' +
                '}';
    }
}
