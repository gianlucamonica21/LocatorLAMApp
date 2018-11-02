package com.gianlucamonica.locatorlamapp.myLocationManager.utils.indoorParams;

import java.io.Serializable;

public class IndoorParams implements Serializable {

    private IndoorParamName name;
    private Object paramObject;

    public IndoorParams(IndoorParamName name, Object paramObject) {
        this.name = name;
        this.paramObject = paramObject;
    }

    public IndoorParamName getName() {
        return name;
    }

    public void setName(IndoorParamName name) {
        this.name = name;
    }

    public Object getParamObject() {
        return paramObject;
    }

    public void setParamObject(Object paramObject) {
        this.paramObject = paramObject;
    }

    @Override
    public String toString() {
        return "IndoorParams{" +
                "name='" + name + '\'' +
                ", paramObject=" + paramObject +
                '}';
    }
}
