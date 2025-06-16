package com.example.chaptertracker;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ChapterViewModel extends AndroidViewModel {

    private final ChapterDao chapterDao;
    public ChapterViewModel(@NonNull Application application) {
        super(application);
        ChapterTrackerDatabase db = ChapterTrackerDatabase.getInstance(application);
        chapterDao = db.chapterDao();
    }

    public LiveData<List<Chapter>> getAllChaptersForBook(int bookId) {return chapterDao.getChaptersForBook(bookId);}
    public LiveData<Chapter> getChapterById(int chapterId) {return chapterDao.getChapterById(chapterId);}
    public void updateChapter(Chapter chapter) {
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

    public void getSyncChapterForBook(int bookId, Consumer<List<Chapter>> onCall) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Chapter> chapters = chapterDao.getSyncChapterForBook(bookId);
            new Handler(Looper.getMainLooper()).post(() -> {
                onCall.accept(chapters);
            });
        });
    }
}
