package com.codelancer.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codelancer.alarmclock.Alarm.Alarm;
import com.codelancer.alarmclock.Database.AlarmModel;
import com.codelancer.alarmclock.Database.ViewModel;

public class EditAlarm extends AppCompatActivity {

    private Button doneButton, deleteButton;
    private TimePicker timePicker;
    private EditText labelText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch repeatSwitch, vibrateSwitch, activeSwitch;
    private ViewModel viewModel;
    private AlarmModel alarmModel;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);

        getSupportActionBar().hide();

        initialize();

        setData();

        deleteButton.setOnClickListener(v -> deleteIt());

        doneButton.setOnClickListener(v ->{
            String time = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();

            AlarmModel alarmModelUpdate = new AlarmModel(time,
                    labelText.getText().toString(),
                    activeSwitch.isChecked(),
                    repeatSwitch.isChecked(),
                    vibrateSwitch.isChecked());
            alarmModelUpdate.setId(alarmModel.getId());
            // delete old alarm
            alarm.deleteAlarm(alarmModel);
            // set new alarm
            if(activeSwitch.isChecked()) alarm.setAlarm(alarmModelUpdate);
            viewModel.update(alarmModelUpdate);
            Toast.makeText(EditAlarm.this, "Alarm updated", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private void initialize() {
        timePicker = findViewById(R.id.editTimePicker);
        doneButton = findViewById(R.id.doneButton);
        deleteButton = findViewById(R.id.editDelete);
        repeatSwitch = findViewById(R.id.editRepeat);
        vibrateSwitch = findViewById(R.id.editVibrate);
        activeSwitch = findViewById(R.id.editActive);
        labelText = findViewById(R.id.editLabelText);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        Bundle bundle = getIntent().getBundleExtra(getString(R.string.bundle_name));
        alarmModel= (AlarmModel) bundle.getSerializable(getString(R.string.model_name));
        alarm = new Alarm(this);
    }

    private void setData(){
        timePicker.setCurrentHour(Integer.valueOf(alarmModel.getTime().split(":")[0]));
        timePicker.setCurrentMinute(Integer.valueOf(alarmModel.getTime().split(":")[1]));
        labelText.setText(alarmModel.getLabel());

        activeSwitch.setChecked(alarmModel.isActive());
        repeatSwitch.setChecked(alarmModel.isRepeate());
        vibrateSwitch.setChecked(alarmModel.isVibrate());
    }

    private void deleteIt(){
        viewModel.delete(alarmModel);

        alarm.deleteAlarm(alarmModel);
        Toast.makeText(EditAlarm.this, "Alarm deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}