package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankedListActivity extends ComponentActivity {
    ArrayList<Movie> movies = new ArrayList<>();
    RecyclerView movieList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranklist);

        //Refresh button
        Button refresh = findViewById(R.id.refreshRankingsButton);
        ImageButton backOut = findViewById(R.id.backButton);

        //Movies -> Updates the watchlist everytime you go into the ranklist
        updateMovies();
        Collections.sort(movies, new MovieComparator());
        Collections.reverse(movies);

        //Setup the view
        movieList = findViewById(R.id.rankList);
        movieList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(movies, this);
        movieList.setAdapter(adapter);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update the movies arraylist
                updateMovies();

                //Sort the movies using the compare function below
                Collections.sort(movies, new MovieComparator());

                //notify the adapter of a change of the data set
                adapter.notifyDataSetChanged();
            }
        });

        backOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    public void updateMovies()
    {
        File watchlist = new File(getFilesDir(), "watchlist_"+getID()+".txt");
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(watchlist)))
        {
            movies = (ArrayList<Movie>) ois.readObject();
        }
        catch(Exception e)
        {
            Log.e("Ranked List", String.format("Cannot open file %s, please make sure it exists", "watchlist_"+getID()+".txt"));
            e.printStackTrace();
        }
    }

    public int getID() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        return id;
    }

    private class MovieComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie m1, Movie m2) {
            float ratingTieBreaker = Float.compare(m1.getRating(), m2.getRating());
            int nameTieBreaker = m1.getTitle().compareTo(m2.getTitle());
            int genreTieBreaker = m1.getGenre().compareTo(m2.getGenre());

            if (ratingTieBreaker != 0f) return (int) (ratingTieBreaker * 100f);
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

    private class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView information;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.movie_item, parent, false));
            information = itemView.findViewById(R.id.movie_info);
        }

        public void bind(Movie movie)
        {
            information.setText(movie.toString() + "\n");
        }
    }
}