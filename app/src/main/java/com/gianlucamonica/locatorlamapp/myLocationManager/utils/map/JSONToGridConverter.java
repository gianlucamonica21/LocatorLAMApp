package com.gianlucamonica.locatorlamapp.myLocationManager.utils.map;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONToGridConverter {

    public JSONToGridConverter() {
    }

    public ArrayList<Grid> convert(JSONObject config){

        ArrayList<Coordinate> coo = new ArrayList<Coordinate>();
        ArrayList<Grid> rects = new ArrayList<Grid>();
        ArrayList<String> rectsName = new ArrayList<String>();

        Iterator<String> iter = config.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                JSONArray value = (JSONArray) config.get(key);
                rectsName.add(key.toString());

                coo.add(new Coordinate((int) value.get(0),(int) value.get(1)));
                coo.add(new Coordinate((int) value.get(2),(int) value.get(3)));
                rects.add(new Grid(
                        new Coordinate((int) value.get(0),(int) value.get(1)),
                        new Coordinate((int) value.get(2),(int) value.get(3)),
                        key.toString()));
            } catch (JSONException e) {
                // Something went wrong!
            }
        }

        Log.i("Grid conv", rects.toString());
        return rects;
    }
}
