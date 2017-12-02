package com.muzammilpeer.picarapp.cell;

import android.view.View;

import com.androidapp.baselayer.cell.BaseCell;
import com.androidapp.baselayer.views.BaseTextView;
import com.muzammilpeer.picarapp.R;

/**
 * Created by muzammilpeer on 29/11/2017.
 */

public class ChatMessageCell extends BaseCell {

    BaseTextView messageTextView;

    public ChatMessageCell(View itemView) {
        super(itemView);

        messageTextView = baseView.findViewById(R.id.messageTextView);
    }

    @Override
    public void updateCell(Object model) {
        mDataSource = model;
        if (model instanceof String) {
            String dataSource = (String) model;
            messageTextView.setText(model + "");
        }

    }
}
