package com.fiap.heitor.android.decorator;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fiap.heitor.android.R;

/**
 * Created by heitornascimento on 20/03/17.
 */

public class ErrorLoginDialog implements Decorator {

    private AlertDialog mDialog;
    private Context mCtx;

    public ErrorLoginDialog(Context context) {
        mCtx = context;
    }

    @Override
    public void decorate() {

        mDialog = new AlertDialog.Builder(mCtx).setNeutralButton(mCtx.getString(R.string.error_login_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        mDialog.setTitle(mCtx.getString(R.string.error_login_title));
        mDialog.setMessage(mCtx.getString(R.string.error_login_message));

        mDialog.show();

    }
}
