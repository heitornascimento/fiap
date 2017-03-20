package com.fiap.heitor.android.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 16/03/17.
 */

public class AuthResponse {


    @SerializedName("usuario")
    private String mUser;
    @SerializedName("senha")
    private String mPassword;

    private String mId;


    public AuthResponse() {

    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }
}
