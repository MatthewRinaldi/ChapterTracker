package com.example.chaptertracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Transaction
    @Query("SELECT * FROM Book")
    LiveData<List<BookWithChapters>> getBooksWithChapters();

    @Query("SELECT * FROM Book")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM Book WHERE bookId = :bookId")
    LiveData<Book> getBookById(int bookId);

    @Query("SELECT * FROM Book WHERE bookId = :bookId")
    Book getSyncBookById(int bookId);

    @Insert
    long insertBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
