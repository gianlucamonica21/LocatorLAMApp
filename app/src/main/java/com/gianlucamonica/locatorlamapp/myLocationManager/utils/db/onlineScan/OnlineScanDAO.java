package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class OnlineScanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OnlineScan... onlineScans);

    @Update
    public void update(OnlineScan... onlineScans){};

    @Delete
    public void delete(OnlineScan... onlineScans){};

    @Query("DELETE FROM onlineScan")
    public void deleteAll(){};

    @Query("SELECT * FROM onlineScan")
    public abstract List<OnlineScan> getOnlineScans();

    @Query("SELECT * FROM onlineScan WHERE id = :id")
    public abstract List<OnlineScan>  getOnlineScansById(int id);

}
