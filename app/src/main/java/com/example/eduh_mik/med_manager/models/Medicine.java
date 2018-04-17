package com.example.eduh_mik.med_manager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Mayore on 12/04/2018.
 */
@Entity(tableName = "medicine")
public class Medicine {
    public String getAilment() {
        return ailment;
    }

    public Medicine(String name, String description, String ailment, int frequency, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.ailment = ailment;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "ailment")
    private String ailment;
    @ColumnInfo(name = "frequency")
    private int frequency;
    @ColumnInfo(name = "startDate")
    private Date startDate;
    @ColumnInfo(name = "endDate")
    private Date endDate;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getFrequency() {
        return frequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


}
