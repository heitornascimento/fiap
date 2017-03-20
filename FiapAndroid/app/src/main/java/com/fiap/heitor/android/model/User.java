package com.fiap.heitor.android.model;

/**
 * Created by heitornascimento on 16/03/17.
 */

public class User {

    private String id;
    private String name;
    private String mPassword;

    public User() {

    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
