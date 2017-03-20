package com.fiap.heitor.android.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.Place;
import com.fiap.heitor.android.persistence.DAO;
import com.fiap.heitor.android.presenter.GeoCodePresenter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements GeoCodePresenter.GeoCallback {

    private GeoCodePresenter mGeoCodePresenter;
    @BindView(R.id.txtLocation)
    TextInputEditText mTextInputLocation;
    @BindView(R.id.location)
    TextView mLocation;

    private Place mSinglePlace;
    private String mIdPlace;

    public static final String PLACE_ID_PARAMS = "place_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mGeoCodePresenter = new GeoCodePresenter(this, this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateLocation(getIntent());
    }

    @OnClick(R.id.findLocation)
    public void findLocation() {
        String locationString = mTextInputLocation.getText().toString();
        if (locationString != null && !locationString.isEmpty()) {
            mGeoCodePresenter.findLocation(locationString);
        }
    }

    @OnClick(R.id.save)
    public void saveLocation() {
        try {
            if (mSinglePlace.getId() != null && !mSinglePlace.getId().isEmpty()) {
                String newName = mTextInputLocation.getEditableText().toString();
                mSinglePlace.setFeatureName(newName);
                DAO.getInstance(this).update(mSinglePlace);
            } else {
                DAO.getInstance(this).save(mSinglePlace);
            }
            Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        } catch (FiapDatabaseException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            //TODO
            e.printStackTrace();
        }

        finish();

    }

    private void populateLocation(Intent intent) {
        if (intent != null) {
            mIdPlace = intent.getStringExtra(PLACE_ID_PARAMS);
            if (mIdPlace != null && !mIdPlace.isEmpty()) {
                mSinglePlace = mGeoCodePresenter.findOne(mIdPlace);
                if (mSinglePlace != null) {
                    mTextInputLocation.setText(mSinglePlace.getFeatureName());
                    onSuccess(mSinglePlace);
                }
            }
        }
    }


    @Override
    public void onSuccess(Place place) {
        mSinglePlace = place;
        mLocation.setVisibility(View.VISIBLE);
        mLocation.setText("Latitude = " + place.getLatitude() +
                "Longitude = " + place.getLongitude());
    }

    @Override
    public void onFalied(String message) {
        mLocation.setVisibility(View.GONE);
    }
}
