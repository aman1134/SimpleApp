package com.example.assignment;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
interface Task {

    @Query("Select * from user")
    List<User> getPersonList();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}
