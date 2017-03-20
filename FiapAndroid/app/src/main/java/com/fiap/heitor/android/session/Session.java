package com.fiap.heitor.android.session;

import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.model.User;
import com.fiap.heitor.android.persistence.DAO;

/**
 * Created by heitornascimento on 16/03/17.
 */

public class Session {

    private User mUser;
    private AuthResponse mAuthResponse;

    private static Session instance;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }

        return instance;
    }


    private Session() {

    }

    public void setUserSession(String name) {
        User user = new User();
        mUser = user;
    }

    public void deleteSession() {
        mUser = null;
    }

    public void setAuth(AuthResponse auth) {
        mAuthResponse = auth;
    }

    public AuthResponse getAuth() {
        return mAuthResponse;
    }
}
