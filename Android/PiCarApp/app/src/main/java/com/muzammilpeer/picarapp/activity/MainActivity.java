package com.muzammilpeer.picarapp.activity;

import com.androidapp.baselayer.activity.BaseActivity;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.fragment.DevicesListFragment;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getFrameLayoutId() {
        return R.id.dashboardFrameLayoutId;
    }

    @Override
    protected void initViews() {
        super.initViews();

        DevicesListFragment fragment = new DevicesListFragment();
        addFragment(fragment, getFrameLayoutId());
    }

    @Override
    protected void initObjects() {
        super.initObjects();
    }

    @Override
    protected void initListenerOrAdapter() {
        super.initListenerOrAdapter();
    }

    @Override
    protected void initNetworkCalls() {
        super.initNetworkCalls();
    }
}
