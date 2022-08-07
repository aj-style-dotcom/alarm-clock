package com.codelancer.alarmclock.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepo {
    private final AlarmDao alarmDao;
    private final LiveData<List<AlarmModel>> listLiveData;

    public AlarmRepo(Application application){
        AlarmDatabase database = AlarmDatabase.getInstance(application);
        alarmDao = database.alarmDao();
        listLiveData = alarmDao.getAlarms();
    }

    LiveData<List<AlarmModel>> getAllAlarms(){
        return listLiveData;
    }

    void insert(AlarmModel alarmModel){
        AlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.insert(alarmModel));
    }

    public void update(AlarmModel alarmModel){
        AlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.update(alarmModel));
    }

    void delete(AlarmModel alarmModel){
        AlarmDatabase.databaseWriteExecutor.execute(() -> alarmDao.delete(alarmModel));
    }

}
