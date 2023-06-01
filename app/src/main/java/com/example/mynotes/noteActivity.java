package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class noteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteDesc;
    private TextView toolbarTitle;
    private Button saveButton;
    private ImageButton cancelButton;
    private NoteList noteList = NoteList.getInstance();
    private SQLiteManager SQL_MANAGER = SQLiteManager.getInstance(this);
    Note alreadyNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initElements();
        System.out.println("ksandkasndlkasndljkansldkasdkakdskdsnksdnksdksdksdksksdkskkssdnkksdksdkdsksd");
        saveButton.setOnClickListener(v -> {saveNote();});
        cancelButton.setOnClickListener(v -> {finish();});
    }
    private void initElements(){
        toolbarTitle = findViewById(R.id.toolBarTitle);
        noteTitle = findViewById(R.id.titleData);
        noteDesc = findViewById(R.id.descData);
        saveButton = findViewById(R.id.saveNote);
        cancelButton = findViewById(R.id.cancelButton);

        if (getIntent().getSerializableExtra("note" )!= null){
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            loadPrevNote();
        }

    }

    public void saveNote() {
        String title = noteTitle.getText().toString();
        String desc = noteDesc.getText().toString();
        if (alreadyNote != null) {
            noteList.getList().get(alreadyNote.getId()).setTitle(title);
            noteList.getList().get(alreadyNote.getId()).setDescription(desc);
            System.out.println(noteList.getList().get(alreadyNote.getId()).getTitle());
            SQL_MANAGER.updateNoteInDb(noteList.getList().get(alreadyNote.getId()));
            finish();
        } else {
            int id = noteList.getList().size();
            if (!title.isEmpty()) {
                Note newNote = new Note(id, title, desc);
                noteList.addToList(newNote);
                SQL_MANAGER.saveNoteToDb(newNote);
                finish();
            } else {
                Toast.makeText(this, "A valid title is required ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadPrevNote() {
        toolbarTitle.setText(alreadyNote.getTitle());
        noteTitle.setText(alreadyNote.getTitle());
        noteDesc.setText(alreadyNote.getDescription());
    }
}