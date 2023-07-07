package com.example.wikimedia.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wikimedia.data.models.Articles.Pages;
import com.example.wikimedia.data.models.Articles.Thumbnail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pages.class, Thumbnail.class}, version = 1)
public abstract  class ImagesDatabase extends RoomDatabase {

        public abstract PagesDao pagesDao();
        public abstract ThumbnailDao thumbnailDao();

        // Create a singleton instance of the database
        private static ImagesDatabase instance;

        public static synchronized ImagesDatabase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                                ImagesDatabase.class, "pages_db")
                        .build();
            }
            return instance;
        }

}
