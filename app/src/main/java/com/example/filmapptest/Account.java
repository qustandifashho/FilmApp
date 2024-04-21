package com.example.filmapptest;

import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Scanner;

// Lab 5 ACCOUNT PAGE
public class Account {
    private int id; // basically the ley that links accounts.txt and login.txt
    private String name;
    private String email;



    public Account(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Account(int id, AssetManager assets){
        this.id = id;
        setupFromFile(id, assets);
    }
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void setupFromFile(int id, AssetManager assets){ // lab 5
        Scanner scan;
        String str = "";
        String[] arr = null;

        try {
            scan = new Scanner(assets.open("accounts.txt"));
            while(scan.hasNext()) {  // while there is another line in our .txt file...
                str = scan.nextLine();
                arr = str.split(",");
                if(Integer.parseInt(arr[0]) == id){
                    id = Integer.parseInt(arr[0]); // lab 5
                    name = arr[1];
                    email = arr[2];
                    break;
                }
            }
            scan.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}



