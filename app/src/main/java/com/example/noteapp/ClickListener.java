package com.example.noteapp;

import androidx.cardview.widget.CardView;

import com.example.noteapp.Entities.Note;

public interface ClickListener {
    void onClick(Note note);
    void onLongClick(Note note , CardView cardView);
}
