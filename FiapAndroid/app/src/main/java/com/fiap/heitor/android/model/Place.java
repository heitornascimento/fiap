package com.fiap.heitor.android.model;

/**
 * Created by heitornascimento on 10/29/16.
 */

public class Place {

    private String id;
    private String mFeatureName;
    private double mLatitude;
    private double mLongitude;

    public Place(String featureName, double latitude, double longitude) {
        this.mFeatureName = featureName;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }


    public Place() {
    }

    public String getFeatureName() {
        return mFeatureName;
    }

    public void setFeatureName(String mFeatureName) {
        this.mFeatureName = mFeatureName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Place{" +
                "mFeatureName='" + mFeatureName + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }
}
