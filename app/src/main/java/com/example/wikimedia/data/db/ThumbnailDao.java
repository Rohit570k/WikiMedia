package com.example.wikimedia.data.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wikimedia.data.models.Articles.Thumbnail;

import java.util.List;

@Dao
public interface ThumbnailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Thumbnail thumbnail);

    @Delete
    void delete(Thumbnail thumbnail);

    @Query("DELETE FROM thumbnail")
    void deleteAllThumbnails();
    @Query("SELECT * FROM thumbnail")
    List<Thumbnail> getThumbnail();

}
