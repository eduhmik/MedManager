package com.example.eduh_mik.med_manager.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.lin_Google) LinearLayout linGoogle;
    @OnClick(R.id.lin_Google)
    public void onViewClicked() {
        startNewActivity(RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        linGoogle.setVisibility(sharedPrefs.getIsloggedIn() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPrefs.getIsloggedIn()) {
                    startNewActivity( MainActivity.class);
                    SplashActivity.this.finish();
                }
            }
        }, 3000);
    }


}
