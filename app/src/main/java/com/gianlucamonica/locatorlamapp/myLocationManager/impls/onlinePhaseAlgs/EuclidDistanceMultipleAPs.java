package com.gianlucamonica.locatorlamapp.myLocationManager.impls.onlinePhaseAlgs;

import android.util.Log;

import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.utils.AP_RSS;
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

    public int findAP(int idAP, int idGrid){
        for(int i = 0; i < offlineScans.size(); i++){
            if(offlineScans.get(i).getIdWifiAP() == idAP && offlineScans.get(i).getIdGrid() == idGrid){
                return i;
            }
        }
        return -1;
    }

    public int compute(){
        ArrayList<Double> rss_acc = new ArrayList<>(); // accumulatore dei valori rss
        ArrayList<Integer> off_index = new ArrayList<>(); // collezionatore degli indici dei riquadri

        int idSquareTmp;
        int uniqueSquaresNumber = 1;
        ArrayList<Integer> uniqueSquares = new ArrayList<>();
        uniqueSquares.add(offlineScans.get(0).getIdGrid());
        idSquareTmp = offlineScans.get(0).getIdGrid();

        /**
         * estrapolo dagli offline scans il numero di quadratini univoci e l'id ad essi associato
         */
        for(int k = 0; k < offlineScans.size(); k++){
            if(offlineScans.get(k).getIdGrid() != idSquareTmp){
                uniqueSquaresNumber++;
                uniqueSquares.add(offlineScans.get(k).getIdGrid());
                idSquareTmp = offlineScans.get(k).getIdGrid();
            }
        }

        Log.i("unique grids", "unique grids " + uniqueSquares.toString());
        Log.i("unique grids", "unique grids number" + uniqueSquaresNumber);
        Log.i("compute wifi","ap rss " + ap_rsses.toString());
        Log.i("compute wifi", "off scan ordinati " + offlineScans.toString() );

        int idAP;
        double offRss = 0;
        double acc = 0;

        for(int i = 0; i < uniqueSquares.size(); i++){ // scorro i quadratini univoci
            Log.i("compute wifi", "calcolo grid " + uniqueSquares.get(i));
            for (int j = 0; j < ap_rsses.size(); j++) { // scorro gli ap dai quali ho ricevuro un rss live
                idAP = ap_rsses.get(j).getIdAP();
                if (findAP(idAP, uniqueSquares.get(i)) != -1) { // se in offline scan è presente uno scan per questo idAP e questo quadratino
                    Log.i("compute wifi", "AP " + idAP + " per posizione " + uniqueSquares.get(i) + " trovato ");
                    offRss = offlineScans.get(findAP(idAP, uniqueSquares.get(i) )).getValue(); // recupero valore rss dell offline scan
                    acc += Math.pow((double) ap_rsses.get(j).getRss() - offRss, 2); // formuletta della distanza euclidea
                    Log.i("compute wifi", "rss online= " + ap_rsses.get(j).getRss() + " rss offline= " + offRss + " dell' AP " + idAP + " nel riquadro " + uniqueSquares.get(i));

                }
            }
            Log.i("compute wifi","totale per grid " + uniqueSquares.get(i) + " = " + acc);
            rss_acc.add(acc); // aggiungo la somma accumulata per ciascuna quadratino
            acc = 0;
            off_index.add(uniqueSquares.get(i));
        }

        Log.i("computw wifi","accumulatore \n" + rss_acc.toString());


      Double min = rss_acc.get(0);
        int index = 0;
        for (int i = 0; i < rss_acc.size() - 1; i++){
            if(rss_acc.get(i+1) < min){
                min = rss_acc.get(i+1);
                index = i + 1;
            }
        }

        Log.i("compute","Min " + min);
        Log.i("compute","Winner  " + off_index.get(index));

        return off_index.get(index);

    }
}
