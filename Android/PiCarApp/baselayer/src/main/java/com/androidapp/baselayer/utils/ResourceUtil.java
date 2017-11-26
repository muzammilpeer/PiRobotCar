package com.androidapp.baselayer.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by muzammilpeer on 05/11/2017.
 */

public class ResourceUtil {
    public static Drawable whiteDrawable(View itemView, int drawableID) {
        Drawable drawable = itemView.getResources().getDrawable(drawableID);
        drawable.setColorFilter(itemView.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
}
