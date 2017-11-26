package com.muzammilpeer.picarapp.fragment;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.androidapp.baselayer.utils.Log4a;
import com.androidapp.baselayer.views.BaseButton;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.cell.DeviceCell;

import java.util.Set;

/**
 * Created by muzammilpeer on 26/11/2017.
 */

public class DevicesListFragment extends BaseFragment {

    SimpleRecyclerViewAdapter devicesListRecyclerAdapter;
    RecyclerView devicesListRecyclerView;
    BaseButton searchDevicesButton;

    private BluetoothAdapter mBtAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_devices_list;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        super.initViews(view, savedInstanceState);

        devicesListRecyclerView = fragmentBaseView.findViewById(R.id.devicesListRecyclerView);
        searchDevicesButton = fragmentBaseView.findViewById(R.id.searchDevicesButton);
    }

    @Override
    public void initObjects() {

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getBaseActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void initListenerOrAdapter() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        devicesListRecyclerView.setLayoutManager(layoutManager);
        devicesListRecyclerAdapter = new SimpleRecyclerViewAdapter(localDataSource, DeviceCell.class, R.layout.cell_device);
        devicesListRecyclerView.setAdapter(devicesListRecyclerAdapter);


        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                localDataSource.add(device);
            }
            devicesListRecyclerAdapter.notifyDataSetChanged();
        } else {
        }

        searchDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bluetooth is off, ask user to on it.
                if (!mBtAdapter.isEnabled()) {
                    Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableAdapter, REQUEST_ACCESS_FINE_LOCATION_STATE);
                }

                showAccessLocationStatePermission();

            }
        });


    }

    private final int REQUEST_ACCESS_FINE_LOCATION_STATE = 1;


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
            doDiscovery();
            Toast.makeText(getBaseActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void initNetworkCalls() {

    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        getBaseActivity().unregisterReceiver(mReceiver);
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log4a.e("device found", device.getAddress());
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (localDataSource.contains(device) == false) {
                        localDataSource.add(device);
                    }
                }
                devicesListRecyclerAdapter.notifyDataSetChanged();

                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                devicesListRecyclerAdapter.notifyDataSetChanged();
            }
        }
    };


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

                    doDiscovery();
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
