package com.muzammilpeer.picarapp.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidapp.baselayer.adapter.SimpleRecyclerViewAdapter;
import com.androidapp.baselayer.fragment.BaseFragment;
import com.androidapp.baselayer.utils.Log4a;
import com.androidapp.baselayer.views.BaseButton;
import com.androidapp.baselayer.views.BaseEditText;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;
import com.muzammilpeer.picarapp.R;
import com.muzammilpeer.picarapp.cell.ChatMessageCell;
import com.muzammilpeer.picarapp.utils.BluetoothCommonConfiguration;
import com.muzammilpeer.picarapp.utils.RecyclerViewLayoutManger;

/**
 * Created by muzammilpeer on 29/11/2017.
 */

public class ChatClientFragment extends BaseFragment implements BluetoothService.OnBluetoothScanCallback, BluetoothService.OnBluetoothEventCallback, View.OnClickListener {

    BaseEditText chatEditText;
    BaseButton sendButton;
    BaseButton closeButton;
    RecyclerView chatHistoryRecyclerView;


    //Controls
    BaseButton leftButton, rightButton, forwardButton, reverseButton, stopButton;


    SimpleRecyclerViewAdapter chatHistoryRecyclerAdapter;


    BluetoothDevice currentDevice;
    BluetoothService bluetoothService;
    BluetoothWriter bluetoothWriter;


    public static ChatClientFragment newInstance(BluetoothDevice bluetoothDevice) {
        ChatClientFragment fragment = new ChatClientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedDevice", bluetoothDevice);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_client;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey("selectedDevice") == true) {
            currentDevice = getArguments().getParcelable("selectedDevice");

        }

        chatEditText = fragmentBaseView.findViewById(R.id.chatEditText);
        sendButton = fragmentBaseView.findViewById(R.id.sendButton);
        closeButton = fragmentBaseView.findViewById(R.id.closeButton);


        leftButton = fragmentBaseView.findViewById(R.id.leftButton);
        rightButton = fragmentBaseView.findViewById(R.id.rightButton);
        forwardButton = fragmentBaseView.findViewById(R.id.forwardButton);
        reverseButton = fragmentBaseView.findViewById(R.id.reverseButton);
        stopButton = fragmentBaseView.findViewById(R.id.stopButton);


        chatHistoryRecyclerView = fragmentBaseView.findViewById(R.id.chatHistoryRecyclerView);
    }

    @Override
    public void initObjects() {
        bluetoothService = BluetoothCommonConfiguration.getBluetoothService(getBaseActivity());
//        bluetoothService = BluetoothService.getDefaultInstance();
        bluetoothWriter = new BluetoothWriter(bluetoothService);
    }

    @Override
    public void initListenerOrAdapter() {

        bluetoothService.setOnScanCallback(this);
        bluetoothService.setOnEventCallback(this);
        bluetoothService.connect(currentDevice);

        chatHistoryRecyclerView.setLayoutManager(RecyclerViewLayoutManger.getVerticalLayoutManager(getBaseActivity()));
        chatHistoryRecyclerAdapter = new SimpleRecyclerViewAdapter(localDataSource, ChatMessageCell.class, R.layout.cell_chat_message);
        chatHistoryRecyclerView.setAdapter(chatHistoryRecyclerAdapter);


        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
        reverseButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothWriter.writeln(chatEditText.getText().toString() + "");
                chatEditText.setText("");
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothWriter.writeln("stopServer");
            }
        });

    }

    @Override
    public void initNetworkCalls() {

    }

    @Override
    public void onDataRead(byte[] buffer, int length) {
        localDataSource.add("< " + new String(buffer, 0, length) + "\n");
        chatHistoryRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusChange(BluetoothStatus bluetoothStatus) {
        Log4a.e("onStatusChange", bluetoothStatus.toString());

        if (bluetoothStatus == BluetoothStatus.CONNECTED) {
            bluetoothWriter.writeln(BluetoothCommonConfiguration.getPhoneBluetoothInformation());
        }


    }

    @Override
    public void onDeviceName(String s) {
        Log4a.e("onDeviceName", s);
    }

    @Override
    public void onToast(String s) {
        Log4a.e("onToast", s);

    }

    @Override
    public void onDataWrite(byte[] buffer) {
        localDataSource.add("> " + new String(buffer));

        chatHistoryRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceDiscovered(BluetoothDevice bluetoothDevice, int i) {

    }

    @Override
    public void onStartScan() {

    }

    @Override
    public void onStopScan() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.leftButton: {
                bluetoothWriter.writeln("6");
            }
            break;
            case R.id.rightButton: {
                bluetoothWriter.writeln("7");
            }
            break;
            case R.id.forwardButton: {
                bluetoothWriter.writeln("0");
            }
            break;
            case R.id.reverseButton: {
                bluetoothWriter.writeln("1");
            }
            break;
            case R.id.stopButton: {
                bluetoothWriter.writeln("8");
            }
            break;

        }

    }
}
