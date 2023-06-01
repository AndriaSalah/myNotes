package com.example.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private NoteActions noteActions;
    public NoteAdapter(Context context, List<Note> notes ,NoteActions actions ){
        super(context ,0,notes );
        noteActions = actions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        if(view == null) view = LayoutInflater.from(getContext()).inflate(R.layout.note_item,parent,false);

            TextView title = view.findViewById(R.id.noteCellTitle);
            TextView description = view.findViewById(R.id.noteCellDescription);
            ImageButton deleteNoteButton = view.findViewById(R.id.deleteNote);;

            title.setText(note.getTitle());
            description.setText(note.getDescription());
            deleteNoteButton.setOnClickListener(v -> noteActions.deleteNote(note) );
            view.setOnClickListener(v -> noteActions.editNote(note));
        return view;
    }

}
