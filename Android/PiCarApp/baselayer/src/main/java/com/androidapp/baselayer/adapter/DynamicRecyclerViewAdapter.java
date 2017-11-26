package com.androidapp.baselayer.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.model.DynamicRowModel;
import com.androidapp.baselayer.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzammilpeer on 01/11/2017.
 */

public class DynamicRecyclerViewAdapter extends Adapter<BaseCell> {

    // The items to display in your RecyclerView
    protected List<DynamicRowModel> mObjects;

    private int currentColumnWidth;

    public DynamicRecyclerViewAdapter() {
        super();
        this.mObjects = new ArrayList<DynamicRowModel>();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DynamicRecyclerViewAdapter(List<DynamicRowModel> items) {
        this.mObjects = items;
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        return  position;
    }


    @Override
    public BaseCell onCreateViewHolder(ViewGroup parent, int viewType) {

        DynamicRowModel model = this.mObjects.get(viewType);
        BaseCell viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflatedView = inflater.inflate(model.cellLayoutID, parent, false);
        viewHolder = (BaseCell) ReflectionUtil.instantiate(model.cellClazz, View.class, inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseCell holder, int position) {
        holder.setAdapter(this);
        holder.updateCell(getItem(position));
    }

    @Override
    public int getItemCount() {
        return this.mObjects.size();
    }

    //utils
    public DynamicRowModel getItem(int position) {
        return mObjects.get(position);
    }

    public int getCurrentColumnWidth() {
        return currentColumnWidth;
    }

    public void setCurrentColumnWidth(int currentColumnWidth) {
        this.currentColumnWidth = currentColumnWidth;
    }
}
