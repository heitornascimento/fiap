package com.fiap.heitor.android.decorator;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.view.MainListActivity;

/**
 * Created by heitornascimento on 19/03/17.
 */

public class DialogDecorator implements Decorator {

    private AlertDialog mDialog;
    private Context mCtx;
    private OnDeleteListener mOnDeleteListener;
    private String mId;

    public DialogDecorator(Context context, OnDeleteListener onDeleteListener, String id) {
        mCtx = context;
        mOnDeleteListener = onDeleteListener;
        mId = id;
    }

    @Override
    public void decorate() {

        mDialog = new AlertDialog.Builder(mCtx).setPositiveButton(mCtx.getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOnDeleteListener.deletePlace(mId);
                dialog.dismiss();
            }
        }).setNegativeButton(mCtx.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        mDialog.setTitle(mCtx.getString(R.string.title));
        mDialog.setMessage(mCtx.getString(R.string.message_confirmation));

        mDialog.show();

    }

    public interface OnDeleteListener {
        void deletePlace(String id);
    }
}
