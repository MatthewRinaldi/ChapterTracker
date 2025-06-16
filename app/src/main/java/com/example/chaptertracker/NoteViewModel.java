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

public class NoteViewModel extends AndroidViewModel {

    private final NoteDao noteDao;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        ChapterTrackerDatabase db = ChapterTrackerDatabase.getInstance(application);
        noteDao = db.noteDao();
    }

    public LiveData<List<Note>> getNotesFromChapter(int chapterId) {return noteDao.getNotesFromChapter(chapterId);}

    public void insertNote(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            noteDao.insertNote(note);
        });
    }

    public void deleteNote(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            noteDao.deleteNote(note);
        });
    }

    public void updateNote(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            noteDao.updateNote(note);
        });
    }

    public void getSyncNoteById(int noteId, Consumer<Note> onCall) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Note note = noteDao.getSyncNoteById(noteId);
            new Handler(Looper.getMainLooper()).post(() -> {
                onCall.accept(note);
            });
        });
    }

}
