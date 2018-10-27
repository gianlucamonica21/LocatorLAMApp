package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;

import java.util.List;

@Dao
public interface MyDao {
    @Query("SELECT config.* FROM config "
            + "INNER JOIN scanSummary ON scanSummary.idConfig = config.id "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idAlgorithm = :idAlgorithm")
    public List<Config> findConfigByBuildingAndAlgorithm(int idBuilding, int idAlgorithm);

    @Query("SELECT offlineScan.* FROM offlineScan "
            + "INNER JOIN scanSummary ON scanSummary.id = offlineScan.idScan "
            + "WHERE scanSummary.idBuilding = :idBuilding AND scanSummary.idAlgorithm = :idAlgorithm AND scanSummary.idConfig = :idConfig")
    public List<OfflineScan> getOfflineScan(int idBuilding, int idAlgorithm, int idConfig);


}
