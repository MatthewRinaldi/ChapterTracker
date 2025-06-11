package com.example.chaptertracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class ChapterViewModel extends AndroidViewModel {

    private final ChapterDao chapterDao;
    public ChapterViewModel(@NonNull Application application) {
        super(application);
        ChapterTrackerDatabase db = ChapterTrackerDatabase.getInstance(application);
        chapterDao = db.chapterDao();
    }

    public LiveData<List<Chapter>> getAllChaptersForBook(int bookId) {return chapterDao.getChaptersForBook(bookId);}
    public LiveData<Chapter> getChapterById(int chapterId) {return chapterDao.getChapterById(chapterId);}
    public void updateChapterName(Chapter chapter) {
        Executors.newSingleThreadExecutor().execute(() -> {
            chapterDao.updateChapter(chapter);
        });
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
