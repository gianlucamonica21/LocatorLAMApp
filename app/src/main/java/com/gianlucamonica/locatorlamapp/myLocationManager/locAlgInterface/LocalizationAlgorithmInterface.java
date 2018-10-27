package com.gianlucamonica.locatorlamapp.myLocationManager.locAlgInterface;

import android.view.View;

public interface LocalizationAlgorithmInterface {

    Object getBuildClass();

    <T extends View> T build(Class<T> type);

    <T> T locate();

    void checkPermissions();

}
