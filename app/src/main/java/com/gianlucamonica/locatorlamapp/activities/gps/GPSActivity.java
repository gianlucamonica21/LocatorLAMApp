package com.gianlucamonica.locatorlamapp.activities.gps;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.gps.fragments.MapsActivity;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class GPSActivity extends Activity implements OnMapReadyCallback {

    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LNG = "lng";

    private GoogleMap mMap;
    // location's stuff
    private MyLocationManager myLocationManager;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("GPSactivity","on create");
        setContentView(R.layout.activity_gps);

        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting location
                if (myLocationManager.getMyPermissionsManager().isGPSEnabled()) {

                    Location location = myLocationManager.locate();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(GPSActivity.this, MapsActivity.class);
                    intent.putExtra(EXTRA_LAT, longitude);
                    intent.putExtra(EXTRA_LNG, latitude);

                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("GPSactivity","on resume");
        //myLocationManager = new MyLocationManager(AlgorithmName.GPS,this, );

        if(!myLocationManager.getMyPermissionsManager().isGPSEnabled()){
            btn.setEnabled(false);
        }

        if(myLocationManager.getMyPermissionsManager().isGPSEnabled()){
            btn.setEnabled(true);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


}
