package com.example.mynotesapproom.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotesapproom.database.Note;
import com.example.mynotesapproom.databinding.ItemNoteBinding;
import com.example.mynotesapproom.helper.NoteDiffCallback;
import com.example.mynotesapproom.ui.insert.NoteAddUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final List<Note> listNotes = new ArrayList<>();

    // Method to set a new list of notes and apply DiffUtil
    public void setListNotes(List<Note> listNotes) {
        NoteDiffCallback diffCallback = new NoteDiffCallback(this.listNotes, listNotes);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout using the binding
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        // Bind the data to the ViewHolder
        holder.bind(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    // ViewHolder class to bind note data to the UI
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding binding;

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Note note) {
            // Bind data to views in the layout
            binding.tvItemTitle.setText(note.getTitle());
            binding.tvItemDate.setText(note.getDate());
            binding.tvItemDescription.setText(note.getDescription());

            // Set up click listener to start the NoteAddUpdateActivity with the selected note
            binding.cvItemNote.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note);
                v.getContext().startActivity(intent);
            });
        }
    }
}