package com.example.filmapptest;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String director;

    private String genre;
    private float rating;

    public Movie(String title, String director, String genre, float rating) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = rating;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}

    public String toString() {
        return "Title: " + title + "\nDirector: " + director + "\nGenre: " + genre + "\nRating: " +
                rating;
    }
}
