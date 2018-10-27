package com.gianlucamonica.locatorlamapp.activities.newFloor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.List;

public class InsertFloorActivity extends AppCompatActivity {

    private EditText floorName;
    private Button insertButton;
    private boolean valid = true; // for validation form
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_floor);

        floorName = (EditText) findViewById(R.id.nameEditText);
        insertButton = (Button) findViewById(R.id.insertButton);
        databaseManager = new DatabaseManager();

        insertButton.setOnClickListener(new View.OnClickListener() { // onclick button
            @Override
            public void onClick(View v) {
                String buildingName = getIntent().getStringExtra("name"); // recupero nome building da InsertBuildingActivity

                int idBuilding;
                // query per estrarre id del suddetto building
                try{
                    List<Building> buildings = databaseManager.getAppDatabase().getBuildingDAO().getBuildingWithName(buildingName);

                    if(buildings.size() > 0) {
                        idBuilding = buildings.get(0).getId();

                        if (floorName.getText().toString().equals("")) {
                            valid = false;
                        }

                        if (valid) {
                            //inserisco in db nuovo floor
                            String floorText = floorName.getText().toString();
                            Log.i("inserisco floor",floorText);
                            try{
                                databaseManager.getAppDatabase().getBuildingFloorDAO().insert(
                                        new BuildingFloor(idBuilding, floorText)
                                );
                                floorName.getText().clear();
                            }catch (Exception e){
                                Log.i("error insert new floor", e.toString());
                            }
                        }
                    }
                }
                catch (Exception e){
                    Log.e("error get/insert floor",String.valueOf(e));
                }


            }
        });
    }
}
