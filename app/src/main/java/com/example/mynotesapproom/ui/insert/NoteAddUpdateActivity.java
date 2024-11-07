package com.example.mynotesapproom.ui.insert;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotesapproom.R;
import com.example.mynotesapproom.database.Note;
import com.example.mynotesapproom.databinding.ActivityNoteAddUpdateBinding;
import com.example.mynotesapproom.helper.DateHelper;
import com.example.mynotesapproom.helper.ViewModelFactory;

public class NoteAddUpdateActivity extends AppCompatActivity {
    // Static constants, equivalent to companion object in Kotlin
    public static final String EXTRA_NOTE = "extra_note";
    public static final int ALERT_DIALOG_CLOSE = 10;
    public static final int ALERT_DIALOG_DELETE = 20;

    // Member variables
    private boolean isEdit = false;
    private Note note;  // 'Note' object can be null, so we don't need to use 'Optional' or any other wrapper

    // ViewModel and binding
    private NoteAddUpdateViewModel noteAddUpdateViewModel;
    private ActivityNoteAddUpdateBinding binding;  // Replacing nullable binding with direct reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityNoteAddUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        noteAddUpdateViewModel = obtainViewModel();

        // Get the note from the intent
        note = getIntent().getParcelableExtra(EXTRA_NOTE);

        // If note is null, create a new one
        if (note != null) {
            isEdit = true;
        } else {
            note = new Note(); // Create a new Note object if none was passed
        }

        // ActionBar title and button text
        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            // Set ActionBar and button text for edit mode
            actionBarTitle = getString(R.string.change);  // Change title
            btnTitle = getString(R.string.update);  // Update button text

            // Set note details to EditTexts
            if (note != null) {
                binding.edtTitle.setText(note.getTitle()); // Set title
                binding.edtDescription.setText(note.getDescription()); // Set description
            }
        } else {
            // Set ActionBar and button text for add mode
            actionBarTitle = getString(R.string.add);  // Add title
            btnTitle = getString(R.string.save);  // Save button text
        }

        // Update the ActionBar title and show the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set the submit button text
        binding.btnSubmit.setText(btnTitle);

        // Handle the submit button click
        binding.btnSubmit.setOnClickListener(v -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                binding.edtTitle.setError(getString(R.string.empty));
            } else if (TextUtils.isEmpty(description)) {
                binding.edtDescription.setError(getString(R.string.empty));
            } else {
                // Update the note's data
                note.setTitle(title);
                note.setDescription(description);

                if (isEdit) {
                    // If editing, update the note
                    noteAddUpdateViewModel.update(note);
                    showToast(getString(R.string.changed));
                } else {
                    // If adding, set the current date and insert the note
                    note.setDate(DateHelper.getCurrentDate());
                    noteAddUpdateViewModel.insert(note);
                    showToast(getString(R.string.added));
                }

                // Finish the activity
                finish();
            }
        });

        // Handle back press with custom callback
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Custom back press behavior, for example, ask the user to confirm before exiting
                showAlertDialog(ALERT_DIALOG_CLOSE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
            return true;
        } else if (id == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    // Obtain the ViewModel instance
    private NoteAddUpdateViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(NoteAddUpdateViewModel.class);
    }

    // Show Toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Show AlertDialog for Close or Delete actions
    private void showAlertDialog(int type) {
        boolean isDialogClose = (type == ALERT_DIALOG_CLOSE);
        String dialogTitle;
        String dialogMessage;

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogMessage = getString(R.string.message_delete);
            dialogTitle = getString(R.string.delete);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    if (!isDialogClose) {
                        noteAddUpdateViewModel.delete(note);
                        showToast(getString(R.string.deleted));
                    }
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}