package com.androidapp.baselayer.cell;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidapp.baselayer.R;
import com.androidapp.baselayer.model.BaseModel;
import com.androidapp.baselayer.utils.ReflectionUtil;

/**
 * Created by muzammilpeer on 23/11/2017.
 */

public class BaseCellView extends RelativeLayout {

    //bCell is the reference of Your Cell object e.g (DrawerCell)
    public BaseCell baseCell;

    //View inflate class method
    public static BaseCellView inflate(ViewGroup parent, int cellLayoutId, Class cellClassName) {
        BaseCellView itemView = (BaseCellView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_base_view, parent, false);

        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(cellLayoutId, itemView, true);
        itemView.baseCell = (BaseCell) ReflectionUtil.instantiate(cellClassName, View.class, inflatedView);

        //Cell Reference and cell views setup is called to fetch views from xml
//        itemView.bCell = (BaseCell) ReflectionUtil.instantiate(cellClassName);
//        if (itemView.baseCell != null) {
//            itemView.baseCell.initializeViews(baseView);
//        }

        return itemView;
    }

    public void updateCell(BaseModel item) {
        if (baseCell != null) {
            baseCell.updateCell(item);
        }
    }


    public BaseCellView(Context context) {
        super(context);
    }

    public BaseCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
