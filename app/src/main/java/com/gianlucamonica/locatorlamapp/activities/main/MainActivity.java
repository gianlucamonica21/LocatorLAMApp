package com.gianlucamonica.locatorlamapp.activities.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.fragments.algorithm.AlgorithmFragment;
import com.gianlucamonica.locatorlamapp.fragments.building.BuildingFragment;
import com.gianlucamonica.locatorlamapp.fragments.buttons.ButtonsFragment;
import com.gianlucamonica.locatorlamapp.fragments.floor.FloorFragment;
import com.gianlucamonica.locatorlamapp.fragments.param.MagnParamFragment;
import com.gianlucamonica.locatorlamapp.fragments.scan.ScanFragment;
import com.gianlucamonica.locatorlamapp.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.util.ArrayList;
import java.util.List;

import static com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName.MAGNETIC_FP;

public class MainActivity extends AppCompatActivity implements
        BuildingFragment.BuildingListener, // building
        AlgorithmFragment.OnFragmentInteractionListener, // algorithm
        ButtonsFragment.OnFragmentInteractionListener, // buttons
        FloorFragment.OnFragmentInteractionListener,// flor
        MagnParamFragment.OnFragmentInteractionListener, // magn param
        ScanFragment.OnFragmentInteractionListener{ // scan

    private ArrayList<IndoorParams> indoorParams; // contenitore indoor algorithm infos
    private IndoorParamsUtils indoorParamsUtils;

    private Algorithm chosenAlgorithm;
    private Building chosenBuilding;
    private BuildingFloor chosenFloor;
    private int chosenSize;
    private Config chosenConfig;

    private LocationMiddleware locationMiddleware;
    private DatabaseManager databaseManager;

    private FragmentTransaction ft;
    private boolean INDOOR_LOC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.setActivity(this);
        locationMiddleware = new LocationMiddleware(indoorParams);
        //locationMiddleware.init();

        // forzo indoor per testing
        locationMiddleware.setINDOOR_LOC(true);

        this.INDOOR_LOC = locationMiddleware.isINDOOR_LOC();
        Log.i("main","indoor loc " + this.INDOOR_LOC + "loc midd indoor loc " + locationMiddleware.isINDOOR_LOC());
        if(this.INDOOR_LOC){
            Toast.makeText(this,"You are indoor",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"You are outdoor",Toast.LENGTH_LONG).show();
        }
        MyApp.setLocationMiddlewareInstance(locationMiddleware);

        indoorParams = new ArrayList<>();
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        // adding fragments
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.algorithmLayout, new AlgorithmFragment(), new AlgorithmFragment().getTag());
        ft.replace(R.id.buildingLayout, new BuildingFragment(), new BuildingFragment().getTag());
        //ft.replace(R.id.floorLayout, new FloorFragment(), new FloorFragment().getTag());
        ft.replace(R.id.buttonsLayout, new ButtonsFragment(), new ButtonsFragment().getTag());
        //ft.replace(R.id.paramLayout, new MagnParamFragment(), new MagnParamFragment().getTag());
        //ft.replace(R.id.scanLayout, new ScanFragment(), new ScanFragment().getTag());
        ft.addToBackStack(null);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        DatabaseManager databaseManager= new DatabaseManager();
        /** OPERAZIONI DB DI TESTING*/
        // deleting building
        //databaseManager.getAppDatabase().getBuildingDAO().deleteById(11);
        // inserting building
        if(databaseManager.getAppDatabase().getBuildingDAO().getBuildings().size() == 0)
            databaseManager.getAppDatabase().getBuildingDAO().insert(new Building("Unione",5,5,123,123,123,123));
        // inserting algorithms
        if(databaseManager.getAppDatabase().getAlgorithmDAO().getAlgorithms().size() == 0){
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(MAGNETIC_FP),true));
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.WIFI_RSS_FP),true));
        }
        /*if(databaseManager.getAppDatabase().getConfigDAO().getAllConfigs().size() == 0){
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",1)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",2)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",3)
            );
        }*/
        // inserting onlineScan
        //databaseManager.getAppDatabase().getOnlineScanDAO().insert(new OnlineScan(2,0));
        // inserting offlineScan
        Log.i("offlineScan",databaseManager.getAppDatabase().getOfflineScanDAO().getOfflineScans().toString());
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(2,1,2.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,2,3.0));
        //databaseManager.getAppDatabase().getOfflineScanDAO().insert(new OfflineScan(1,3,4.0));
        // inserting Scan Summary
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,7,1,1,"offline"));
        //databaseManager.getAppDatabase().getScanSummaryDAO().insert(new ScanSummary(1,-1,1,2,"online"));
        //Log.i("deleting scan","");
        //databaseManager.getAppDatabase().getScanSummaryDAO().deleteByBuildingAlgorithmSize(1,1,1,"offline");
        //databaseManager.getAppDatabase().getScanSummaryDAO().deleteByBuildingAlgorithmSize(1,1,1,"online");
        // config isert
        /*databaseManager.getAppDatabase().getConfigDAO().insert(
                new Config(1,"sqSize",1)
        );
        databaseManager.getAppDatabase().getConfigDAO().insert(
                new Config(2,"sqSize",1)
        );*/

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("onFragmentInt",uri.toString());
    }

    @Override
    public void onFragmentInteraction(Object object, IndoorParamName tag) {
        if(object != null) {
            Log.i("onFragmentInt", object.toString());
        }
        switch (tag){
            case BUILDING:
                chosenBuilding = (Building) object;
                // recupero il primo building in db
                //chosenBuilding = databaseManager.getAppDatabase().getBuildingDAO().getBuildings().get(0);
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenBuilding); // populate indoor params

                /*FloorFragment floorFragment= (FloorFragment)
                        getSupportFragmentManager().findFragmentById(R.id.floorLayout);
                floorFragment.setFloorByBuilding(chosenBuilding);*/
                break;
            case FLOOR:
                chosenFloor = (BuildingFloor) object;
                if(chosenFloor != null){
                    indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenFloor); // populate indoor params
                }else{
                    chosenFloor = new BuildingFloor(-1,"Empty");
                    indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenFloor); // populate indoor params
                }
                break;
            case ALGORITHM:
                chosenAlgorithm = (Algorithm) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenAlgorithm); // populate indoor params
                // caricare fragment differente a seconda di chosenAlgorithm
                /*if( chosenAlgorithm.getName().equals(String.valueOf(MAGNETIC_FP))){
                    Log.i("alg scelto",chosenAlgorithm.getName());

                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.add(R.id.paramLayout, new MagnParamFragment(), new MagnParamFragment().getTag());
                    ft2.commit();

                }else{
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.paramLayout);
                    if( fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }*/
                break;
            case SIZE:
                chosenSize = (int) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenSize); // populate indoor params
                break;
            case CONFIG:
                chosenConfig = (Config) object;
                Log.i("config main ", String.valueOf(chosenConfig));
                indoorParamsUtils.updateIndoorParams(indoorParams,tag,chosenConfig);
                break;
            default:
        }


        /* modifiche progetto LAM */
        // recupero il primo building in db
        chosenBuilding = databaseManager.getAppDatabase().getBuildingDAO().getBuildings().get(0);
        indoorParamsUtils.updateIndoorParams(indoorParams,IndoorParamName.BUILDING, chosenBuilding); // populate indoor params

        List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(chosenBuilding.getId(),chosenAlgorithm.getId());
        boolean offlineScan = false;
        if(scanSummary.size() > 0){
            offlineScan = true;
        }

        BuildingFragment buildingFragment = (BuildingFragment)
                getSupportFragmentManager().findFragmentById(R.id.buildingLayout);
        buildingFragment.manageCheckBox(offlineScan);


        chosenConfig = databaseManager.getAppDatabase().getConfigDAO().getConfigByIdAlgorithm(chosenAlgorithm.getId()).get(0);
        indoorParamsUtils.updateIndoorParams(indoorParams,IndoorParamName.CONFIG, chosenConfig); // populate indoor params

        /**************************/



        Log.i("indoorParams",indoorParams.toString());

        // ********* passo parametri indoor a button frag *****************
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.loadIndoorParams(indoorParams);
        // ****************************************************************

        // ********* load info in dynamic fragment in paramLayout *********
        if(chosenAlgorithm != null){
            Log.i("chosenalg main",chosenAlgorithm.getName());
            if(chosenAlgorithm.getName().equals(String.valueOf(AlgorithmName.MAGNETIC_FP))){
                Log.i("chiamo load indPar","magn frag");
                /*MagnParamFragment magnParamFragment = (MagnParamFragment)
                        getSupportFragmentManager().findFragmentById(R.id.paramLayout);
                Log.i("fragm magn", getSupportFragmentManager().getFragments().toString());
                magnParamFragment.loadIndoorParams(indoorParams);*/
            }
        }
        // ****************************************************************

        if ( chosenConfig == null){
            buttonsFragment.manageScanButton(false);
            buttonsFragment.manageLocateButton(false);
        }else{
            buttonsFragment.manageScanButton(true);
            List<ScanSummary> scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().
                    getScanSummaryByBuildingAlgorithm(chosenBuilding.getId(),chosenAlgorithm.getId(),chosenConfig.getId());
            if(scanSummaries.size() > 0){
                buttonsFragment.manageLocateButton(true);
            }
        }
        /*
        if(INDOOR_LOC){

        }
        else{
            // OUTDOOR
            // todo aggiungere tutti i disable del caso
            //buttonsFragment.manageScanButton(false);
        }*/

    }

    @Override
    public void manageSpinner(boolean enable) {
        BuildingFragment buildingFragment = (BuildingFragment)
                getSupportFragmentManager().findFragmentById(R.id.buildingLayout);
        buildingFragment.manageSpinner(enable);
    }

    @Override
    public void onFragmentInteraction(Boolean isOfflineScan) {
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.manageLocateButton(isOfflineScan);
    }

}
