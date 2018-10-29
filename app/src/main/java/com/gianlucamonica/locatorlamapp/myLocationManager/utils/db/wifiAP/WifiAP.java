package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork.WifiNetwork;

@Entity(tableName = "wifiAP",indices = {@Index(value =
        {"idSsid","bssid"}, unique = true)}, foreignKeys = {
        @ForeignKey(
                entity = WifiNetwork.class,
                childColumns = "idSsid",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)})
public class WifiAP {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idSsid;

    @NonNull
    String bssid;

    public WifiAP(@NonNull int idSsid, @NonNull String bssid) {
        this.idSsid = idSsid;
        this.bssid = bssid;
    }

    @NonNull
    public int getIdSsid() {
        return idSsid;
    }

    public void setIdSsid(@NonNull int idSsid) {
        this.idSsid = idSsid;
    }

    @NonNull
    public String getBssid() {
        return bssid;
    }

    public void setBssid(@NonNull String bssid) {
        this.bssid = bssid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WifiAP{" +
                "id=" + id +
                ", idSsid=" + idSsid +
                ", bssid='" + bssid + '\'' +
                '}';
    }
}
