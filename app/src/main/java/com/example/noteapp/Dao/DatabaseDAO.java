package com.example.noteapp.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.noteapp.Entities.Note;

import java.util.List;

@Dao
public interface DatabaseDAO {

    //Get all note from database
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getNote();

    //Search note by title and content
    @Query("SELECT * FROM notes WHERE title LIKE :t AND " +
            "content LIKE :c LIMIT 1")
    Note findByName(String t , String c);

    //Insert note to database
    @Insert(onConflict = REPLACE)
    void insertNote(Note note);

    //Update note
    @Query("UPDATE notes SET title =:title , content =:content WHERE id =:id")
    void updateNote(int id , String title , String content);

    //Delete note
    @Delete
    void deleteNote(Note note);
}
