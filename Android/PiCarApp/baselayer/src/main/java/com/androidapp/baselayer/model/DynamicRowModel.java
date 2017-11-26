package com.androidapp.baselayer.model;

import java.io.Serializable;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public class DynamicRowModel extends BaseModel implements Serializable {

    public  Class cellClazz;
    public  int cellLayoutID;

    public  Double rowId;
    public  Double rowSubId;
    public  Object metaData;
    public  Object rawData;
    public  String rowTitle;
    public  String rowSubTitle;
    public  String rowPlaceholder;

    // constructor
    public DynamicRowModel() {

    }

}
