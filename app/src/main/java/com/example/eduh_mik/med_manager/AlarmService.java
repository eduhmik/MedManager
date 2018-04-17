package com.example.eduh_mik.med_manager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.models.Medicine;

/**
 * Created by Mayore on 16/04/2018.
 */

public class AlarmService extends Service
{
    AlarmReceiver alarm = new AlarmReceiver();
    public void onCreate()
    {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        for(Medicine medicine: AppDatabase.getAppDatabase(this).medicineDao().getAll()){
            alarm.setAlarm(this,medicine);
        }
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
