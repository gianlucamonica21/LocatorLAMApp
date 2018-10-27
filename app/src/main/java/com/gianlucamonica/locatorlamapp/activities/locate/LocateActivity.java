package com.gianlucamonica.locatorlamapp.activities.locate;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class LocateActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager; // fare tutto con MyLocMiddleware
    private DatabaseManager databaseManager;
    private IndoorParamsUtils indoorParamsUtils;
    private LocationMiddleware locationMiddleware;

    private EditText actualGrid;
    private EditText estimatedGrid;

    // loop
    final Handler handler = new Handler();
    final int delay = 5000; //milliseconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        MyApp.setActivity(this);
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        actualGrid = (EditText) findViewById(R.id.actualGridEditText);
        estimatedGrid = (EditText) findViewById(R.id.estimateGridEditText);
        estimatedGrid.setEnabled(false);
        // recupero parametri indoor
        Algorithm algorithm;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        algorithmName = AlgorithmName.valueOf(algorithm.getName());
        Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
        Config config = (Config) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.CONFIG);

        try{
            List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                    getScanSummaryByBuildingAlgorithm(building.getId(),algorithm.getId(),config.getId());
            final List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScansById(scanSummary.get(0).getId());
            Log.i("locate activity","scansummary " +scanSummary.toString());
            Log.i("locate activity","offlinescans " +offlineScans.toString());



            locationMiddleware = new LocationMiddleware(algorithmName,indoorParams);
            // setting algorithm in mylocationmanager
            //myLocationManager = new MyLocationManager(algorithmName, indoorParams);

            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            MapView mapView = new MapView(MyApp.getContext(), null,null, indoorParams, offlineScans);
            mLinearLayout.addView(mapView);

            actualGrid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    /*Thread one = new Thread() {
                        public void run() {
                            try {
                                System.out.println("Does it work?");

                                Thread.sleep(1000);

                                System.out.println("Nope, it doesnt...again.");
                            } catch(InterruptedException v) {
                                System.out.println(v);
                            }
                        }
                    };

                    one.start();*/
                    //prima scansione
                    //OnlineScan onlineScan = myLocationManager.locate();
                    OnlineScan onlineScan = locationMiddleware.locate();
                    Log.i("locate activity", "onlinescan " + onlineScan.toString());
                    estimatedGrid.setText(String.valueOf(onlineScan.getIdEstimatedPos()));
                    handler.removeCallbacks(runnable);
                    //todo inserire online scan in db

                    //successive altre scansioni
                    handler.postDelayed(runnable = new Runnable(){
                        public void run(){
                            //do something
                            //OnlineScan onlineScan = myLocationManager.locate();
                            OnlineScan onlineScan = locationMiddleware.locate();

                            estimatedGrid.setText(String.valueOf(onlineScan.getIdEstimatedPos()));
                            Log.i("locate activity", "onlinescan " + onlineScan.toString());
                            //todo inserire online scan in db
                            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

                            // setting the map view
                            MapView mapView = new MapView(MyApp.getContext(),
                                    String.valueOf(onlineScan.getIdEstimatedPos()), actualGrid.getText().toString()  , indoorParams, offlineScans);
                            mLinearLayout.addView(mapView);
                            handler.postDelayed(this, delay);
                        }
                    }, delay);
                }
            });




        }catch(Exception e){
            Log.e("error get scan",String.valueOf(e));
        }
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
