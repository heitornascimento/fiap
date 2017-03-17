package com.fiap.heitor.android.endpoint;

import com.fiap.heitor.android.model.AuthResponse;

import retrofit2.http.GET;

/**
 * Created by heitornascimento on 16/03/17.
 */

public interface FiapService {

    @GET("v2/58b9b1740f0000b614f09d2f")
    retrofit2.Call<AuthResponse> getPosts();

}

