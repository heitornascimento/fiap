package com.fiap.heitor.android.decorator;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.view.MainListActivity;

import java.util.zip.Inflater;

/**
 * Created by heitornascimento on 19/03/17.
 */

public class AboutApp implements Decorator {

    private AlertDialog mDialog;
    private Context mCtx;


    public AboutApp(Context context) {
        mCtx = context;
    }

    @Override
    public void decorate() {
        mDialog = new AlertDialog.Builder(mCtx).create();
        LayoutInflater inflater = ((MainListActivity) mCtx).getLayoutInflater();//(LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.about_app_layout, null);
        mDialog.setView(view);

        mDialog.show();


        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 1000);
    }
}
