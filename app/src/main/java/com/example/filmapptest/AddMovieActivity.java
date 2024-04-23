package com.example.filmapptest;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


import androidx.activity.ComponentActivity;


public class AddMovieActivity extends ComponentActivity {
    private EditText movieTitle, movieDirector, movieGenre;
    private RatingBar movieRating;
    private Button saveMovieButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmovies);

        movieTitle = findViewById(R.id.movieTitle);
        movieDirector = findViewById(R.id.movieDirector);
        movieGenre = findViewById(R.id.movieGenre);
        movieRating = findViewById(R.id.movieRating);
        saveMovieButton = findViewById(R.id.saveMovieButton);


        saveMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new Movie(
                        movieTitle.getText().toString(),
                        movieDirector.getText().toString(),
                        movieGenre.getText().toString(),
                        movieRating.getRating()
                );

                finish(); // Return to the previous activity
            }
        });
    }
}
