package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;

import java.util.List;

@Dao
public abstract class AlgorithmDAO {

    @Insert
    public abstract void insert(Algorithm... algorithms);

    @Update
    public void update(Algorithm ... algorithms){};

    @Delete
    public void delete(Algorithm ... algorithms){};

    @Query("DELETE FROM algorithm")
    public void deleteAll(){};

    @Query("SELECT * FROM algorithm")
    public abstract List<Algorithm> getAlgorithms();

    @Query("SELECT name FROM algorithm")
    public abstract List<String> getAlgorithmsName();
}
