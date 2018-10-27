package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "building",indices = {@Index(value =
        {"name"}, unique = true)})
public class Building implements Serializable{

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    String name;
    @NonNull
    int height;
    @NonNull
    int widht;
    @NonNull
    double SOLat;
    @NonNull
    double SOLng;
    @NonNull
    double NELat;
    @NonNull
    double NELng;


    public Building(String name, int height, int widht, double SOLat, double SOLng, double NELat, double NELng){
        this.id = id;
        this.name = name;
        this.height = height;
        this.widht = widht;
        this.SOLat = SOLat;
        this.SOLng = SOLng;
        this.NELat = NELat;
        this.NELng = NELng;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getHeight() {
        return height;
    }

    public void setHeight(@NonNull int height) {
        this.height = height;
    }

    @NonNull
    public int getWidht() {
        return widht;
    }

    public void setWidht(@NonNull int widht) {
        this.widht = widht;
    }

    @NonNull
    public double getSOLat() {
        return SOLat;
    }

    public void setSOLat(@NonNull double SOLat) {
        this.SOLat = SOLat;
    }

    @NonNull
    public double getSOLng() {
        return SOLng;
    }

    public void setSOLng(@NonNull double SOLng) {
        this.SOLng = SOLng;
    }

    @NonNull
    public double getNELat() {
        return NELat;
    }

    public void setNELat(@NonNull double NELat) {
        this.NELat = NELat;
    }

    @NonNull
    public double getNELng() {
        return NELng;
    }

    public void setNELng(@NonNull double NELng) {
        this.NELng = NELng;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", widht=" + widht +
                ", SOLat=" + SOLat +
                ", SOLng=" + SOLng +
                ", NELat=" + NELat +
                ", NELng=" + NELng +
                '}';
    }
}
