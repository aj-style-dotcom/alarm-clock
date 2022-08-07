package com.codelancer.alarmclock;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codelancer.alarmclock.Alarm.Alarm;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.ViewModel;

public class CreateAlarm extends AppCompatActivity {
    private  Button createButton;
    private TimePicker timePicker;
    private TextView remainingTimeText;
    private EditText labelText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch repeatSwitch, vibrateSwitch, activeSwitch;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarm);

        getSupportActionBar().hide();

        initialize();

        createButton.setOnClickListener(v -> {
            String time = timePicker.getCurrentHour().toString()+":"+timePicker.getCurrentMinute().toString();

            AlarmModel alarmModel = new AlarmModel(time,
                    labelText.getText().toString(),
                    activeSwitch.isChecked(),
                    repeatSwitch.isChecked(),
                    vibrateSwitch.isChecked());
            viewModel.insert(alarmModel);
            if(activeSwitch.isChecked()) {
                Alarm alarm = new Alarm(getApplicationContext());
                alarm.setAlarm(alarmModel);
            }
            Toast.makeText(CreateAlarm.this, "Alarm created", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void initialize(){
        timePicker = findViewById(R.id.TimePicker);
        remainingTimeText = findViewById(R.id.remainingTimeText);
        createButton = findViewById(R.id.createButton);
        repeatSwitch = findViewById(R.id.Repeat);
        vibrateSwitch = findViewById(R.id.Vibrate);
        activeSwitch = findViewById(R.id.Active);
        labelText = findViewById(R.id.LabelText);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }
}