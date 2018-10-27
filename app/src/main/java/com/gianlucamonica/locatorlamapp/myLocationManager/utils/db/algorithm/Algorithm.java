package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;

import java.io.Serializable;

@Entity(tableName = "algorithm",indices = {@Index(value =
        {"name"}, unique = true)})
public class Algorithm implements Serializable{

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    String name;
    @NonNull
    Boolean phases;

    public Algorithm(String name,Boolean phases){
        this.name = name;
        this.phases = phases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public Boolean getPhases() {
        return phases;
    }

    public void setPhases(@NonNull Boolean phases) {
        this.phases = phases;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
