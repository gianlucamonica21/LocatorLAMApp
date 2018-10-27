package com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic.db.magneticFingerPrint;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(tableName = "magneticFingerPrint",
        primaryKeys = {"gridName"})
public class MagneticFingerPrint {

    @NonNull
    private String gridName;
    @NonNull
    private double magnitude;

    @Ignore
    public MagneticFingerPrint(){}

    public MagneticFingerPrint(String gridName, double magnitude){
        this.gridName = gridName;
        this.magnitude = magnitude;
    }

    @NonNull
    public String getGridName() {
        return gridName;
    }

    public void setGridName(@NonNull String gridName) {
        this.gridName = gridName;
    }

    @NonNull
    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(@NonNull double magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return "MagneticFingerPrint{" +
                ", gridName='" + gridName + '\'' +
                ", magnitude=" + magnitude +
                '}';
    }
}
