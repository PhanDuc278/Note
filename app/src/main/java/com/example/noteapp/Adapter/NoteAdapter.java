package com.example.noteapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.ClickListener;
import com.example.noteapp.Entities.Note;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> noteList ;
    private Context context ;
    ClickListener listener ;

    public NoteAdapter(List<Note> noteList, Context context, ClickListener listener) {
        this.noteList = noteList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note , parent , false);

        return new ViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.txt_Title.setText(note.getTitle());
        holder.txt_Title.setSelected(true);

        holder.txt_DateTime.setText(note.getDate());
        holder.txt_DateTime.setSelected(true);

        holder.txt_Content.setText(note.getContent());
        holder.txt_Content.setSelected(true);

        if (note.isPin()){
            holder.img_Pin.setVisibility(View.VISIBLE);
        }else {
            holder.img_Pin.setVisibility(View.GONE);
        }

        int color_Card = colorRandom();
        holder.card_ItemNote.setCardBackgroundColor(holder.itemView.getResources().getColor(color_Card , null));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(noteList.get(holder.getAdapterPosition()), holder.card_ItemNote);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(noteList.get(holder.getAdapterPosition()));
            }
        });
    }

    private int colorRandom(){
        List<Integer> list = new ArrayList<>();
        list.add(R.color.orange);
        list.add(R.color.green);
        list.add(R.color.pink);
        list.add(R.color.red);
        list.add(R.color.blue);
        list.add(R.color.orange);
        list.add(R.color.brown);

        Random random = new Random();
        int ran_Color = random.nextInt(list.size());
        return list.get(ran_Color);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void filterList(List<Note> filterList){
        noteList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card_ItemNote ;
        private ImageView img_Pin ;
        private TextView txt_Title , txt_Content , txt_DateTime ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_DateTime = itemView.findViewById(R.id.txt_DateTime);
            txt_Content = itemView.findViewById(R.id.txt_Content);
            txt_Title = itemView.findViewById(R.id.txt_Title);
            img_Pin = itemView.findViewById(R.id.img_Pin);
            card_ItemNote = itemView.findViewById(R.id.card_ItemNote);
        }
    }
}
