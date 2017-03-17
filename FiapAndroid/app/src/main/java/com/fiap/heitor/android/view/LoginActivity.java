package com.fiap.heitor.android.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.session.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtName)
    TextInputEditText mName;
    @BindView(R.id.txtPassword)
    TextInputEditText mPassword;

    @BindView(R.id.txtInputNameLayout)
    TextInputLayout mNameLayout;
    @BindView(R.id.txtInputPasswordLayout)
    TextInputLayout mPasswordLayout;

    private String mNameValue;
    private String mPasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void login() {
        if (checkFieldValid() && checkCredentials()) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkFieldValid() {

        mNameValue = mName.getText().toString();

        if (mNameValue.isEmpty()) {
            mNameLayout.setError("Campo Obrigatório");
            return false;
        } else {
            mNameLayout.setError(null);
        }

        mPasswordValue = mPassword.getText().toString();

        if (mPasswordValue.isEmpty()) {
            mPasswordLayout.setError("Campo Obrigatório");
            return false;
        } else {
            mPasswordLayout.setError(null);
        }


        return true;
    }

    private boolean checkCredentials() {

        AuthResponse authResponse = Session.getInstance().getAuth();
        if (authResponse != null) {
            return mNameValue.equals(authResponse.getmUser()) && mPasswordValue.equals(authResponse.getmPassword());
        }

        return false;
    }

}
