package com.gianlucamonica.locatorlamapp.activities.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.locate.LocateActivity;
import com.gianlucamonica.locatorlamapp.activities.scanResults.ScanResultsActivity;
import com.gianlucamonica.locatorlamapp.fragments.algorithm.AlgorithmFragment;
import com.gianlucamonica.locatorlamapp.fragments.building.BuildingFragment;
import com.gianlucamonica.locatorlamapp.fragments.buttons.ButtonsFragment;
import com.gianlucamonica.locatorlamapp.fragments.floor.FloorFragment;
import com.gianlucamonica.locatorlamapp.fragments.param.MagnParamFragment;
import com.gianlucamonica.locatorlamapp.fragments.scan.ScanFragment;
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
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.permissionsManager.MyPermissionsManager;

import java.util.ArrayList;
import java.util.List;

import static com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName.MAGNETIC_FP;
import static com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName.WIFI_RSS_FP;

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
    private Config chosenConfig;

    private DatabaseManager databaseManager;

    private FragmentTransaction ft;

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.setActivity(this);

        /* to enable WIFI */
        //myLocationManager = new MyLocationManager(AlgorithmName.WIFI_RSS_FP,null);

        indoorParams = new ArrayList<>();
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();

        /* adding fragments */
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.algorithmLayout, new AlgorithmFragment(), new AlgorithmFragment().getTag());
        ft.replace(R.id.buildingLayout, new BuildingFragment(), new BuildingFragment().getTag());
        ft.replace(R.id.buttonsLayout, new ButtonsFragment(), new ButtonsFragment().getTag());
        ft.addToBackStack(null);
        // Complete the changes added above
        ft.commit();

        DatabaseManager databaseManager= new DatabaseManager();
        /** OPERAZIONI DB DI TESTING*/
        // deleting building
        //databaseManager.getAppDatabase().getBuildingDAO().deleteById(11);
        // inserting building
        if(databaseManager.getAppDatabase().getBuildingDAO().getBuildings().size() == 0)
            databaseManager.getAppDatabase().getBuildingDAO().
                    insert(new Building("Unione",8,8,123,123,123,123));
        // inserting algorithms
        if(databaseManager.getAppDatabase().getAlgorithmDAO().getAlgorithms().size() == 0){
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(MAGNETIC_FP),true));
            databaseManager.getAppDatabase().getAlgorithmDAO().insert(new Algorithm(String.valueOf(AlgorithmName.WIFI_RSS_FP),true));
        }
        if(databaseManager.getAppDatabase().getConfigDAO().getAllConfigs().size() == 0){
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(1,"gridSize",1)
            );
            databaseManager.getAppDatabase().getConfigDAO().insert(
                    new Config(2,"gridSize",1)
            );
         }
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
            case ALGORITHM:
                chosenAlgorithm = (Algorithm) object;
                indoorParamsUtils.updateIndoorParams(indoorParams,tag, chosenAlgorithm); // populate indoor params
                myLocationManager = new MyLocationManager(AlgorithmName.valueOf(chosenAlgorithm.getName()),indoorParams);
                MyApp.setMyLocationManagerInstance(myLocationManager);
                break;
            default:
        }

        /* modifiche progetto LAM */
        // recupero il primo building in db
        chosenBuilding = databaseManager.getAppDatabase().getBuildingDAO().getBuildings().get(0);
        indoorParamsUtils.updateIndoorParams(indoorParams,IndoorParamName.BUILDING, chosenBuilding); // populate indoor params

        List<ScanSummary> scanSummary = databaseManager.getAppDatabase().getScanSummaryDAO().
                getScanSummaryByBuildingAlgorithm(chosenBuilding.getId(),chosenAlgorithm.getId());
        boolean offlineScan = false;
        if(scanSummary.size() > 0){
            offlineScan = true;
        }

        BuildingFragment buildingFragment = (BuildingFragment)
                getSupportFragmentManager().findFragmentById(R.id.buildingLayout);
        buildingFragment.manageCheckBox(offlineScan);

        chosenConfig = databaseManager.getAppDatabase().getConfigDAO().getConfigByIdAlgorithm(chosenAlgorithm.getId()).get(0);
        if(chosenConfig != null){
            buildingFragment.loadGridSize(chosenConfig.getParValue());
            indoorParamsUtils.updateIndoorParams(indoorParams,IndoorParamName.CONFIG, chosenConfig); // populate indoor params
        }


        Log.i("indoorParams",indoorParams.toString());

        // ********* passo parametri indoor a button frag *****************
        ButtonsFragment buttonsFragment = (ButtonsFragment)
                getSupportFragmentManager().findFragmentById(R.id.buttonsLayout);
        buttonsFragment.loadIndoorParams(indoorParams);
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
            }else {
                buttonsFragment.manageLocateButton(false);
            }
        }
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

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.scan_results:
                Intent intent = new Intent(this, ScanResultsActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("indoorParams", indoorParams);
                Log.i("buttonsFrag", indoorParams.toString());
                intent.putExtras(bundle);*/
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
