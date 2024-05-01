package com.example.filmapptest;

import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.util.Scanner;

public class ProfileActivity extends ComponentActivity {
    private Account profileinfo;
    private AssetManager assets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        assets = getAssets();
        setupProfile(); //hi
    }
    private int getId(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        return id;
    }

    public void setupProfile(){
        int id = getId();

        Scanner scnr;
        String str ="";
        String[] arr = null;

        try{
            scnr = new Scanner(assets.open("accounts.txt"));
            while(scnr.hasNext()) {
                str = scnr.nextLine();
                arr = str.split(",");
                if(Integer.parseInt(arr[0])==id){
                    profileinfo = new Account(id,arr[1],arr[2]);
                    break;
                }
            }
            scnr.close();
        }
        catch(IOException e){
            System.out.println("Error: "+ e.getMessage());
        }
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        name.setText(profileinfo.getName());
        email.setText(profileinfo.getEmail());
        setupButtons();
    }
    private void setupButtons(){
        Button watchlistButton = (Button) findViewById(R.id.watch);
        watchlistButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WatchlistActivity.class);
                intent.putExtra("id", getId());
                startActivity(intent);
            }


        });
    }
}


