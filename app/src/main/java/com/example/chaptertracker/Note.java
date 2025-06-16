package com.example.chaptertracker;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Chapter.class,
                parentColumns = "chapterId",
                childColumns = "chapterId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("chapterId")}
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private int chapterId;
    private String note;

    public Note(int chapterId, String note) {
        this.chapterId = chapterId;
        this.note = note;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
