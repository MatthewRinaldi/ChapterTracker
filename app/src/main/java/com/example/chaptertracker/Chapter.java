package com.example.chaptertracker;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Book.class,
                parentColumns = "bookId",
                childColumns = "bookId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("bookId")}
)
public class Chapter {

    @PrimaryKey(autoGenerate = true)
    private int chapterId;

    private int bookId;
    private String chapterName = "";
    private String chapterNotes;
    private Boolean chapterRead = false;
    private int difficultyTag;
    private long timestamp;
    private int chapterIndex;

    public Chapter(int bookId, String chapterName, String chapterNotes, Boolean chapterRead, int difficultyTag, long timestamp) {
        this.bookId = bookId;
        this.chapterName = chapterName;
        this.chapterNotes = chapterNotes;
        this.chapterRead = chapterRead;
        this.difficultyTag = difficultyTag;
        this.timestamp = timestamp;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterNotes() {
        return chapterNotes;
    }

    public void setChapterNotes(String chapterNotes) {
        this.chapterNotes = chapterNotes;
    }

    public Boolean getChapterRead() {
        return chapterRead;
    }

    public void setChapterRead(Boolean chapterRead) {
        this.chapterRead = chapterRead;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getDifficultyTag() {
        return difficultyTag;
    }

    public void setDifficultyTag(int difficultyTag) {
        this.difficultyTag = difficultyTag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
}
