package com.example.mynotesapproom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mynotesapproom.database.Note;
import com.example.mynotesapproom.database.NoteDao;
import com.example.mynotesapproom.database.NoteRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private final NoteDao mNotesDao;
    private final ExecutorService executorService;

    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNotesDao = db.noteDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mNotesDao.getAllNotes();
    }

    // Method to insert a note in the background
    public void insert(Note note) {
        executorService.execute(() -> mNotesDao.insert(note));
    }

    // Method to delete a note in the background
    public void delete(Note note) {
        executorService.execute(() -> mNotesDao.delete(note));
    }

    // Method to update a note in the background
    public void update(Note note) {
        executorService.execute(() -> mNotesDao.update(note));
    }
}
