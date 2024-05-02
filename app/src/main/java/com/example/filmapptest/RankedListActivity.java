package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmapptest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankedListActivity extends ComponentActivity {
    ArrayList<Movie> movies;
    RecyclerView movieList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranklist);

        //Refresh button
        Button refresh = findViewById(R.id.refreshRankingsButton);

        //Get the movies
        //FAKE MOVIES DELETE AND REPLACE WITH THE ACTUAL ONES
        Movie one = new Movie("Best movie of all time", "Todd Howard", "Action", 1.0f);
        Movie two = new Movie("Best movie of all time", "Todd Howard", "Action", 1.0f);
        Movie three = new Movie("Best movie of all time", "Todd Howard", "Action", 1.0f);


        //Setup the view
        movieList = findViewById(R.id.rankList);
        movieList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(movies, this);
        movieList.setAdapter(adapter);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view0) {
                //Update the movies arraylist

                //Sort the movies using the compare function below
                Collections.sort(movies, new MovieComparator());

                //notify the adapter of a change of the data set
                adapter.notifyDataSetChanged();
            }
        });
    }

    public int getID() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        return id;
    }

    private class MovieComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie m1, Movie m2) {
            Integer m1Rating = (int) (m1.getRating() * 100);
            Integer m2Rating = (int) (m2.getRating() * 100);

            int ratingTieBreaker = m1Rating.compareTo(m2Rating);
            int nameTieBreaker = m1.getTitle().compareTo(m2.getTitle());
            int genreTieBreaker = m1.getGenre().compareTo(m2.getGenre());

            if (ratingTieBreaker != 0) return ratingTieBreaker;
            else if (nameTieBreaker != 0) return nameTieBreaker;
            else return genreTieBreaker;
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<Movie> movies;
        RankedListActivity rlActivity;

        public Adapter(ArrayList<Movie> movies, RankedListActivity rlActivity) {
            this.movies = movies;
            this.rlActivity = rlActivity;
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(rlActivity);
            return new ViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.bind(movie);
        }

        public ArrayList<Movie> getMovies() {
            return movies;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView information;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.addmovies, parent, false));
            information = itemView.findViewById(R.id.movieGenre);
        }

        public void bind(Movie movie)
        {
            information.setText(movie.toString());
        }
    }
}