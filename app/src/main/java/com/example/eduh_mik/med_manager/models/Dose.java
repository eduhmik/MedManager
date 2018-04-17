package com.example.eduh_mik.med_manager.models;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mayore on 16/04/2018.
 */

public class Dose implements Comparable<Dose>{
    private Medicine medicine;
    public Date doseTime;
    public Dose(Medicine medicine,Date doseTime) {
        this.medicine = medicine;
        this.doseTime = doseTime;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public String getDoseTime(){
        DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
        return outputFormat.format(doseTime);
    }
    public String getDoseDate(){
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        return outputFormat.format(doseTime);
    }
    public String getDoseWaitTime(){
        return new PrettyTime().format(doseTime);
    }
    public boolean getDoseIsTaked(){
        return !Calendar.getInstance().getTime().after(doseTime);
    }
    @Override
    public int compareTo(Dose o) {
        return doseTime.compareTo(o.doseTime);
    }
}
