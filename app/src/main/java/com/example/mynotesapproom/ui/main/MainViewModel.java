package com.example.mynotesapproom.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mynotesapproom.database.Note;
import com.example.mynotesapproom.repository.NoteRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final NoteRepository mNoteRepository;

    // Constructor
    public MainViewModel(Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);  // Initialize the NoteRepository
    }

    // Method to get all notes as LiveData
    public LiveData<List<Note>> getAllNotes() {
        return mNoteRepository.getAllNotes();  // Return LiveData from the repository
    }
}
