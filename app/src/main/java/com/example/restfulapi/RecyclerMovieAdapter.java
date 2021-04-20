package com.example.restfulapi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.ViewHolder> {

    private final ArrayList<DataMovies> list;
    private final Activity activity;

    public RecyclerMovieAdapter(ArrayList<DataMovies> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMovieAdapter.ViewHolder holder, int position) {
        holder.itemBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        TextView tv_title;
        TextView tv_year;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item = itemView.findViewById(R.id.img_item);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_year  = itemView.findViewById(R.id.tv_year);
        }

        public void itemBind(DataMovies dataMovies) {
            Picasso.get().load(dataMovies.getPoster()).into(img_item);
            tv_title.setText("Title : " + dataMovies.getTitle());
            tv_year.setText("Year : " + dataMovies.getYear());

            itemView.setOnClickListener(v -> {
                FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                DialogMovies movies = new DialogMovies(dataMovies.getTitle());
                movies.show(fragmentManager, "fm");

            });

        }
    }
}
