package com.codelancer.alarmclock.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codelancer.alarmclock.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = AlarmModel.class, version = 3)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
    private static volatile AlarmDatabase Instance;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    static AlarmDatabase getInstance(final Context context){
        if(Instance==null){
            synchronized (AlarmDatabase.class){
                if(Instance==null){
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, context.getString(R.string.db_name))
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return Instance;
    }
}
