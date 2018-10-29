package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;

@Entity(tableName = "scanSummary",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                childColumns = "idBuilding",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Algorithm.class,
                childColumns = "idAlgorithm",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = Config.class,
                childColumns = "idConfig",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)
        /*@ForeignKey(
                entity = BuildingFloor.class,
                childColumns = "idBuildingFloor",
                parentColumns = "id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)*/},
        indices = {@Index(value =
                {"idBuilding","idAlgorithm","idConfig","type","idWifiNetwork"}, unique = true)}
                // non posso comparire due righe aventi stesso building,algorithm,idConfig,idWifiNetwork type
)
public class ScanSummary {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    int idBuilding;

    int idBuildingFloor;

    @NonNull
    int idAlgorithm;

    @NonNull
    int idConfig;

    int idWifiNetwork;

    @NonNull
    String type;

    public ScanSummary(int idBuilding, int idBuildingFloor, int idAlgorithm, int idConfig, int idWifiNetwork, String type){
        this.idBuilding = idBuilding;
        this.idBuildingFloor = idBuildingFloor;
        this.idAlgorithm = idAlgorithm;
        this.idConfig = idConfig;
        this.idWifiNetwork = idWifiNetwork;
        this.type = type;
    }

    @Ignore
    public ScanSummary(int idBuilding, int idBuildingFloor, int idAlgorithm, int idConfig, String type){
        this.idBuilding = idBuilding;
        this.idBuildingFloor = idBuildingFloor;
        this.idAlgorithm = idAlgorithm;
        this.idConfig = idConfig;
        this.type = type;
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

    public int getIdBuildingFloor() {
        return idBuildingFloor;
    }

    public void setIdBuildingFloor(int idBuildingFloor) {
        this.idBuildingFloor = idBuildingFloor;
    }

    @NonNull
    public int getIdAlgorithm() {
        return idAlgorithm;
    }

    public void setIdAlgorithm(@NonNull int idAlgorithm) {
        this.idAlgorithm = idAlgorithm;
    }

    @NonNull
    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(@NonNull int idConfig) {
        this.idConfig = idConfig;
    }

    public int getIdWifiNetwork() {
        return idWifiNetwork;
    }

    public void setIdWifiNetwork(int idWifiNetwork) {
        this.idWifiNetwork = idWifiNetwork;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ScanSummary{" +
                "id=" + id +
                ", idBuilding=" + idBuilding +
                ", idBuildingFloor=" + idBuildingFloor +
                ", idAlgorithm=" + idAlgorithm +
                ", idConfig=" + idConfig +
                ", idWifiNetwork=" + idWifiNetwork +
                ", type='" + type + '\'' +
                '}';
    }
}
