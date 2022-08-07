package com.codelancer.alarmclock.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@androidx.room.Dao
public interface AlarmDao {
    @Insert
    void insert(AlarmModel alarmModel);

    @Update
    void update(AlarmModel alarmModel);

    @Delete
    void delete(AlarmModel alarmModel);

    @Query("select * from Alarm_Table")
    LiveData<List<AlarmModel>> getAlarms();
}
