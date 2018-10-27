package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class ScanSummaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ScanSummary... scanSummaries);

    @Update
    public void update(ScanSummary... scanSummaries){};

    @Delete
    public void delete(ScanSummary... scanSummaries){};

    @Query("DELETE FROM scanSummary")
    public abstract void deleteAll();

    @Query("DELETE FROM scanSummary WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm AND idConfig = :idConfig AND type = :type")
    public abstract void deleteByBuildingAlgorithmConfig(int idBuilding, int idAlgorithm, int idConfig,String type);

    @Query("SELECT * FROM scanSummary")
    public abstract List<ScanSummary> getScanSummary();

    @Query("SELECT * FROM scanSummary WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm ")
    public abstract List<ScanSummary>  getScanSummaryByBuildingAlgorithm(int idBuilding, int idAlgorithm);

    @Query("SELECT * FROM scanSummary WHERE idBuilding = :idBuilding AND idAlgorithm = :idAlgorithm AND idConfig = :idConfig")
    public abstract List<ScanSummary>  getScanSummaryByBuildingAlgorithm(int idBuilding, int idAlgorithm, int idConfig);

}
