package com.muzammilpeer.picarapp.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

import java.util.UUID;

/**
 * Created by muzammilpeer on 29/11/2017.
 */

public class BluetoothCommonConfiguration {

    private static BluetoothService bluetoothService = null;

    public static BluetoothService getBluetoothService(Context context) {
        if (bluetoothService == null) {
            BluetoothConfiguration config = new BluetoothConfiguration();
            config.context = context;
            config.bluetoothServiceClass = BluetoothClassicService.class; // BluetoothClassicService.class or BluetoothLeService.class
            config.bufferSize = 1024;
            config.characterDelimiter = '\n';
            config.deviceName = "Android Client";
            config.callListenersInMainThread = true;

// Bluetooth Classic
            config.uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");  //UUID.fromString("btgoep://DCEE0679F5F4:7;authenticate=false;encrypt=false;master=false");
            // UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Set null to find all devices on scan.

//// Bluetooth LE
//        config.uuidService = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
//        config.uuidCharacteristic = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");
//        config.transport = BluetoothDevice.TRANSPORT_LE; // Only for dual-mode devices

            BluetoothService.init(config);
        }
        bluetoothService = BluetoothService.getDefaultInstance();
        return bluetoothService;
    }


    public static String getPhoneBluetoothInformation() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            ba = (BluetoothAdapter) getSystemService(Context.BLUETOOTH_SERVICE);
        return "Mac Address=" + bluetoothAdapter.getAddress() + " Name=" + bluetoothAdapter.getName();
    }
}
