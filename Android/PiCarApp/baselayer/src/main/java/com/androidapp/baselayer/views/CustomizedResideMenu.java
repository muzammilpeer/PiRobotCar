package com.androidapp.baselayer.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;

import com.special.ResideMenu.ResideMenu;

/**
 * Created by muzammilpeer on 27/11/2017.
 */

public class CustomizedResideMenu extends ResideMenu {
    public CustomizedResideMenu(Context context) {
        super(context);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        // Applies the content insets to the view's padding, consuming that content (modifying the insets to be 0),
        // and returning true. This behavior is off by default and can be enabled through setFitsSystemWindows(boolean)
        // in api14+ devices.
        int bottomPadding = insets.bottom;

        if (Build.VERSION.SDK_INT >= 21) {
            Resources resources = getContext().getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                bottomPadding += resources.getDimensionPixelSize(resourceId);
            }
        }
        insets.bottom = bottomPadding;

        super.fitSystemWindows(insets);
        return true;
    }
}
