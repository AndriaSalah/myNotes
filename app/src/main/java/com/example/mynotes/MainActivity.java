package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NoteActions {
    private ListView noteListView;
    private Button addNoteButton;

    private boolean firstStart = true ;
    NoteAdapter noteAdapter;

    private SQLiteManager SQL_MANAGER = SQLiteManager.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        setNoteAdapter();
        loadNotesFromDb();
        addNoteButton.setOnClickListener(v -> createNewNote());

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

    public void loadNotesFromDb(){
        SQL_MANAGER.loadNotesFromDb();
        System.out.println("---------------------------------------------------------------------------------- load");
    }
    private void initElements(){
        noteListView = findViewById(R.id.noteListView);
        addNoteButton = findViewById(R.id.addNote);

    }

    private void setNoteAdapter() {
        noteAdapter = new NoteAdapter(getApplicationContext() , NoteList.getInstance().getList(),this);
        noteListView.setAdapter(noteAdapter);
    }

    public void createNewNote(){
        Intent newNoteIntent = new Intent(this,noteActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    public void deleteNote(Note note) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add New Item");

        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog, null, false);
        builder.setView(v);
        builder.setTitle("Confrimation");
        builder.setPositiveButton("Yes",(dialogInterface, i) -> {
            NoteList.getInstance().removeFromList(note);
            SQL_MANAGER.deleteNoteFromDb(note);
            noteAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No",(dialogInterface, i) -> {

            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        });
    builder.show();
    }

    @Override
    public void editNote(Note note ) {
        Intent intent = new Intent(this,noteActivity.class);
        intent.putExtra("note",note);
        startActivity(intent);
    }

}