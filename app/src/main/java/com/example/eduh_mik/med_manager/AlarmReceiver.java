package com.example.eduh_mik.med_manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.eduh_mik.med_manager.activities.MainActivity;
import com.example.eduh_mik.med_manager.models.Medicine;
import com.google.gson.Gson;

import java.util.Calendar;

/**
 * Created by Mayore on 16/04/2018.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    public static final String END_DATE_INTENT = "end date";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Medicine medicine  = new Gson().fromJson(intent.getExtras().getString(END_DATE_INTENT),Medicine.class);
        if(medicine.getEndDate().after(Calendar.getInstance().getTime())){
            playAlarmSound(context);
            showSmallNotification(context,medicine,intent);
        }else{
            cancelAlarm(context,medicine.getId());
        }

        wl.release();
    }

    public void setAlarm(Context context, Medicine medicine)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(END_DATE_INTENT,new Gson().toJson(medicine));
        PendingIntent pi = PendingIntent.getBroadcast(context, medicine.getId(), i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, medicine.getStartDate().getTime(), (24/medicine.getFrequency())*60*60*1000, pi); // Millisec * Second * Minute
        //am.setRepeating(AlarmManager.RTC_WAKEUP, medicine.getStartDate().getTime(), 360000, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context,int id)
    {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public void playAlarmSound(Context context) {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.getPackageName() + "/raw/reminder");
            Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showSmallNotification(Context context,Medicine medicine,Intent intent) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine("It is time to take your medicine");

        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.png_logo).setTicker(medicine.getName()).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(medicine.getName())
                .setContentIntent(resultPendingIntent)
                .setStyle(inboxStyle)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.png_logo))
                .setContentText("It is time to take your medicine")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(111, notification);
    }
}
