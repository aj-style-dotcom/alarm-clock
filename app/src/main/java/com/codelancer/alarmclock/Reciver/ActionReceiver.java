package com.codelancer.alarmclock.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codelancer.alarmclock.Alarm.Notifier;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notifier.stopNotify();
    }
}