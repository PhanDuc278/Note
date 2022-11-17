package com.example.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.noteapp.Adapter.NoteAdapter;
import com.example.noteapp.Database.NoteDatabase;
import com.example.noteapp.Entities.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView img_AddNewNote;
    private RecyclerView rec_ItemNote ;
    private SearchView Search ;

    private static final int REQUEST_ADD_NOTE = 1;
    private static final int REQUEST_SHOW_NOTE = 2;

    NoteAdapter adapter ;
    List<Note> noteList ;
    NoteDatabase database ;

    Note selectNote ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search = findViewById(R.id.search);
        img_AddNewNote = findViewById(R.id.img_AddNewNote);
        rec_ItemNote = findViewById(R.id.rec_ItemNote);

        database = NoteDatabase.getInstance(getApplicationContext());
        noteList = database.dao().getNote();

        updateNote(noteList);


        img_AddNewNote.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext() , CreateNoteActivity.class);
            startActivityForResult(intent , REQUEST_ADD_NOTE);
        });

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                search(newText);
                return true;
            }
        });
    }

    private void search(String s) {
        List<Note> list = new ArrayList<>();
        for (Note note : noteList){
            if (note.getTitle().toLowerCase().contains(s.toLowerCase(Locale.ROOT)) ||
            note.getContent().toLowerCase().contains(s.toLowerCase(Locale.ROOT))
                    || note.getDate().toLowerCase().contains(s.toLowerCase(Locale.ROOT))){
                list.add(note);
            }
            adapter.filterList(list);
        }
    }

    private void updateNote(List<Note> noteList) {
        rec_ItemNote.setHasFixedSize(true);
        rec_ItemNote.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteAdapter(noteList , getApplicationContext() , listener);
        rec_ItemNote.setAdapter(adapter);
    }

    private final ClickListener listener = new ClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this , CreateNoteActivity.class);
            intent.putExtra("show_note" , note);
            startActivityForResult(intent , REQUEST_SHOW_NOTE);
        }

        @Override
        public void onLongClick(Note note, CardView cardView) {
            selectNote = note ;
            showDialog(selectNote);
        }
    };

    private void showDialog(Note selectNote) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure want delete note ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        database.dao().deleteNote(selectNote);
                        noteList.remove(selectNote);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_NOTE){
            if (resultCode == RESULT_OK){
                Note note = (Note) data.getSerializableExtra("note");
                database.dao().insertNote(note);
                noteList.clear();
                noteList.addAll(database.dao().getNote());
                adapter.notifyDataSetChanged();
            }
        }else if (requestCode == REQUEST_SHOW_NOTE){
            if (resultCode == RESULT_OK){
                Note note = (Note) data.getSerializableExtra("note");
                database.dao().updateNote(note.getId() , note.getTitle() , note.getContent());
                noteList.clear();
                noteList.addAll(database.dao().getNote());
                adapter.notifyDataSetChanged();
            }
        }
    }
}