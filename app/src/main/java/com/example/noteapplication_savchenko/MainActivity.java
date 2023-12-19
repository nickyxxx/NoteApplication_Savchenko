// MainActivity.java
package com.example.noteapplication_savchenko;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static List<Note> notes = new ArrayList<>();
    static ArrayAdapter<Note> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        loadNotesFromSharedPreferences();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Launch EditNoteActivity
                // for editing when a note is tapped
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });
    }


    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteNoteAndRefresh(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    private void deleteNoteAndRefresh(int position) {
        notes.remove(position);
        arrayAdapter.notifyDataSetChanged();
        saveNotesToSharedPreferences();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_note) {
            Intent intent = new Intent(this, EditNoteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadNotesFromSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapplication_savchenko", Context.MODE_PRIVATE);
        Set<String> noteSet = sharedPreferences.getStringSet("notes", new HashSet<>());

        // If noteSet is empty, do not clear existing notes
        if (!noteSet.isEmpty()) {
            // Clear existing notes and add new ones
            notes.clear();
            for (String noteStr : noteSet) {
                String[] parts = noteStr.split("\\|");
                if (parts.length == 2) {
                    notes.add(new Note(parts[0], parts[1]));
                }
            }

            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void saveNotesToSharedPreferences() {
        Log.d("NoteApp", "Saving notes to SharedPreferences");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapplication_savchenko", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear existing data
        editor.clear();

        // Add new notes
        Set<String> noteSet = new HashSet<>();
        for (Note note : notes) {
            noteSet.add(note.getName() + "|" + note.getDescription());
        }
        editor.putStringSet("notes", noteSet);

        // Apply changes
        editor.apply();
    }
}
