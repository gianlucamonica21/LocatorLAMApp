package com.gianlucamonica.locatorlamapp.myLocationManager.utils.map;

import android.content.Context;
import android.util.Log;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONReader {

    private JSONObject config;

    public JSONReader(String fileName){
        String s = readFromFile(MyApp.getContext(),fileName);
        try {
            config = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("LETTO ", s);
    }

    private String readFromFile(Context context,String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }
}
