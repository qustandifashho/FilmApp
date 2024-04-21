package com.example.filmapptest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import java.io.IOException;
import java.util.Scanner;


public class ProfileActivity extends ComponentActivity {

    private com.example.filmapptest.Account profileInfo; // Lab 5
    private AssetManager assets; // lab 5


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // we called it login. R links to res folder
        assets = getAssets(); // lab 5
        setupProfile(); // lab 5
    }

    public void setupProfile() { // lab 5
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1); // holding id from mainActivity

        // profileInfo = new Account(id, assets);

        Scanner scan;
        String str = "";
        String[] arr = null;

        try {
            scan = new Scanner(assets.open("accounts.txt"));
            while (scan.hasNext()) {  // while there is another line in our .txt file...
                str = scan.nextLine();
                arr = str.split(",");
                if (Integer.parseInt(arr[0]) == id) {
                    id = Integer.parseInt(arr[0]); // lab 5
                    profileInfo = new com.example.filmapptest.Account(id, arr[1], arr[2]);
                    break;
                }
            }
            scan.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


        TextView name = (TextView) findViewById((R.id.nameask));
        TextView email = (TextView) findViewById((R.id.profileInfo1));
        name.setText(profileInfo.getName());
        email.setText(profileInfo.getEmail());

    }
}
