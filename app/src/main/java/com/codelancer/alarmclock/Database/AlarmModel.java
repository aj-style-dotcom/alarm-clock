package com.codelancer.alarmclock.Database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Alarm_Table")
public class AlarmModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "active")
    private boolean active;

    @ColumnInfo(name = "repeate")
    private boolean repeate;

    @ColumnInfo(name = "vibrate")
    private boolean vibrate;

    public AlarmModel(String time, String label, boolean active, boolean repeate, boolean vibrate) {
        this.time = time;
        this.active = active;
        this.repeate = repeate;
        this.vibrate = vibrate;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeate() {
        return repeate;
    }

    public void setRepeate(boolean repeate) {
        this.repeate = repeate;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}
