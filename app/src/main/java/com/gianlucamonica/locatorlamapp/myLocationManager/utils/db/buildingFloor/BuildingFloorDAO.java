package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;

import java.util.List;

@Dao
public abstract class BuildingFloorDAO {

    @Insert
    public abstract void insert(BuildingFloor... buildingFloors);

    @Update
    public void update(BuildingFloor ... buildingFloors){};

    @Delete
    public void delete(BuildingFloor ... buildingFloors){};

    @Query("DELETE FROM buildingFloor")
    public void deleteAll(){};

    @Query("SELECT * FROM buildingFloor")
    public abstract List<BuildingFloor> getAllBuildingFloors();

    @Query("SELECT * FROM buildingFloor WHERE idBuilding =:idBuilding")
    public abstract List<BuildingFloor> getBuildingsFloorsByIdBuilding(int idBuilding);

}
