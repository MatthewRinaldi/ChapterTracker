package com.example.chaptertracker;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// POJO relationship to represent Chapters contained in a Book
public class BookWithChapters {

    @Embedded
    public Book book;

    @Relation(
            parentColumn = "bookId",
            entityColumn = "bookId"
    )
    public List<Chapter> chapters;
}
