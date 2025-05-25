package com.example.chaptertracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class BookViewModel extends AndroidViewModel {

    private final BookDao bookDao;
    private final LiveData<List<Book>> allBooks;
    public BookViewModel(@NonNull Application application) {
        super(application);
        ChapterTrackerDatabase db = ChapterTrackerDatabase.getInstance(application);
        bookDao = db.bookDao();
        allBooks = bookDao.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public void insertBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.insertBook(book);
        });
    }

    public void deleteBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.deleteBook(book);
        });
    }
}
