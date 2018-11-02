package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;

import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WifiScanReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult, intent);
        asyncTask.execute();
    }

    private static class Task extends AsyncTask {

        private final PendingResult pendingResult;
        private final Intent intent;
        private WifiManager wifiManager;
        private DatabaseManager databaseManager;

        private Task(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
            databaseManager = new DatabaseManager();
            wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
            // faccio partire lo scan
            wifiManager.startScan();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> mScanResults = wifiManager.getScanResults();
                WifiInfo info = wifiManager.getConnectionInfo();
                String ssid = info.getSSID();
                ssid = ssid.toString().replace("\"", "");

                for (int i = 0; i < mScanResults.size(); i++) {
                    // inserisco scan in db solo per rete a cui si è connessi utilizzando l'info nowRect
                    if (mScanResults.get(i).SSID.toString().equals(ssid)) {
                        String BSSID = mScanResults.get(i).BSSID;
                        int level = mScanResults.get(i).level;

                        Log.i("background","level -> " + level);
                        // todo inserire in live measurements

                        int idAP = 0;
                        try {
                            idAP = databaseManager.getAppDatabase().getWifiAPDAO().getByBssid(BSSID).get(0).getId();
                            Log.i("background","idAP " + idAP);
                            databaseManager.getAppDatabase().getLiveMeasurementsDAO().insert(
                                    new LiveMeasurements(2,idAP , "wifi_rss", level)
                            );

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            return  null;
        }
    }
}