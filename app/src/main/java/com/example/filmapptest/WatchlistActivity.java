package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class WatchlistActivity extends ComponentActivity {
    private ListView moviesList;
    ListView listViewMovies;
    ArrayList<Movie> movies;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchlist);
        setUpWatchlist();

    }
    public void setUpWatchlist() {
        moviesList = findViewById(R.id.moviesList);
        Button addMovieButton = (Button) findViewById(R.id.addMovieButton);
        Button refreshButton = (Button) findViewById(R.id.refresh);
        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        listViewMovies = findViewById(R.id.moviesList);
        movies = new ArrayList<>();
        loadMovies();
        updateListView();

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchlistActivity.this, AddMovieActivity.class);
                intent.putExtra("id", getId());
                startActivity(intent);
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovies();
                updateListView();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private int getId(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        return id;
    }
    private void loadMovies() {
        File file = new File(getFilesDir(), "watchlist_" + getId() + ".txt");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                movies = (ArrayList<Movie>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateListView() {
        ArrayList<String> movieDetails = new ArrayList<>();
        for (Movie movie : movies) {
            movieDetails.add(movie.toString());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieDetails);
        listViewMovies.setAdapter(adapter);
    }

}
