package com.androidapp.baselayer.interfaces;

import android.support.annotation.Nullable;

/**
 * Created by muzammilpeer on 27/11/2017.
 */

public interface ToolbarInstanceInterface extends ToolbarInterface {
    @Nullable
    int getToolbarLayoutId();

    @Nullable
    int getToolbarLeftItemResourceId();

    @Nullable
    int getToolbarRightItemResourceId();
}
