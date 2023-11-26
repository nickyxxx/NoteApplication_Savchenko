// EditNoteActivity.java
package com.example.noteapplication_savchenko;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class EditNoteActivity extends AppCompatActivity {

    EditText editName;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editName = findViewById(R.id.editName);
        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        int noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            // Editing an existing note
            Note note = MainActivity.notes.get(noteId);
            editName.setText(note.getName());
            editText.setText(note.getDescription());
        }

        // Add click listener to Save Note button
        findViewById(R.id.saveNoteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteClicked();
            }
        });
    }

    private void saveNoteClicked() {
        String name = editName.getText().toString().trim();
        String description = editText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            // Show a toast if any field is empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save the note
            if (getIntent().getIntExtra("noteId", -1) != -1) {
                // Editing an existing note
                int noteId = getIntent().getIntExtra("noteId", -1);
                MainActivity.notes.get(noteId).setName(name);
                MainActivity.notes.get(noteId).setDescription(description);
            } else {
                // Adding a new note
                MainActivity.notes.add(new Note(name, description));
            }

            // Save notes to SharedPreferences
            saveNotesToSharedPreferences();

            // Notify the adapter that the dataset has changed
            MainActivity.arrayAdapter.notifyDataSetChanged();

            // Finish the activity
            finish();
        }
    }

    private void saveNotesToSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapplication_savchenko", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> noteSet = new HashSet<>();
        for (Note note : MainActivity.notes) {
            noteSet.add(note.getName() + "|" + note.getDescription());
        }

        editor.putStringSet("notes", noteSet);
        editor.apply();
    }
}
