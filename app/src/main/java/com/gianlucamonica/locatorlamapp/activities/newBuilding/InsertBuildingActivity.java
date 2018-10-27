package com.gianlucamonica.locatorlamapp.activities.newBuilding;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.newFloor.InsertFloorActivity;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.BuildingDAO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class InsertBuildingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button insertButton;
    private EditText nameEditText;
    private EditText heightEditText;
    private EditText widthEditText;
    private EditText SOLatEditText;
    private EditText SOLngEditText;
    private EditText NELatEditText;
    private EditText NELngEditText;

    private DatabaseManager databaseManager;
    private MyLocationManager myLocationManager;
    private Location currentLocation;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("insBuildAct","on create");
        setContentView(R.layout.activity_insert_building);

        insertButton = (Button) findViewById(R.id.insertButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        widthEditText = (EditText) findViewById(R.id.widthEditText);
        SOLatEditText = (EditText) findViewById(R.id.SOLatEditText);
        SOLngEditText = (EditText) findViewById(R.id.SOLngEditText);
        NELatEditText = (EditText) findViewById(R.id.NELatEditText);
        NELngEditText = (EditText) findViewById(R.id.NELngEditText);

        databaseManager = new DatabaseManager();
        MyApp.setActivity(this);

        myLocationManager = new MyLocationManager(AlgorithmName.GPS, null);
        if (myLocationManager.getMyPermissionsManager().isGPSEnabled()) {
            currentLocation = myLocationManager.locate();
        }
        // todo fare in modo di non dover ricaricare l'activity per visualizzare la propria posizione sulla mappa (dopo aver attivato il gps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //  validation control list
        final ArrayList<EditText> editTextList = new ArrayList<>();
        editTextList.add(nameEditText);
        editTextList.add(heightEditText);
        editTextList.add(widthEditText);
        editTextList.add(SOLatEditText);
        editTextList.add(SOLngEditText);
        editTextList.add(NELatEditText);
        editTextList.add(NELngEditText);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                for (int i = 0; i < editTextList.size(); i++){
                    if(editTextList.get(i).getText().toString().equals("")){
                        valid = false;
                    }
                }

                if(valid){
                    try{
                        BuildingDAO buildingDAO = databaseManager.getAppDatabase().getBuildingDAO();
                        buildingDAO.insert(new Building(
                                nameEditText.getText().toString(),
                                Integer.parseInt(heightEditText.getText().toString()),
                                Integer.parseInt(widthEditText.getText().toString()),
                                Double.parseDouble(SOLatEditText.getText().toString()),
                                Double.parseDouble(SOLngEditText.getText().toString()),
                                Double.parseDouble(NELatEditText.getText().toString()),
                                Double.parseDouble(NELngEditText.getText().toString())
                        ));
                        Toast.makeText(MyApp.getContext(),"Building inserted!",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.e("error insert building",String.valueOf(e));
                    }

                    // todo caricare intent activity di inserimento floors
                    Intent intent = new Intent(InsertBuildingActivity.this,InsertFloorActivity.class);
                    intent.putExtra("name", nameEditText.getText().toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MyApp.getContext(),"All fields are required!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("insBuildAct","on resume");
        //myLocationManager = new MyLocationManager(AlgorithmName.GPS,this, null);
        if (myLocationManager.getMyPermissionsManager().isGPSEnabled()) {
            Log.i("insBuildAct","permission ok");
            currentLocation = myLocationManager.locate();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("insBuildAct","drawing");
        if(currentLocation != null) {
            Log.i("insBuildAct",currentLocation.toString());
            LatLng myLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLoc).title("You are here"));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, zoomLevel));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                int clickNum = 0;

                @Override
                public void onMapClick(LatLng latLng) {
                    clickNum++;
                    Log.i("clicked on map","clicked");

                    if(isOdd(clickNum)){
                        SOLatEditText.setText(String.valueOf(latLng.latitude));
                        SOLngEditText.setText(String.valueOf(latLng.longitude));
                    }else{
                        NELatEditText.setText(String.valueOf(latLng.latitude));
                        NELngEditText.setText(String.valueOf(latLng.longitude));
                    }
                }
            });
        }
    }

    boolean isOdd( int val ) { return (val & 0x01) != 0; }

}
