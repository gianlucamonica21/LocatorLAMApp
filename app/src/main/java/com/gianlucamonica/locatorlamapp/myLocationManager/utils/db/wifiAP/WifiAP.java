package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "wifiAP",indices = {@Index(value =
        {"ssid"}, unique = true)})
public class WifiAP {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String ssid;

    @NonNull
    String mac;

    public WifiAP(@NonNull String ssid, @NonNull String mac) {
        this.ssid = ssid;
        this.mac = mac;
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
        return "WifiAP{" +
                "id=" + id +
                ", ssid='" + ssid + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
