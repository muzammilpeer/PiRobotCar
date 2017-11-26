package com.androidapp.baselayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<BaseCell> {

    // The items to display in your RecyclerView
    protected List<Object> mObjects = new ArrayList<Object>();
    private Class cellClassName;
    private int cellLayoutId;
    public boolean isMultipleSelectionAllowed = false;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SimpleRecyclerViewAdapter(List<Object> items, Class clazz, int layoutId) {
        mObjects = items;
        cellClassName = clazz;
        cellLayoutId = layoutId;
    }


    @Override
    public BaseCell onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseCell viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflatedView = inflater.inflate(cellLayoutId, parent, false);
        viewHolder = (BaseCell) ReflectionUtil.instantiate(cellClassName, View.class, inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseCell holder, int position) {
        Object model = mObjects.get(position);
        holder.setAdapter(this);
        holder.updateCell(getItem(position));
    }

    @Override
    public int getItemCount() {
        return this.mObjects.size();
    }

    //utils
    public Object getItem(int position) {
        return mObjects.get(position);
    }

}
