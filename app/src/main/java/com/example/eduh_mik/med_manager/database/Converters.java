package com.example.eduh_mik.med_manager.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Mayore on 13/04/2018.
 */

public class Converters {
    @TypeConverter
    public static Long fromDate(Date date){
        if (date == null) {
            return(null);
        }
        return date.getTime();
    }
    @TypeConverter
    public static Date toDate(Long millisSinceEpoch){
        if (millisSinceEpoch == null) {
            return(null);
        }
        return (new Date(millisSinceEpoch));
    }
}
