package com.gianlucamonica.locatorlamapp.activities.scanResults;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
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
        //List<OnlineScan> onlineScans = databaseManager.getAppDatabase().getMyDAO().getOnlineScan(1);

        tableLayout = findViewById(R.id.table);
        for (int i = 0; i < 1; i++)
        {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Algorithm");
            tableRow.addView(tableRow);

            textView.setText("Success %");
            tableRow.addView(tableRow);


            tableLayout.addView(tableRow);
        }
    }
}
