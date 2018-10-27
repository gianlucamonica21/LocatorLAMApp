package com.gianlucamonica.locatorlamapp.activities.wifi.onlineActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.MapView;

public class OnlineWIFIActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_wifi);
        myLocationManager = MyApp.getMyLocationManagerInstance();
    }

    public void locate(View view){
        WifiFingerPrint computedLocation = myLocationManager.locate();
        if(computedLocation != null){
            Toast.makeText(this,"your position: " + computedLocation.toString(),Toast.LENGTH_SHORT).show();
            final ViewGroup mLinearLayout = (ViewGroup) findViewById(R.id.constraintLayout);

            // setting the map view
            //MapView mapView = new MapView(this,computedLocation.getGridName());
            //mLinearLayout.addView(mapView);
        }
    }
}
