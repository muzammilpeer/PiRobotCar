package com.muzammilpeer.picarapp.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

/**
 * Created by muzammilpeer on 29/11/2017.
 */

public class RecyclerViewLayoutManger {

    public static LinearLayoutManager getHorizontalLayoutManager(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        return layoutManager;
    }

    public static LinearLayoutManager getVerticalLayoutManager(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        return layoutManager;
    }
}
