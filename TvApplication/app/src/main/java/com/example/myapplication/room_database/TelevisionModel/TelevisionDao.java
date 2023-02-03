package com.example.myapplication.room_database.TelevisionModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TelevisionDao {

    @Insert
    void addNewTelevision(Television model);

    @Update
    void update(Television model);

    @Delete
    void delete(Television model);

    @Query("DELETE FROM tv_database")
    void deleteAllCourses();

    @Query("SELECT * FROM tv_database ORDER BY tv_name ASC")
    List<Television> getAllTelevisions();

    @Query("SELECT * FROM tv_database WHERE is_favourite_tv = 1 AND bgGledai = 1 ORDER BY tv_name ASC")
    List<Television> getAllFavouriteTelevisionsBgGledai();


    @Query("SELECT * FROM tv_database WHERE is_favourite_tv = 1 AND bgGledai = 0 ORDER BY tv_name ASC")
    List<Television> getAllFavouriteTelevisionsSeirSanduk();

    @Query("SELECT * FROM tv_database WHERE bgGledai = 1 ORDER BY tv_name ASC")
    List<Television> getAllBgGledaiTV();

    @Query("SELECT * FROM tv_database WHERE bgGledai = 0 ORDER BY tv_name ASC")
    List<Television> getAllSeirSanduk();

    @Query("SELECT COUNT(*) FROM tv_database ")
    int getAllTVCount();

    @Query("SELECT * FROM tv_database WHERE tv_name = :tv_name")
    Television getTelevisionByName(String tv_name);
}
