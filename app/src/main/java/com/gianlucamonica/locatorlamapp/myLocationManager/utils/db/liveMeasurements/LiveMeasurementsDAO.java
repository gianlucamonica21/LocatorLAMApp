package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.liveMeasurements;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;

import java.util.List;

@Dao
public abstract class LiveMeasurementsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LiveMeasurements... liveMeasurements);

    @Update
    public void update(LiveMeasurements ... liveMeasurements){};

    @Delete
    public void delete(LiveMeasurements ... liveMeasurements ){};

    @Query("DELETE FROM liveMeasurements")
    public void deleteAll(){};

    @Query("SELECT * FROM liveMeasurements")
    public abstract List<LiveMeasurements> getLiveMeasurements();

    @Query("SELECT * FROM liveMeasurements WHERE idAlgorithm = :idAlgorithm AND name = :name")
    public abstract List<LiveMeasurements> getLiveMeasurements(int idAlgorithm, String name);
}