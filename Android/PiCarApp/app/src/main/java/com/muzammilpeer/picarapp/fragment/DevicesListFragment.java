package com.muzammilpeer.picarapp.fragment;


import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidapp.baselayer.adapter.SimpleRecyclerViewAdapter;
import com.androidapp.baselayer.fragment.BaseFragment;
import com.androidapp.baselayer.views.BaseButton;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.cell.DeviceCell;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class DevicesListFragment extends BaseFragment {


    private final int REQUEST_ACCESS_FINE_LOCATION_STATE = 1;


    SimpleRecyclerViewAdapter devicesListRecyclerAdapter;
    RecyclerView devicesListRecyclerView;
    BaseButton searchDevicesButton;


    private static final int REQ_BLUETOOTH_ENABLE = 1000;
    private static final int DEVICE_SCAN_MILLISECONDS = 10 * 1000;

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

    }

    @Override
    public void initListenerOrAdapter() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        devicesListRecyclerView.setLayoutManager(layoutManager);
        devicesListRecyclerAdapter = new SimpleRecyclerViewAdapter(localDataSource, DeviceCell.class, R.layout.cell_device);
        devicesListRecyclerView.setAdapter(devicesListRecyclerAdapter);


        BluetoothConfiguration config = new BluetoothConfiguration();
        config.context = getBaseActivity();
        config.bluetoothServiceClass = BluetoothClassicService.class; // BluetoothClassicService.class or BluetoothLeService.class
        config.bufferSize = 1024;
        config.characterDelimiter = '\n';
        config.deviceName = "Your App Name";
        config.callListenersInMainThread = true;

// Bluetooth Classic
        config.uuid = null;// UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Set null to find all devices on scan.

//// Bluetooth LE
//        config.uuidService = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
//        config.uuidCharacteristic = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");
//        config.transport = BluetoothDevice.TRANSPORT_LE; // Only for dual-mode devices

        showAccessLocationStatePermission();
        BluetoothService.init(config);
        BluetoothService service = BluetoothService.getDefaultInstance();
        searchDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback() {
                    @Override
                    public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
                        localDataSource.add(device);
                        devicesListRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onStartScan() {
                    }

                    @Override
                    public void onStopScan() {
                    }
                });

                service.startScan(); // See also service.stopScan();


            }
        });


    }


    @Override
    public void initNetworkCalls() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void showAccessLocationStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getBaseActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_ACCESS_FINE_LOCATION_STATE);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_ACCESS_FINE_LOCATION_STATE);
            }
        } else {
            Toast.makeText(getBaseActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(getBaseActivity(),
                new String[]{permissionName}, permissionRequestCode);
    }


}
