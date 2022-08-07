package com.codelancer.alarmclock.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private AlarmRepo alarmRepo;
    private LiveData<List<AlarmModel>> listLiveData;

    public ViewModel(@NonNull Application application) {
        super(application);
        alarmRepo = new AlarmRepo(application);
        listLiveData=alarmRepo.getAllAlarms();
    }

    public LiveData<List<AlarmModel>> getAllAlarms(){
        return listLiveData;
    }

    public void insert(AlarmModel alarmModel) { alarmRepo.insert(alarmModel); }

    public void update(AlarmModel alarmModel) { alarmRepo.update(alarmModel); }

    public void delete(AlarmModel alarmModel) { alarmRepo.delete(alarmModel); }


}
