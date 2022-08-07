package com.codelancer.alarmclock;

public class Helper {
    public static String[] getTimeString(String string){
        String[] time_div = new String[2];
        int hr = Integer.parseInt(string.split(":")[0]);
        int mn = Integer.parseInt(string.split(":")[1]);
        if(hr>12){
            hr = hr-12;
            time_div[1] = "PM";
        }else time_div[1] = "AM";

        if (hr<10) time_div[0] = "0" + hr;
        else time_div[0] = String.valueOf(hr);

        if(mn<10) time_div[0] = time_div[0] + ":0" + mn;
        else time_div[0] = time_div[0] + ":" + mn;

        return time_div;
    }
}
