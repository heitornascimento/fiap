package com.fiap.heitor.android.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.presenter.FiapPresenter;
import com.fiap.heitor.android.R;

public class SplashScreen extends AppCompatActivity implements FiapPresenter.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadUserCredentials();
    }

    private void loadUserCredentials() {
        new FiapPresenter(this).getCredentials();
    }

    @Override
    public void onSuccess() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Could Load data", Toast.LENGTH_SHORT).show();
    }
}
