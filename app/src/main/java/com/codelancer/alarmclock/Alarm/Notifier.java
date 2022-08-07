package com.codelancer.alarmclock.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.codelancer.alarmclock.AlarmScreen;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Helper;
import com.codelancer.alarmclock.R;
import com.codelancer.alarmclock.Reciver.ActionReceiver;

public class Notifier {

    private static Context context1;
    private static MediaPlayer mediaPlayer;
    private static Vibrator vibrator;
    private static int notifyID=0;
    private static NotificationManager notificationManager;

    public static void notifyAlarm(Context context, AlarmModel alarmModel) {
        context1 = context;
        vibrator = (Vibrator) context1.getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent1 = new Intent(context, AlarmScreen.class);
        Intent actionIntent = new Intent(context, ActionReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.model_name), alarmModel);
        intent1.putExtra(context.getString(R.string.bundle_name), bundle);

        PendingIntent alarmScreenPendingInt = PendingIntent.getActivity(context1, 0, intent1, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_ONE_SHOT);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, actionIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.channel_id));
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentText(Helper.getTimeString(alarmModel.getTime())[0]+" "+Helper.getTimeString(alarmModel.getTime())[1]);
        builder.setContentTitle("Alarm");
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setFullScreenIntent(alarmScreenPendingInt, true);
        builder.setContentIntent(alarmScreenPendingInt);
        builder.addAction(R.mipmap.ic_launcher_round, "Dismiss", actionPendingIntent);
        builder.build().flags = Notification.PRIORITY_HIGH;

        //turnOfAlarm(alarmModel);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(context1.getString(R.string.channel_id), context1.getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(context1.getString(R.string.channel_id));
        }

        notifyID = (int) System.currentTimeMillis();
        notificationManager.notify(notifyID, builder.build());
        startAlarmSound();
        if(alarmModel.isVibrate()) startVibrate();
    }

    private static void startAlarmSound(){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer=MediaPlayer.create(context1, alarmSound);
        mediaPlayer.setLooping(true);
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());
        mediaPlayer.start();
    }
    private static void  startVibrate(){
        vibrator.vibrate(new long[] {0, 100, 1000}, 0);
    }

    public static void stopNotify(){
        if(notifyID!=0){
            notificationManager.cancel(notifyID);
            mediaPlayer.stop();
            vibrator.cancel();
        }
    }
}

