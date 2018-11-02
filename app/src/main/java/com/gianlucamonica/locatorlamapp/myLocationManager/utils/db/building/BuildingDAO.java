package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class BuildingDAO {

    @Insert
    public abstract void insert(Building... buildings);

    @Update
    public void update(Building ... buildings){};

    @Delete
    public void delete(Building ... buildings){};

    @Query("DELETE FROM building")
    public void deleteAll(){};

    @Query("DELETE FROM building WHERE id = :id")
    public abstract void deleteById(int id);

    @Query("SELECT * FROM building")
    public abstract List<Building> getBuildings();

    @Query("SELECT name FROM building")
    public abstract List<String> getBuildingsName();

    @Query("SELECT * FROM building WHERE name = :name")
    public abstract List<Building> getBuildingWithName(String name);
}
