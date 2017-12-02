package com.muzammilpeer.picarapp.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidapp.baselayer.adapter.SimpleRecyclerViewAdapter;
import com.androidapp.baselayer.fragment.BaseFragment;
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

public class ChatClientFragment extends BaseFragment implements BluetoothService.OnBluetoothEventCallback {

    BaseEditText chatEditText;
    BaseButton sendButton;
    RecyclerView chatHistoryRecyclerView;

    SimpleRecyclerViewAdapter chatHistoryRecyclerAdapter;


    BluetoothDevice currentDevice;
    BluetoothService bluetoothService;
    BluetoothWriter bluetoothWriter;


    public static ChatClientFragment newInstance(BluetoothDevice bluetoothDevice) {
        ChatClientFragment fragment = new ChatClientFragment();
        Bundle args = new Bundle();
        args.putParcelable("selectedDevice", bluetoothDevice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_client;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("selectedDevice") == true) {
            currentDevice = savedInstanceState.getParcelable("selectedDevice");
        }

        chatEditText = fragmentBaseView.findViewById(R.id.chatEditText);
        sendButton = fragmentBaseView.findViewById(R.id.sendButton);
        chatHistoryRecyclerView = fragmentBaseView.findViewById(R.id.chatHistoryRecyclerView);
    }

    @Override
    public void initObjects() {
        bluetoothService = BluetoothCommonConfiguration.getBluetoothService(getBaseActivity());
        bluetoothWriter = new BluetoothWriter(bluetoothService);
    }

    @Override
    public void initListenerOrAdapter() {

        bluetoothService.setOnEventCallback(this);

        chatHistoryRecyclerView.setLayoutManager(RecyclerViewLayoutManger.getVerticalLayoutManager(getBaseActivity()));
        chatHistoryRecyclerAdapter = new SimpleRecyclerViewAdapter(localDataSource, ChatMessageCell.class, R.layout.cell_chat_message);
        chatHistoryRecyclerView.setAdapter(chatHistoryRecyclerAdapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothWriter.writeln(chatEditText.getText().toString() + "");
                chatEditText.setText("");
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

    }

    @Override
    public void onDeviceName(String s) {

    }

    @Override
    public void onToast(String s) {

    }

    @Override
    public void onDataWrite(byte[] buffer) {
        localDataSource.add("> " + new String(buffer));
        chatHistoryRecyclerAdapter.notifyDataSetChanged();
    }
}