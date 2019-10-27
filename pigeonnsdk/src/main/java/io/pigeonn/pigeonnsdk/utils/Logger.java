package io.pigeonn.pigeonnsdk.utils;

import android.util.Log;

public enum Logger {

    INSTANCE;

    public void log(String tag){
        Log.d("Pigeonn Sdk",tag);
    }

}
