package com.fiap.heitor.android.endpoint;

import com.fiap.heitor.android.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heitornascimento on 16/03/17.
 */

public class Injector {

    public static Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static FiapService provideRedditService() {
        return provideRetrofit(Constants.BASE_URL).create(FiapService.class);
    }
}
