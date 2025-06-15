package com.example.chaptertracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChapterDao {

    @Insert
    void insertChapter(Chapter chapter);

    @Update
    void updateChapter(Chapter chapter);

    @Delete
    void deleteChapter(Chapter chapter);

    @Query("SELECT * FROM Chapter WHERE bookId = :bookId ORDER BY chapterId ASC")
    LiveData<List<Chapter>> getChaptersForBook(int bookId);

    @Query("SELECT * FROM Chapter WHERE chapterId = :chapterId")
    LiveData<Chapter> getChapterById(int chapterId);

    @Query("SELECT * FROM Chapter WHERE bookId = :bookId")
    List<Chapter> getSyncChapterForBook(int bookId);
}
