package com.example.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager instance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotesDB";
    private static final String TABLE_NAME = "Note";
    private static final String ID_FIELD = "id";
    private static final String DESC_FIELD = "desc";
    private static final String TITLE_FIELD = "title";

    private SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager getInstance(Context context){
        if(instance == null ){
            instance = new SQLiteManager(context);
        }
         return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String initDb = new StringBuilder().
                append("CREATE TABLE ").
                append(TABLE_NAME).append("(").
                append(ID_FIELD).append(" INT,").
                append(TITLE_FIELD).append(" TEXT,").
                append(DESC_FIELD).append(" TEXT)").toString();

        sqLiteDatabase.execSQL(initDb);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveNoteToDb(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD , note.getId());
        contentValues.put(TITLE_FIELD , note.getTitle());
        contentValues.put(DESC_FIELD , note.getDescription());

        db.insert(TABLE_NAME,null,contentValues);

    }

    public void loadNotesFromDb(){

        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    int id = result.getInt(0);
                    String title = result.getString(1);
                    String desc = result.getString(2);
                    Note note = new Note(id,title,desc);
                    NoteList.getInstance().addToList(note);
                }
            }
        }

    }
    public void updateNoteInDb(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD , note.getId());
        contentValues.put(TITLE_FIELD , note.getTitle());
        contentValues.put(DESC_FIELD , note.getDescription());

        db.update(TABLE_NAME , contentValues , ID_FIELD + "=?" , new String[]{String.valueOf(note.getId())});
    }

    public void deleteNoteFromDb(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME , ID_FIELD + "=?" , new String[]{String.valueOf(note.getId())});

    }
}
