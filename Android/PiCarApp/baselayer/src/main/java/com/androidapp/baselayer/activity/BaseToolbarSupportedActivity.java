package com.androidapp.baselayer.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidapp.baselayer.interfaces.TablayoutInstanceInterfaces;
import com.androidapp.baselayer.interfaces.ToolbarInstanceInterface;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public abstract class BaseToolbarSupportedActivity extends BaseActivity implements ToolbarInstanceInterface, TablayoutInstanceInterfaces {

    Toolbar toolbar = null;
    TabLayout tabLayout = null;
    View leftToolbarItem = null;
    View rightToolbarItem = null;

    @Override
    protected void initViews() {
        super.initViews();

        if (getTabLayoutId() != 0) {
            tabLayout = findViewById(getTabLayoutId());
        }
        if (getToolbarLayoutId() != 0) {
            toolbar = findViewById(getToolbarLayoutId());

            if (getToolbarLeftItemResourceId() != 0) {
                leftToolbarItem = findViewById(getToolbarLeftItemResourceId());
            }
            if (getToolbarRightItemResourceId() != 0) {
                rightToolbarItem = findViewById(getToolbarRightItemResourceId());
            }
        }
    }

    @Nullable
    @Override
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Nullable
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void showToolBar() {
        if (getToolbar() != null) {
            getToolbar().setVisibility(View.VISIBLE);
//            getSupportActionBar().show();
//            getSupportActionBar().setHideOffset(0);
        }
    }

    @Override
    public void hideToolBar() {
        if (getToolbar() != null) {
//            getSupportActionBar().hide();
            getToolbar().setVisibility(View.GONE);
        }
    }

    @Override
    public void showTablayout() {
        if (getTabLayout() != null) {
            getTabLayout().setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideTablayout() {
        if (getTabLayout() != null) {
            getTabLayout().setVisibility(View.GONE);
        }
    }


    @Override
    public void showLeftToolbarItem() {
        if (getLeftToolbarbarItem() != null) {
            getLeftToolbarbarItem().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLeftToolbarItem() {
        if (getLeftToolbarbarItem() != null) {
            getLeftToolbarbarItem().setVisibility(View.GONE);
        }
    }

    @Override
    public void showRightToolbarItem() {
        if (getRightToolbarbarItem() != null) {
            getRightToolbarbarItem().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRightToolbarItem() {
        if (getRightToolbarbarItem() != null) {
            getRightToolbarbarItem().setVisibility(View.GONE);
        }
    }


    @Nullable
    @Override
    public View getLeftToolbarbarItem() {
        return leftToolbarItem;
    }

    @Nullable
    @Override
    public View getRightToolbarbarItem() {
        return rightToolbarItem;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
