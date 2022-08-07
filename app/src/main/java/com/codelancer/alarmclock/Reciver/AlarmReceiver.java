package com.codelancer.alarmclock.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.codelancer.alarmclock.Alarm.Alarm;
import com.codelancer.alarmclock.Alarm.Notifier;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.ViewModel;
import com.codelancer.alarmclock.R;
import com.codelancer.alarmclock.Service.UpdateAlarm;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    AlarmModel alarmModel;
    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        Log.d("testing", "alarm ringing");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            // resetting all alarm
            resetAlarms(context);
        }else{
            //getting alarm data
            Bundle bundle = intent.getBundleExtra(context.getString(R.string.bundle_name));
            alarmModel = (AlarmModel) bundle.getSerializable(context.getString(R.string.model_name));
            // notifying alarm
            Notifier.notifyAlarm(context,  alarmModel);
            // setting alarm if repeated
            if(alarmModel.isRepeate()) new Alarm(context).setAlarm(alarmModel);
            // starting service
            startSer(context, alarmModel);
        }
    }
    public void resetAlarms(Context context){
        Alarm alarm = new Alarm(context);
        ViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ViewModel.class);
        List<AlarmModel> modelLiveData = viewModel.getAllAlarms().getValue();
        assert modelLiveData != null;
            for(AlarmModel alarmModel : modelLiveData) {
                if (alarmModel.isActive()) {
                    alarm.setAlarm(alarmModel);
                }
            }
    }

    public void startSer(Context context, AlarmModel alarmModel){
        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.model_name), alarmModel);
        Intent intent = new Intent(context, UpdateAlarm.class);
        intent.putExtra(context.getString(R.string.bundle_name), bundle);
        context.startService(intent);
    }
}