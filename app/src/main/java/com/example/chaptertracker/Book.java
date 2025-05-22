package com.example.chaptertracker;

import java.util.List;

public class Book {
    String bookTitle;
    String bookDescription;
    List<Chapter> chapters;

    public Book(String bookTitle, String bookDescription, List<Chapter> chapters) {
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.chapters = chapters;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
