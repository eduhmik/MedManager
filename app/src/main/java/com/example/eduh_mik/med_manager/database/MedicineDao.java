package com.example.eduh_mik.med_manager.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.eduh_mik.med_manager.models.Medicine;
import com.example.eduh_mik.med_manager.models.User;

import java.util.List;

/**
 * Created by Mayore on 12/04/2018.
 */

@Dao
public interface MedicineDao {

    @Query("SELECT * FROM medicine")
    List<Medicine> getAll();

    @Query("SELECT * FROM medicine where id LIKE  :id")
    Medicine findById(int id);

    @Query("SELECT COUNT(*) from medicine")
    int countUsers();

    @Insert
    void insertAll(Medicine... medicines);

    @Delete
    void delete(Medicine medicine);
}
