package com.example.chaptertracker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int bookId;

    @NonNull
    private String bookTitle;
    private String bookDescription;
    private int chapterCount;
    private int progress;

    public Book(String bookTitle, String bookDescription, int chapterCount) {
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.chapterCount = chapterCount;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }
}
