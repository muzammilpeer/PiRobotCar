package com.androidapp.baselayer.interfaces;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public interface ToolbarInterface {
    void showLeftToolbarItem();

    void hideLeftToolbarItem();

    void showRightToolbarItem();

    void hideRightToolbarItem();

    void showToolBar();

    void hideToolBar();

    @Nullable
    View getLeftToolbarbarItem();

    @Nullable
    View getRightToolbarbarItem();

    @Nullable
    Toolbar getToolbar();


}
