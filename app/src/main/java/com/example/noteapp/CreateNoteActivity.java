package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.noteapp.Entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {
    private ImageView img_back , img_Done ;
    private EditText edt_Title , edt_Content ;
    Note note ;
    boolean isNote = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        img_back = findViewById(R.id.img_back);
        img_Done = findViewById(R.id.img_Done);
        edt_Title = findViewById(R.id.edt_Title);
        edt_Content = findViewById(R.id.edt_Content);


        try {
            note = (Note) getIntent().getSerializableExtra("show_note");
            edt_Content.setText(note.getContent());
            edt_Title.setText(note.getTitle());
            isNote = true ;
        }catch (Exception e){
            e.printStackTrace();
        }


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_Title.getText().toString().trim();
                String content = edt_Content.getText().toString().toString();

                if (title.isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "Title is not null ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (content.isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "Please type something ", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d-MM-yyyy HH:mm a ");
                Date date = new Date();

                if (!isNote){
                    note = new Note();
                }
                note.setContent(content);
                note.setTitle(title);
                note.setDate(dateFormat.format(date));

                Intent intent = new Intent();
                intent.putExtra("note" , note);
                setResult(Activity.RESULT_OK , intent);
                finish();
            }
        });


    }

}