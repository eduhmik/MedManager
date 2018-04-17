package com.example.eduh_mik.med_manager.application;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.example.eduh_mik.med_manager.AlarmService;

public class AppController extends MultiDexApplication {

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        startService(new Intent(this,AlarmService.class));
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "sensation_bold.ttf");
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

}