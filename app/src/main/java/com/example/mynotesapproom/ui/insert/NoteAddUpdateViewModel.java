package com.example.mynotesapproom.ui.insert;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

import com.example.mynotesapproom.database.Note;
import com.example.mynotesapproom.repository.NoteRepository;

public class NoteAddUpdateViewModel extends AndroidViewModel {

    private final NoteRepository mNoteRepository;

    // Constructor
    public NoteAddUpdateViewModel(Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
    }

    // Method to insert a note
    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    // Method to update a note
    public void update(Note note) {
        mNoteRepository.update(note);
    }

    // Method to delete a note
    public void delete(Note note) {
        mNoteRepository.delete(note);
    }
}

