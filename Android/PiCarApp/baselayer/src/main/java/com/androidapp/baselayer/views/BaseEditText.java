package com.androidapp.baselayer.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.androidapp.baselayer.utils.CustomFontUtils;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class BaseEditText extends AppCompatEditText {
    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            CustomFontUtils.applyCustomFont(this, context, attrs);
        }

    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            CustomFontUtils.applyCustomFont(this, context, attrs);
        }

    }
}
