package com.codelancer.alarmclock.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.AlarmRepo;
import com.codelancer.alarmclock.Database.ViewModel;
import com.codelancer.alarmclock.R;

public class UpdateAlarm extends Service {
    public UpdateAlarm() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getBundleExtra(getString(R.string.bundle_name));
        AlarmModel alarmModel = (AlarmModel) bundle.getSerializable(getString(R.string.model_name));
        alarmModel.setActive(false);
        AlarmRepo alarmRepo = new AlarmRepo(getApplication());
        alarmRepo.update(alarmModel);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}