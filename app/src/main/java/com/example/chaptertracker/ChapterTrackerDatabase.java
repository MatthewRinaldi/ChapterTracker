package com.example.chaptertracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Book.class, Chapter.class, Note.class}, version = 10)
public abstract class ChapterTrackerDatabase extends RoomDatabase {

    private static ChapterTrackerDatabase INSTANCE;

    public abstract BookDao bookDao();
    public abstract ChapterDao chapterDao();
    public abstract NoteDao noteDao();

    // Custom DB initialization setup to prevent potential duplicate DBs
    public static synchronized  ChapterTrackerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ChapterTrackerDatabase.class,
                    "chapter_tracker_db"
            ).fallbackToDestructiveMigration(true)
                    .addCallback(new Callback() {
                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            db.setForeignKeyConstraintsEnabled(true);
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

}
