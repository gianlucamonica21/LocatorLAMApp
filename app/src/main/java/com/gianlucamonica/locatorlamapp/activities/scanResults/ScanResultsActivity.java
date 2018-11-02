package com.gianlucamonica.locatorlamapp.activities.scanResults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;

import java.util.ArrayList;
import java.util.List;

public class ScanResultsActivity extends AppCompatActivity {

    TableLayout tableLayout;
    ArrayList<IndoorParams> indoorParams;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_results);

        databaseManager = new DatabaseManager();
        List<OnlineScan> magnScans = databaseManager.getAppDatabase().getMyDAO().getOnlineScan(1);
        List<OnlineScan> wifiScans = databaseManager.getAppDatabase().getMyDAO().getOnlineScan(2);

        tableLayout = findViewById(R.id.table);

        int wifiSucc = 0;
        int magnSucc = 0;

        for(int i = 0; i < magnScans.size(); i++){
            if(magnScans.get(i).getIdActualPos() == magnScans.get(i).getIdEstimatedPos()){
                magnSucc++;
            }
        }

        for(int i = 0; i < wifiScans.size(); i++){
            if(wifiScans.get(i).getIdActualPos() == wifiScans.get(i).getIdEstimatedPos()){
                wifiSucc++;
            }
        }


        TextView m = findViewById(R.id.magnSuccTV);
        if(magnScans.size() != 0){
            m.setText(String.valueOf((magnSucc * 100)/ magnScans.size()));
        }else{
            m.setText("no scans");
        }

        TextView w = findViewById(R.id.wifiSuccTV);
        if(wifiScans.size() != 0){
            w.setText(String.valueOf((wifiSucc * 100)/ wifiScans.size()));
        }else{
            w.setText("no scans");
        }

        TextView mScans = findViewById(R.id.magnScansTV);
        mScans.setText(String.valueOf(magnScans.size()));

        TextView wScans = findViewById(R.id.wifiScansTV);
        wScans.setText(String.valueOf(wifiScans.size()));



    }
}
