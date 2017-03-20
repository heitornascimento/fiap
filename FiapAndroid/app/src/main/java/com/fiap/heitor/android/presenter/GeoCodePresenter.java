package com.fiap.heitor.android.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.fiap.heitor.android.model.Place;
import com.fiap.heitor.android.persistence.DAO;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by heitornascimento on 18/03/17.
 */

public class GeoCodePresenter implements GeoCodeFiap {

    private Context mContext;
    private static final int MAX_RESULTS = 10;
    private GeoCallback mView;

    public GeoCodePresenter(Context ctx, GeoCallback view) {
        this.mContext = ctx;
        mView = view;
    }


    @Override
    public void findLocation(String address) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addressList =
                    geocoder.getFromLocationName(address, MAX_RESULTS);
            if (addressList == null || addressList.size() == 0) {
                mView.onFalied("asdas");
                return;
            }
            mView.onSuccess(parserAddressToPlace(addressList.get(0)));
        } catch (IOException e) {
            mView.onFalied("aasda");
            e.printStackTrace();
        }

    }

    public Place findOne(String id) {
        Place place = DAO.getInstance(mContext).show(id);
        return place;
    }

    private Place parserAddressToPlace(Address address) {
        Place place = new Place();
        place.setFeatureName(address.getFeatureName());
        place.setLatitude(address.getLatitude());
        place.setLongitude(address.getLongitude());

        return place;
    }


    public interface GeoCallback {
        void onSuccess(Place place);

        void onFalied(String message);
    }

}

