package com.androidapp.baselayer.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.cell.BaseCellView;
import com.androidapp.baselayer.model.BaseModel;
import com.androidapp.baselayer.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzammilpeer on 23/11/2017.
 */

public class SimpleBaseAdapter extends BaseAdapter {

    protected List<BaseModel> mObjects = new ArrayList<BaseModel>();
    private Class cellClassName;
    private int cellLayoutId;


    public SimpleBaseAdapter(List<BaseModel> mObjects, Class cellClassName, int cellLayoutId) {
        this.mObjects = mObjects;
        this.cellClassName = cellClassName;
        this.cellLayoutId = cellLayoutId;
    }


    @Override
    public int getCount() {
        return this.mObjects.size();
    }

    @Override
    public BaseModel getItem(int i) {
        return mObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    // Spinner/DropDown getDropDownView() support
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // ListView and GridView  getView() support
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    //Inflate in both cases of getview,getdropdownview
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        BaseCellView itemView = (BaseCellView) convertView;
        if (null == itemView) {
            itemView = BaseCellView.inflate(parent, this.cellLayoutId, this.cellClassName);
        }

        if (itemView != null) {
            //updateCell
            itemView.updateCell(getItem(position));
        }
        return itemView;
    }
}