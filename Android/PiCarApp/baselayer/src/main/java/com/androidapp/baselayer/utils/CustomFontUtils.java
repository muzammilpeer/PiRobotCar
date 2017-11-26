package com.androidapp.baselayer.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.androidapp.baselayer.R;
import com.androidapp.baselayer.views.BaseTextView;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class CustomFontUtils {
    // region Constants
    private static final int HELVETICA__NEUE_NORMAL = 0;
    private static final int HELVETICA__NEUE_BOLD = 1;
    private static final int HELVETICA__NEUE_ITALIC = 2;
    private static final int HELVETICA__NEUE_MEDIUM = 3;

    // endregion
    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs, R.styleable.BaseTextViewStyle);

        try {
            int font = attributeArray.getInteger(R.styleable.BaseTextViewStyle_textFont, 0);
            Typeface customFont = getTypeface(context, font);
            customFontTextView.setTypeface(customFont);
        } finally {
            attributeArray.recycle();
        }
    }

    private static Typeface getTypeface(Context context, int font) {
        switch (font) {
            case HELVETICA__NEUE_NORMAL:
                return FontCache.getTypeface("helvetica_neue_light.ttf", context);
            case HELVETICA__NEUE_BOLD:
                return FontCache.getTypeface("helvetica_neue_bold.ttf", context);
            case HELVETICA__NEUE_ITALIC:
                return FontCache.getTypeface("helvetica_neue_it.ttf", context);
            case HELVETICA__NEUE_MEDIUM:
                return FontCache.getTypeface("helvetica_neue_medium.ttf", context);
            default:
                // no matching font found
                // return null so Android just uses the standard font (Roboto)
                return null;
        }
    }
}
