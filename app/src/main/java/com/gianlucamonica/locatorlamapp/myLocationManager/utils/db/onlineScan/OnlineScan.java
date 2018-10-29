package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.media.RemoteController;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "onlineScan",
        indices = {@Index(value = {"timeStamp"},
                unique = true)},
        foreignKeys = {
        @ForeignKey(
                entity = ScanSummary.class,
                childColumns = "idScan",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)}
)
@TypeConverters(DateConverter.class)
public class OnlineScan {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idScan;

    @NonNull
    int idEstimatedPos;

    @NonNull
    int idActualPos;

    @NonNull
    Date timeStamp;


    public OnlineScan(@NonNull int idScan, @NonNull int idEstimatedPos, @NonNull int idActualPos, @NonNull Date timeStamp) {
        this.idScan = idScan;
        this.idEstimatedPos = idEstimatedPos;
        this.idActualPos = idActualPos;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public int getIdEstimatedPos() {
        return idEstimatedPos;
    }

    public void setIdEstimatedPos(@NonNull int idEstimatedPos) {
        this.idEstimatedPos = idEstimatedPos;
    }

    @NonNull
    public int getIdActualPos() {
        return idActualPos;
    }

    public void setIdActualPos(@NonNull int idActualPos) {
        this.idActualPos = idActualPos;
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
        return "OnlineScan{" +
                "id=" + id +
                ", idScan=" + idScan +
                ", idEstimatedPos=" + idEstimatedPos +
                ", idActualPos=" + idActualPos +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
