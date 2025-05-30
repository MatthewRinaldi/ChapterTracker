package com.example.chaptertracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Chapter.class}, version = 2)
public abstract class ChapterTrackerDatabase extends RoomDatabase {

    private static ChapterTrackerDatabase INSTANCE;

    public abstract BookDao bookDao();
    public abstract ChapterDao chapterDao();

    // Custom DB initialization setup to prevent potential duplicate DBs
    public static synchronized  ChapterTrackerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ChapterTrackerDatabase.class,
                    "chapter_tracker_db"
            ).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
