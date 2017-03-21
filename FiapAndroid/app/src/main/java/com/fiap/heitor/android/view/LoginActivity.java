package com.fiap.heitor.android.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.decorator.ErrorLoginDialog;
import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.model.User;
import com.fiap.heitor.android.persistence.DAO;
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
            Intent intent = new Intent(this, MainListActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkFieldValid() {

        mNameValue = mName.getText().toString();

        if (mNameValue.isEmpty()) {
            mNameLayout.setError(getString(R.string.mandayory_field));
            return false;
        } else {
            mNameLayout.setError(null);
        }

        mPasswordValue = mPassword.getText().toString();

        if (mPasswordValue.isEmpty()) {
            mPasswordLayout.setError(getString(R.string.mandayory_field));
            return false;
        } else {
            mPasswordLayout.setError(null);
        }


        return true;
    }

    private boolean checkCredentials() {

        AuthResponse auth = DAO.getInstance(this).findOneUserSession();
        if (auth != null) {
            boolean isCredentialsValid = mNameValue.equals(auth.getmUser())
                    && mPasswordValue.equals(auth.getmPassword());

            if (isCredentialsValid) {
                User user = new User();
                user.setName(mNameValue);
                user.setPassword(mPasswordValue);
                try {
                    DAO.getInstance(this).saveUser(user);
                } catch (FiapDatabaseException e) {
                    e.printStackTrace();
                }
            } else {
                ErrorLoginDialog errorLoginDialog = new ErrorLoginDialog(this);
                errorLoginDialog.decorate();
            }

            return isCredentialsValid;
        }

        return false;
    }

}
