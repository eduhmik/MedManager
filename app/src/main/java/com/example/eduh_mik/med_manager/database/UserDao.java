package com.example.eduh_mik.med_manager.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.eduh_mik.med_manager.models.User;

import java.util.List;

/**
 * Created by Mayore on 12/04/2018.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT * FROM user where id LIKE  :id")
    User findById(int id);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Update
    void upDate(User... users);

    @Delete
    void delete(User user);
}
