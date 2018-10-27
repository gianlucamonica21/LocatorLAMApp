package com.gianlucamonica.locatorlamapp.myLocationManager.utils.permissionsManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;

import static android.content.Context.LOCATION_SERVICE;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;
import static android.provider.Settings.ACTION_WIFI_SETTINGS;

public class MyPermissionsManager {

    private boolean isGPSEnabled;
    private boolean isWIFIEnabled;
    private LocationManager locationManager;
    private WifiManager wifiManager;
    private String providerMsg = "";
    private AlgorithmName algorithmName;

    public MyPermissionsManager(AlgorithmName algorithmName){

        this.algorithmName = algorithmName;

        switch (algorithmName){
            case GPS:

                locationManager = (LocationManager) MyApp.getContext()
                        .getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                break;
            case WIFI_RSS_FP:

                wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(MyApp.getContext().WIFI_SERVICE);
                isWIFIEnabled = wifiManager.isWifiEnabled();

                break;
        }
    }





    public void requestPermission(final String[] permissions){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(MyApp.getActivity(),
            permissions, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(MyApp.getContext(),
                                "Sorry, we need the this permission",
                                Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(
                                (Activity) MyApp.getActivity(),
                                permissions,200);

                    }
                });

    }

    public void turnOnServiceIfOff(){

        switch (algorithmName){
            case GPS:
                if(!isGPSEnabled){
                    showDialog(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                }

                break;
            case WIFI_RSS_FP:

                if (!isWIFIEnabled){
                    showDialog(Settings.ACTION_WIFI_SETTINGS);

                }

                break;
        }
    }

    public void showDialog(final String providerToEnable){


        switch (providerToEnable){
            case ACTION_LOCATION_SOURCE_SETTINGS:

                locationManager = (LocationManager) MyApp.getContext()
                        .getSystemService(LOCATION_SERVICE);
                providerMsg = "GPS";
                break;
            case ACTION_WIFI_SETTINGS:

                wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(MyApp.getContext().WIFI_SERVICE);
                providerMsg = "WIFI";
                break;
        }

        android.support.v7.app.AlertDialog.Builder alertDialog = buildDialog(providerToEnable);
        alertDialog.show();
        Log.i("show dialog","show dialog " + MyApp.getActivity());
    }

    public android.support.v7.app.AlertDialog.Builder buildDialog(final String providerToEnable){


        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(MyApp.getActivity());
        alertDialog.setTitle(providerMsg + " is not Enabled!");
        alertDialog.setMessage("Do you want to turn on " + providerMsg + "?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (providerToEnable){
                    case ACTION_LOCATION_SOURCE_SETTINGS:
                        Intent intent = new Intent(providerToEnable);
                        MyApp.getActivity().startActivity(intent);
                        //locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER,true);
                        break;
                    case ACTION_WIFI_SETTINGS:
                        wifiManager.setWifiEnabled(true);
                        break;
                }

            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                android.support.v7.app.AlertDialog.Builder alertDialog2 = new android.support.v7.app.AlertDialog.Builder(MyApp.getActivity());

                alertDialog2.setTitle("Info");

                alertDialog2.setMessage("You must turn on " + providerMsg + " to continue!");
                alertDialog2.show();
            }
        });

        return alertDialog;
    }

    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    public boolean isWIFIEnabled() {
        return isWIFIEnabled;
    }
}
