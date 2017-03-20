package com.fiap.heitor.android.presenter;

import com.fiap.heitor.android.endpoint.FiapService;
import com.fiap.heitor.android.endpoint.Injector;
import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.session.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by heitornascimento on 16/03/17.
 */

public class FiapPresenter {


    private Callback mView;

    public FiapPresenter(Callback view) {
        this.mView = view;

    }

    public void getCredentials() {
        FiapService service = Injector.provideRedditService();
        service.getPosts().enqueue(new retrofit2.Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    mView.onSuccess(response.body());
                } else {
                    mView.onError();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                mView.onError();
            }
        });

    }


    public interface Callback {
        void onSuccess(AuthResponse response);

        void onError();
    }
}
