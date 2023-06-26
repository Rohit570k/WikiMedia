package com.example.wikimedia.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.data.models.Articles.Thumbnail;

import java.util.List;

@Dao
public interface PagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pages pages);

    @Delete
    void delete(Pages pages);

    @Query("DELETE FROM pages")
    void deleteAllPages();

    @Query("SELECT * FROM pages")
    List<Pages> getPages();
}
