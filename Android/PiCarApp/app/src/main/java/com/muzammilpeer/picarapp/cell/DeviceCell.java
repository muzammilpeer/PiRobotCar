package com.muzammilpeer.picarapp.cell;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.LinearLayout;

import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.views.BaseTextView;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.fragment.ChatClientFragment;

/**
 * Created by muzammilpeer on 27/11/2017.
 */

public class DeviceCell extends BaseCell implements View.OnClickListener {

    LinearLayout deviceLinearLayout;
    BaseTextView deviceNameTextView;
    BaseTextView deviceMacAddressTextView;

    public DeviceCell(View itemView) {
        super(itemView);

        deviceNameTextView = baseView.findViewById(R.id.deviceNameTextView);
        deviceMacAddressTextView = baseView.findViewById(R.id.deviceMacAddressTextView);
        deviceLinearLayout = baseView.findViewById(R.id.deviceLinearLayout);

        deviceLinearLayout.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (mDataSource instanceof BluetoothDevice) {
            BluetoothDevice dataSource = (BluetoothDevice) mDataSource;
            getBaseActivity().replaceFragmentWithiOSAnimation(ChatClientFragment.newInstance(dataSource), getBaseActivity().getFrameLayoutId());
        }
    }
}
