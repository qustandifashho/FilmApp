package com.example.filmapptest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import androidx.activity.ComponentActivity;


public class WatchlistActivity extends ComponentActivity {
    private ListView moviesList;
    private Button addMovieButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchlist);
        setUpWatchlist();
    }
    public void setUpWatchlist() {
        moviesList = findViewById(R.id.moviesList);
        addMovieButton = findViewById(R.id.addMovieButton);


        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchlistActivity.this, AddMovieActivity.class));
            }
        });
    }

}
