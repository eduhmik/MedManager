package com.example.eduh_mik.med_manager;

/**
 * Created by Mayore on 16/04/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            context.startService(new Intent(context,AlarmService.class));
        }
    }
}
