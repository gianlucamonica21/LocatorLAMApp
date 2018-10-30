package com.gianlucamonica.locatorlamapp.myLocationManager.impls;

import com.gianlucamonica.locatorlamapp.myLocationManager.AP_RSS;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;

import java.util.ArrayList;
import java.util.List;

public class EuclidDistanceMultipleAPs {

    private List<OfflineScan> offlineScans;
    private ArrayList<AP_RSS> ap_rsses;

    public EuclidDistanceMultipleAPs(List<OfflineScan> offlineScans, ArrayList<AP_RSS> ap_rsses) {
        this.offlineScans = offlineScans;
        this.ap_rsses = ap_rsses;
    }

    public int compute(){
        ArrayList<Double> rss_acc = new ArrayList<>();
        ArrayList<Integer> off_index = new ArrayList<>();

        for(int i = 0; i < offlineScans.size(); i++){
            for(int j = 0; j < ap_rsses.size(); j++){
                rss_acc.add((Double) (ap_rsses.get(j).getRss() - offlineScans.get(i).getValue()) );
                off_index.add(offlineScans.get(i).getIdGrid());
            }
        }

        Double min = rss_acc.get(0);
        int index = 0;
        for (int i = 0; i < rss_acc.size() - 1; i++){
            if(rss_acc.get(i+1) < rss_acc.get(i)){
                min = rss_acc.get(i+1);
                index = i + 1;
            }
        }

        return index;
    }
}
