package com.muzammilpeer.picarapp.fragment;


import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidapp.baselayer.adapter.SimpleRecyclerViewAdapter;
import com.androidapp.baselayer.fragment.BaseFragment;
import com.androidapp.baselayer.utils.Log4a;
import com.androidapp.baselayer.views.BaseButton;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.cell.DeviceCell;
import com.muzammilpeer.picarapp.utils.BluetoothCommonConfiguration;
import com.muzammilpeer.picarapp.utils.RecyclerViewLayoutManger;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class DevicesListFragment extends BaseFragment implements BluetoothService.OnBluetoothScanCallback, BluetoothService.OnBluetoothEventCallback {


    SimpleRecyclerViewAdapter devicesListRecyclerAdapter;
    RecyclerView devicesListRecyclerView;
    BaseButton searchDevicesButton;

    BluetoothService bluetoothService;
    private boolean mScanning;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_devices_list;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {


        devicesListRecyclerView = fragmentBaseView.findViewById(R.id.devicesListRecyclerView);
        searchDevicesButton = fragmentBaseView.findViewById(R.id.searchDevicesButton);
    }

    @Override
    public void initObjects() {
        bluetoothService = BluetoothCommonConfiguration.getBluetoothService(getBaseActivity());
    }

    @Override
    public void initListenerOrAdapter() {
        devicesListRecyclerView.setLayoutManager(RecyclerViewLayoutManger.getVerticalLayoutManager(getBaseActivity()));
        devicesListRecyclerAdapter = new SimpleRecyclerViewAdapter(localDataSource, DeviceCell.class, R.layout.cell_device);
        devicesListRecyclerView.setAdapter(devicesListRecyclerAdapter);


        bluetoothService.setOnScanCallback(this);
        bluetoothService.setOnEventCallback(this);

        searchDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopScan();
            }
        });


    }


    @Override
    public void initNetworkCalls() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

//        bluetoothService.stopScan();
        mScanning = false;

//        bluetoothService.disconnect();
//        bluetoothService.setOnEventCallback(null);
//        bluetoothService.setOnScanCallback(null);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void startStopScan() {
        if (!mScanning) {
            bluetoothService.startScan();
        } else {
            bluetoothService.stopScan();
        }
    }

    @Override
    public void onDataRead(byte[] bytes, int i) {
        Log4a.d("onDataRead", "onDataRead");
    }

    @Override
    public void onStatusChange(BluetoothStatus bluetoothStatus) {
        Log4a.d("onStatusChange ", ":" + bluetoothStatus);
        Toast.makeText(getBaseActivity(), bluetoothStatus.toString(), Toast.LENGTH_SHORT).show();

        if (bluetoothStatus == BluetoothStatus.CONNECTED) {
            CharSequence colors[] = new CharSequence[]{"Try text", "Try picture"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
            builder.setTitle("Select");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
//                        startActivity(new Intent(MainActivity.this, DeviceActivity.class));
                    } else {
//                        startActivity(new Intent(MainActivity.this, BitmapActivity.class));
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    public void onDeviceName(String deviceName) {
        Log4a.d("onDeviceName", ": " + deviceName);

    }

    @Override
    public void onToast(String message) {
        Log4a.d("onToast", ":" + message);

    }

    @Override
    public void onDataWrite(byte[] bytes) {
        Log4a.d("onDataWrite", "onDataWrite");

    }

    @Override
    public void onDeviceDiscovered(BluetoothDevice bluetoothDevice, int i) {
        Log4a.e("onDeviceDiscovered", bluetoothDevice.getAddress());
        if (localDataSource.contains(bluetoothDevice) == false) {
            localDataSource.add(bluetoothDevice);
            devicesListRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStartScan() {
        mScanning = true;
    }

    @Override
    public void onStopScan() {
        mScanning = false;
    }


}
