package com.example.mynotes;

import java.util.ArrayList;
import java.util.List;

public class NoteList {
    private static NoteList instance;
    private List<Note> list ;
    private NoteList(){
        list = new ArrayList<>();
    }
    public static NoteList getInstance(){

        if(instance == null){
            instance = new NoteList();
        }
         return instance;
    }

    public void addToList (Note note){
        list.add(note);
    }
    public void removeFromList(Note note){
        list.remove(note);
    }
    public List<Note> getList(){
        return list;
    }
}
