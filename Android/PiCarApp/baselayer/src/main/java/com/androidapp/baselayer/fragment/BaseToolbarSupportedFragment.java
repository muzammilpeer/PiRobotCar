package com.androidapp.baselayer.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.androidapp.baselayer.R;
import com.androidapp.baselayer.activity.BaseToolbarSupportedActivity;

/**
 * Created by muzammilpeer on 27/11/2017.
 */

public abstract class BaseToolbarSupportedFragment extends BaseFragment {

    @Override
    public BaseToolbarSupportedActivity getBaseActivity() {
        return (BaseToolbarSupportedActivity) super.getBaseActivity();
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        getBaseActivity().showToolBar();
        getBaseActivity().hideRightToolbarItem();
        if (getBaseActivity().getLeftToolbarbarItem() instanceof ImageButton) {
            ImageButton leftImageButton = (ImageButton) getBaseActivity().getLeftToolbarbarItem();
            if (getBaseActivity().getFragmentsCount() == 1) {
                leftImageButton.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_menu_white_36dp));
            } else {
                leftImageButton.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_navigate_before_white_36dp));
            }

        }

    }
}
