package com.gianlucamonica.locatorlamapp.myLocationManager.impls;

import android.util.Log;

import com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic.db.magneticFingerPrint.MagneticFingerPrint;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;

import java.util.List;

public class EuclideanDistanceAlg {

    private List<WifiFingerPrint> wifiRadioMap;
    private List<OfflineScan> offlineScans;
    private int scannedRssi;
    private double magnitude;


    public EuclideanDistanceAlg(List<OfflineScan> offlineScans, double magnitude){
        this.offlineScans = offlineScans;
        this.magnitude = magnitude;
    }

    public int compute(AlgorithmName algorithmName){

        switch (algorithmName) {
            case WIFI_RSS_FP:
                return computeWifi();

            case MAGNETIC_FP:
                return computeMagn();
        }

        return -1;
    }

    public int computeWifi(){

        Log.i("euclidean","offline scans before" + offlineScans.toString());
        for (int i = 0; i < offlineScans.size(); i++) {
            double magnTmp = offlineScans.get(i).getValue();
            Log.i("euclidean","static "+String.valueOf(magnTmp));
            Log.i("euclidean","live" + magnitude);
            offlineScans.get(i).setValue(
                    Math.sqrt(
                            Math.pow((double) magnTmp - magnitude,2)
                    )
            );
        }

        Log.i("euclidean","offline scans after" + offlineScans.toString());

        int index = 0;
        double minMagn = offlineScans.get(0).getValue();
        for (int i = 0; i < offlineScans.size() - 1; i++) {
            if( offlineScans.get(i+1).getValue() < minMagn ){
                minMagn = offlineScans.get(i+1).getValue();
                index = i + 1;
            }
        }

        Log.i("euclidean","index estim pos " + index);
        Log.i("euclidean","gridname estim pos " + offlineScans.get(index).getIdGrid());
        return offlineScans.get(index).getIdGrid();
    }

    public int computeMagn(){


        Log.i("euclidean","offline scans before" + offlineScans.toString());
        for (int i = 0; i < offlineScans.size(); i++) {
            double magnTmp = offlineScans.get(i).getValue();
            Log.i("euclidean","static "+String.valueOf(magnTmp));
            Log.i("euclidean","live" + magnitude);
            offlineScans.get(i).setValue(
                    Math.sqrt(
                            Math.pow((double) magnTmp - magnitude,2)
                    )
            );
        }

        Log.i("euclidean","offline scans after" + offlineScans.toString());

        int index = 0;
        double minMagn = offlineScans.get(0).getValue();
        for (int i = 0; i < offlineScans.size() - 1; i++) {
            if( offlineScans.get(i+1).getValue() < minMagn ){
                minMagn = offlineScans.get(i+1).getValue();
                index = i + 1;
            }
        }

        Log.i("euclidean","index estim pos " + index);
        Log.i("euclidean","gridname estim pos " + offlineScans.get(index).getIdGrid());
        return offlineScans.get(index).getIdGrid();
    }

}
