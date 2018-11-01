package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.liveMeasurements;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "liveMeasurements",indices = {@Index(value =
        {"idAlgorithm","idAP","name"}, unique = true)})
public class LiveMeasurements {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idAlgorithm;

    @NonNull
    int idAP;

    @NonNull
    String name;

    @NonNull
    double value;

    public LiveMeasurements(@NonNull int idAlgorithm, int idAP, @NonNull String name, @NonNull double value) {
        this.idAlgorithm = idAlgorithm;
        this.name = name;
        this.idAP = idAP;
        this.value = value;
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
    public int getIdAP() {
        return idAP;
    }

    public void setIdAP(@NonNull int idAP) {
        this.idAP = idAP;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public double getValue() {
        return value;
    }

    public void setValue(@NonNull double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LiveMeasurements{" +
                "id=" + id +
                ", idAlgorithm=" + idAlgorithm +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
