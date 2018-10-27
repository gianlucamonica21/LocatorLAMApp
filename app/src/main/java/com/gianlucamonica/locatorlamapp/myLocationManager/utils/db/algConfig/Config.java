package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "config",indices = {@Index(value =
        {"idAlgorithm","parName","parValue"}, unique = true)},
        foreignKeys = {
            @ForeignKey( // chiave esterna
                    entity = Algorithm.class,
                    childColumns = "idAlgorithm",
                    parentColumns = "id",
                    onUpdate = CASCADE,
                    onDelete = CASCADE)
        })
public class Config implements Serializable{

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idAlgorithm;

    @NonNull
    String parName;

    @NonNull
    int parValue;

    public Config(@NonNull int idAlgorithm, @NonNull String parName, @NonNull int parValue) {
        this.idAlgorithm = idAlgorithm;
        this.parName = parName;
        this.parValue = parValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getIdAlgorithm() {
        return idAlgorithm;
    }

    public void setIdAlgorithm(@NonNull int idAlgorithm) {
        this.idAlgorithm = idAlgorithm;
    }

    @NonNull
    public String getParName() {
        return parName;
    }

    public void setParName(@NonNull String parName) {
        this.parName = parName;
    }

    @NonNull
    public int getParValue() {
        return parValue;
    }

    public void setParValue(@NonNull int parValue) {
        this.parValue = parValue;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", idAlgorithm=" + idAlgorithm +
                ", parName='" + parName + '\'' +
                ", parValue='" + parValue + '\'' +
                '}';
    }
}
