package com.example.chaptertracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class ChapterViewModel extends AndroidViewModel {

    private final ChapterDao chapterDao;
    public ChapterViewModel(@NonNull Application application) {
        super(application);
        ChapterTrackerDatabase db = ChapterTrackerDatabase.getInstance(application);
        chapterDao = db.chapterDao();
    }

    public void insertChapter(Chapter chapter) {
        Executors.newSingleThreadExecutor().execute(() -> {
            chapterDao.insertChapter(chapter);
        });
    }

    public void deleteChapter(Chapter chapter) {
        Executors.newSingleThreadExecutor().execute(() -> {
            chapterDao.deleteChapter(chapter);
        });
    }
}
