package com.codelancer.alarmclock.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.R;
import com.codelancer.alarmclock.Reciver.AlarmReceiver;

import java.util.Calendar;

public class Alarm {
    private final Context context;

    public Alarm(Context context) {
        this.context = context;
    }

    public void setAlarm(AlarmModel alarmModel){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.model_name), alarmModel);
        intent.putExtra(context.getString(R.string.bundle_name), bundle);

        int temp =Integer.parseInt(alarmModel.getTime().split(":")[0])*Integer.parseInt(alarmModel.getTime().split(":")[0]);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, temp, intent,  PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmModel.getTime().split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(alarmModel.getTime().split(":")[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            if(!alarmModel.isRepeate())
                Toast.makeText(context, "Postponing alarm for tomorrow", Toast.LENGTH_SHORT).show();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void deleteAlarm(AlarmModel alarmModel) {
        int temp =Integer.parseInt(alarmModel.getTime().split(":")[0])*Integer.parseInt(alarmModel.getTime().split(":")[0]);
        AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, temp, intent1, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager1.cancel(pendingIntent1);
    }
}

