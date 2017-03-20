package com.fiap.heitor.android.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.model.User;
import com.fiap.heitor.android.persistence.DAO;
import com.fiap.heitor.android.presenter.FiapPresenter;
import com.fiap.heitor.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity implements FiapPresenter.Callback {


    @BindView(R.id.launcher)
    ImageView mLauncher;
    @BindView(R.id.activity_splash_screen)
    RelativeLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        startAnimation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 500);

    }

    private void loadCredentials() {
        User user = DAO.getInstance(SplashScreen.this).findOneUser();
        if (user != null) {
            Intent intent = new Intent(SplashScreen.this, MainListActivity.class);
            startActivity(intent);
            finish();
        } else {
            loadUserCredentials();
        }
    }

    private void startAnimation() {
        Animation anim = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1500);
        mMainLayout.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadCredentials();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void loadUserCredentials() {
        new FiapPresenter(this).getCredentials();
    }

    @Override
    public void onSuccess(AuthResponse response) {
        try {
            DAO.getInstance(this).saveUserSession(response);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (FiapDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, getString(R.string.error_net), Toast.LENGTH_SHORT).show();
    }
}
