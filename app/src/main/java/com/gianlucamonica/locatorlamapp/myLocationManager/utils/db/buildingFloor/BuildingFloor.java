package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "buildingFloor",indices = {@Index(value =
        {"idBuilding","name"}, unique = true)},
        foreignKeys = {
            @ForeignKey( // chiave esterna
                    entity = Building.class,
                    childColumns = "idBuilding",
                    parentColumns = "id",
                    onUpdate = CASCADE,
                    onDelete = CASCADE)
        })
public class BuildingFloor implements Serializable {

    @PrimaryKey(autoGenerate =  true)
    int id;

    @NonNull
    int idBuilding;

    @NonNull
    String name;

    public BuildingFloor(@NonNull int idBuilding, @NonNull String name) {
        this.idBuilding = idBuilding;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(@NonNull int idBuilding) {
        this.idBuilding = idBuilding;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BuildingFloor{" +
                "id=" + id +
                ", idBuilding=" + idBuilding +
                ", name='" + name + '\'' +
                '}';
    }
}
