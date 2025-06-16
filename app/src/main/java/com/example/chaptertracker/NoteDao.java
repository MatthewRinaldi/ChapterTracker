package com.example.chaptertracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM Note WHERE noteId = :noteId")
    LiveData<Note> getNoteById(int noteId);

    @Query("SELECT * FROM Note WHERE chapterId = :chapterId")
    LiveData<List<Note>> getNotesFromChapter(int chapterId);

    @Query("SELECT * FROM Note WHERE noteId = :noteId")
    Note getSyncNoteById(int noteId);
}
