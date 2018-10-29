package com.gianlucamonica.locatorlamapp.activities.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.main.MainActivity;
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
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.MapView;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    private ArrayList<IndoorParams> indoorParams;
    private MyLocationManager myLocationManager; // fare tutto con MyLocMiddleware
    private DatabaseManager databaseManager;
    private IndoorParamsUtils indoorParamsUtils;
    private LocationMiddleware locationMiddleware;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        MyApp.setActivity(this);

        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        Bundle bundle = getIntent().getExtras();
        indoorParams = (ArrayList<IndoorParams>) bundle.getSerializable("indoorParams");

        Algorithm algorithm;
        AlgorithmName algorithmName = AlgorithmName.MAGNETIC_FP;
        for (int i = 0; i < indoorParams.size(); i++){
            if (indoorParams.get(i).getName() == IndoorParamName.ALGORITHM){
                algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                algorithmName = AlgorithmName.valueOf(algorithm.getName());
            }
        }

        Log.i("scan act","alg name "+ algorithmName);

        // setting location middleware
        //locationMiddleware = new LocationMiddleware(algorithmName,indoorParams);

        // setting algorithm in mylocationmanager
        //myLocationManager = new MyLocationManager(algorithmName, indoorParams);
        myLocationManager = MyApp.getMyLocationManagerInstance();

        final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

        // setting the map view
        if(myLocationManager.getLocalizationAlgorithmInterface() != null){
            MapView v = (MapView) myLocationManager.build(MapView.class);
            //MapView v = (MapView) locationMiddleware.build(MapView.class);
            mLinearLayout.addView(v);
        }


        // handling redo scan
        Button redoButton = (Button) findViewById(R.id.redoButton);
        redoButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MyApp.getContext(),
                        "Deleting scan",
                        Toast.LENGTH_SHORT).show();

                // delete scan
                deleteScanFromDB();
                // refreshing the mapview
//                MapView mapView = (MapView) myLocationManager.build(MapView.class);
                if(myLocationManager.getLocalizationAlgorithmInterface() != null){
                    v = (MapView) myLocationManager.build(MapView.class);
                    //MapView v = (MapView) locationMiddleware.build(MapView.class);
                    mLinearLayout.addView(v);
                }
            }
        });

        // handling finish scan
        Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MyApp.getContext(),
                        "Scan finished",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ScanActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void deleteScanFromDB(){
        try{
            ScanSummaryDAO scanSummaryDAO = databaseManager.getAppDatabase().getScanSummaryDAO();
            Algorithm algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.ALGORITHM);
            Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);
            Config config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);

            Log.i("cancello", String.valueOf("a"+algorithm.getId() + "b" +  building.getId()+ "c" + config.getId() + " " + "OFFLINE"));
            scanSummaryDAO.deleteByBuildingAlgorithmConfig(building.getId(),algorithm.getId(),config.getId(),"offline");
        }catch (Exception e){
            Log.e("error get scan","error " +String.valueOf(e));
        }
    }
}
