package com.gianlucamonica.locatorlamapp.myLocationManager.utils;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.ArrayList;

public class IndoorParamsUtils {

    public Object getParamObject(ArrayList<IndoorParams> indoorParams, IndoorParamName indoorParamName){
        for(int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName().equals(indoorParamName)){
                switch (indoorParamName){
                    case BUILDING:
                        return (Building) indoorParams.get(i).getParamObject();
                    case ALGORITHM:
                        return (Algorithm) indoorParams.get(i).getParamObject();
                    case FLOOR:
                        return (BuildingFloor) indoorParams.get(i).getParamObject();
                    case SIZE:
                        return (int) indoorParams.get(i).getParamObject();
                    case CONFIG:
                        return (Config) indoorParams.get(i).getParamObject();
               }
            }
        }
        return null;
    }


    public void updateIndoorParams(ArrayList<IndoorParams> indoorParams, IndoorParamName tag, Object object){
        for (int i = 0; i < indoorParams.size(); i++){
            if(indoorParams.get(i).getName() == tag){
                indoorParams.set(i,new IndoorParams(tag,object));
                return;
            }
        }
        indoorParams.add(new IndoorParams(tag,object));
    }

}
