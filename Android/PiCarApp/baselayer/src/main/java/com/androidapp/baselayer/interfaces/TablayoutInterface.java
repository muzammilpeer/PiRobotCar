package com.androidapp.baselayer.interfaces;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public interface TablayoutInterface {

    void showTablayout();

    void hideTablayout();

    @Nullable
    TabLayout getTabLayout();
}
