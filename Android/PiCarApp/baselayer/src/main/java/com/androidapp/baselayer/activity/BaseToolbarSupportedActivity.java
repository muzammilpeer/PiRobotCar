package com.androidapp.baselayer.activity;

import android.view.View;

import com.androidapp.baselayer.interfaces.ToolbarInterface;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public abstract class BaseToolbarSupportedActivity extends BaseActivity {

    @Override
    public void showToolBar() {
        super.showToolBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setHideOffset(0);
        }
    }

    @Override
    public void hideToolBar() {
        super.hideToolBar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void showTablayout() {
        super.showTablayout();
        if (getTabLayout() != null) {
            getTabLayout().setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideTablayout() {
        super.hideTablayout();
        if (getTabLayout() != null) {
            getTabLayout().setVisibility(View.GONE);
        }
    }


}
