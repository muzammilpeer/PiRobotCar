package com.androidapp.baselayer.cell;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.androidapp.baselayer.activity.BaseActivity;
import com.androidapp.baselayer.model.BaseModel;

import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public abstract class BaseCell<T extends Object> extends RecyclerView.ViewHolder {

    protected T mDataSource;
    protected long position;

    //    protected DynamicRecyclerViewAdapter mAdapter;
    protected RecyclerView.Adapter mAdapter;

    public View baseView;


    public BaseCell(View itemView) {
        super(itemView);
        baseView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) itemView.getContext();
    }

    public abstract void updateCell(T model);

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }


    //support for baseadapter celll
    public void initializeViews(View view) {
        if (view != null) {
            //assign it to base cell view
            baseView = view;
            ButterKnife.bind(this, view);
        }
    }

}