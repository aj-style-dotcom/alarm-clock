package com.codelancer.alarmclock;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.codelancer.alarmclock.Alarm.Notifier;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.ViewModel;

public class AlarmScreen extends AppCompatActivity {
    TextView time, label, amp;
    ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        }

        time=findViewById(R.id.ringTimeText);
        label=findViewById(R.id.ringLabelText);
        amp=findViewById(R.id.ampmText);

        Bundle bundle = getIntent().getBundleExtra(getString(R.string.bundle_name));
        AlarmModel alarmModel = (AlarmModel) bundle.getSerializable(getString(R.string.model_name));
        turnOfAlarm(alarmModel);
        time.setText(Helper.getTimeString(alarmModel.getTime())[0]);
        amp.setText(Helper.getTimeString(alarmModel.getTime())[1]);
        label.setText(alarmModel.getLabel());
        findViewById(R.id.stopButton).setOnClickListener(v->{
            Notifier.stopNotify();
            finish();
        });
    }

    private void turnOfAlarm(@NonNull AlarmModel alarmModel) {
        if (!alarmModel.isRepeate()){
            viewModel = new ViewModelProvider(this).get(ViewModel.class);
            alarmModel.setActive(false);
            viewModel.update(alarmModel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false);
            setTurnScreenOn(false);
        } else {
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        }
    }
}