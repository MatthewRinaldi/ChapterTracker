package com.example.chaptertracker;

public class Chapter {
    String chapterName = "";
    String chapterNotes;
    Boolean chapterRead = false;

    public Chapter(String chapterName, String chapterNotes, Boolean chapterRead) {
        this.chapterName = chapterName;
        this.chapterNotes = chapterNotes;
        this.chapterRead = chapterRead;
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
}
