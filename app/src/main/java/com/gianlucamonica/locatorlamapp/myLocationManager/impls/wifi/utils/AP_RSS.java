package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.utils;

public class AP_RSS {

    int idAP;
    int rss;

    public AP_RSS(int idAP, int rss) {
        this.idAP = idAP;
        this.rss = rss;
    }

    public int getIdAP() {
        return idAP;
    }

    public void setIdAP(int idAP) {
        this.idAP = idAP;
    }

    public int getRss() {
        return rss;
    }

    public void setRss(int rss) {
        this.rss = rss;
    }

    @Override
    public String toString() {
        return "AP_RSS{" +
                "idAP=" + idAP +
                ", rss=" + rss +
                '}';
    }
}
