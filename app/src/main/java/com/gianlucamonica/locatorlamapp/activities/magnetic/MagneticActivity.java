package com.gianlucamonica.locatorlamapp.activities.magnetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.magnetic.offline.OfflineMagneticActivity;
import com.gianlucamonica.locatorlamapp.activities.magnetic.online.OnlineMagneticActivity;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;

public class MagneticActivity extends AppCompatActivity {

    private MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic);

        //myLocationManager = new MyLocationManager(AlgorithmName.MAGNETIC_FP, this, );
        MyApp.setMyLocationManagerInstance(myLocationManager);

    }

    public void openOfflineActivity(View v){
        Intent intent = new Intent(this, OfflineMagneticActivity.class);
        startActivity(intent);
    }

    public void openOnlineActivity(View v){
        Intent intent = new Intent(this, OnlineMagneticActivity.class);
        startActivity(intent);
    }
}
