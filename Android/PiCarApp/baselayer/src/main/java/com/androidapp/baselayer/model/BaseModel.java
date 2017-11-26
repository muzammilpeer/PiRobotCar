package com.androidapp.baselayer.model;

import java.io.Serializable;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public class BaseModel implements Serializable {

    public  Boolean isSelected = false;
    public  String sampleText = "";

    // constructor
    public BaseModel() {

    }

    public BaseModel(String sampleText) {
        this.sampleText = sampleText;
    }
}