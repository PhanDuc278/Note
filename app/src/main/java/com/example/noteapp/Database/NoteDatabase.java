package com.example.noteapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.Dao.DatabaseDAO;
import com.example.noteapp.Entities.Note;

@Database(entities = Note.class , version = 1 , exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase database ;
    private final static String DATABASE_NAME = "Notes.db";

    public synchronized static NoteDatabase getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext() ,
                            NoteDatabase.class ,
                            DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database ;
    }

    public abstract DatabaseDAO dao() ;
}
