package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "wifiNetwork",indices = {@Index(value =
        {"ssid"}, unique = true)})
public class WifiNetwork {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String ssid;

    @NonNull
    String mac;

    public WifiNetwork(@NonNull String ssid, @NonNull String mac) {
        this.ssid = ssid;
        this.mac = mac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getSsid() {
        return ssid;
    }

    public void setSsid(@NonNull String ssid) {
        this.ssid = ssid;
    }

    @NonNull
    public String getMac() {
        return mac;
    }

    public void setMac(@NonNull String mac) {
        this.mac = mac;
    }


    @Override
    public String toString() {
        return "WifiNetwork{" +
                "id=" + id +
                ", ssid='" + ssid + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
