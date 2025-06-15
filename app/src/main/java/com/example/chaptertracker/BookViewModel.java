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

    public LiveData<Book> getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    // Executing new single thread processing for DB transactions
    public void insertBook(Book book, Consumer<Integer> onInsertion) {
        Executors.newSingleThreadExecutor().execute(() -> {
            int bookId = (int) bookDao.insertBook(book);
            // Callback function that returns bookId back to main thread for chapter creation
            new Handler(Looper.getMainLooper()).post(() -> {
                onInsertion.accept(bookId);
            });
        });
    }

    public void deleteBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.deleteBook(book);
        });
    }

    public void updateBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.updateBook(book);
        });
    }

    public void getSyncBookById(int bookId, Consumer<Book> onCall) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Book book = bookDao.getSyncBookById(bookId);
            new Handler(Looper.getMainLooper()).post(() -> {
                onCall.accept(book);
            });
        });
    }
}
