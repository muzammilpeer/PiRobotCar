package com.muzammilpeer.picarapp.cell;

import android.bluetooth.BluetoothDevice;
import android.view.View;

import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.views.BaseTextView;
import com.muzammilpeer.picarapp.R;

/**
 * Created by muzammilpeer on 27/11/2017.
 */

public class DeviceCell extends BaseCell {

    BaseTextView deviceNameTextView;
    BaseTextView deviceMacAddressTextView;

    public DeviceCell(View itemView) {
        super(itemView);

        deviceNameTextView = baseView.findViewById(R.id.deviceNameTextView);
        deviceMacAddressTextView = baseView.findViewById(R.id.deviceMacAddressTextView);
    }

    @Override
    public void updateCell(Object model) {
        mDataSource = model;
        if (model instanceof BluetoothDevice) {
            BluetoothDevice dataSource = (BluetoothDevice) model;
            deviceNameTextView.setText(dataSource.getName() + "");
            deviceMacAddressTextView.setText(dataSource.getAddress() + "");

        }

    }
}
