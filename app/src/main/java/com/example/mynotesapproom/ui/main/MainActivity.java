package com.example.mynotesapproom.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mynotesapproom.databinding.ActivityMainBinding;
import com.example.mynotesapproom.helper.ViewModelFactory;
import com.example.mynotesapproom.ui.insert.NoteAddUpdateActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ViewModel
        MainViewModel mainViewModel = obtainViewModel();

        // Set up the observer to listen to LiveData updates
        mainViewModel.getAllNotes().observe(this, noteList -> {
            if (noteList != null) {
                adapter.setListNotes(noteList);
            }
        });

        // Initialize the adapter
        adapter = new NoteAdapter();

        // Set up the RecyclerView
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);

        // Set up FAB click listener to navigate to NoteAddUpdateActivity
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    // Method to obtain the ViewModel instance
    private MainViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(MainViewModel.class);
    }
}