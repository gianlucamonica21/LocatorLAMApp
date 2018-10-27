package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.utils.DateConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "offlineScan",
        indices = {@Index(value = {"idScan", "idGrid"},
                unique = true)}, // vincoli di unicità
        foreignKeys = {
        @ForeignKey( // chiave esterna
            entity = ScanSummary.class,
            childColumns = "idScan",
            parentColumns = "id",
            onUpdate = CASCADE,
            onDelete = CASCADE)}
        )
@TypeConverters(DateConverter.class)
public class OfflineScan {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idScan;

    @NonNull
    int idGrid;

    @NonNull
    double value;

    @NonNull
    Date timeStamp;

    public OfflineScan(@NonNull int idScan, @NonNull int idGrid, @NonNull double value, Date timeStamp) {
        this.idScan = idScan;
        this.idGrid = idGrid;
        this.value = value;
        this.timeStamp = timeStamp;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getIdScan() {
        return idScan;
    }

    public void setIdScan(@NonNull int idScan) {
        this.idScan = idScan;
    }

    @NonNull
    public int getIdGrid() {
        return idGrid;
    }

    public void setIdGrid(@NonNull int idGrid) {
        this.idGrid = idGrid;
    }


    @NonNull
    public double getValue() {
        return value;
    }

    public void setValue(@NonNull double value) {
        this.value = value;
    }

    @NonNull
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NonNull Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "OfflineScan{" +
                "id=" + id +
                ", idScan=" + idScan +
                ", idGrid=" + idGrid +
                ", value=" + value +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
