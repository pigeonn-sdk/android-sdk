package io.pigeonn.pigeonnsdk;

import io.pigeonn.pigeonnsdk.utils.Logger;

public enum  Pigeonn {

    INSTANCE;

    public void init(){
        Logger.INSTANCE.log("Initialising Pigeonn Sdk");
    }

}
