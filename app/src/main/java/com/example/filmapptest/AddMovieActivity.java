package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class AddMovieActivity extends ComponentActivity {
    private EditText movieTitle, movieDirector, movieGenre;
    private RatingBar movieRating;
    ArrayList<Movie> movies;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmovies);

        movieTitle = findViewById(R.id.movieTitle);
        movieDirector = findViewById(R.id.movieDirector);
        movieGenre = findViewById(R.id.movieGenre);
        movieRating = findViewById(R.id.movieRating);
        Button saveMovieButton = (Button) findViewById(R.id.saveMovieButton);
        ImageButton backButton = (ImageButton) findViewById(R.id.back2);
        movies = new ArrayList<>();

        saveMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new Movie(
                        movieTitle.getText().toString(),
                        movieDirector.getText().toString(),
                        movieGenre.getText().toString(),
                        movieRating.getRating());
                movieTitle.setText("");
                movieDirector.setText("");
                movieGenre.setText("");
                saveMovie(movie);
                finish(); // Return to the previous activity
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
    private void saveMovie(Movie movie) {
        ArrayList<Movie> movies = new ArrayList<>();
        File file = new File(getFilesDir(), "watchlist_" + getId() + ".txt");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                movies = (ArrayList<Movie>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        movies.add(movie);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
