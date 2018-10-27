package com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic;

import android.app.Activity;
import android.view.View;

import com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic.offline.MagneticOfflineManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic.online.MagneticOnlineManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;

import java.util.ArrayList;

public class MagneticFieldAlgorithm implements LocalizationAlgorithmInterface {

    private MagneticOfflineManager magneticOfflineManager;
    private MagneticOnlineManager magneticOnlineManager;
    private Activity activity;

    private ArrayList<IndoorParams> indoorParams;

    public MagneticFieldAlgorithm(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
    }

    @Override
    public Object getBuildClass() {
        return null;
    }

    @Override
    public <T extends View> T build(Class<T> type)  {
        this.magneticOfflineManager = new MagneticOfflineManager(indoorParams);
        return magneticOfflineManager.build(type);
    }

    @Override
    public OnlineScan locate() {
        magneticOnlineManager = new MagneticOnlineManager(indoorParams);
        return magneticOnlineManager.locate();
    }

    @Override
    public void checkPermissions() {

    }


}
