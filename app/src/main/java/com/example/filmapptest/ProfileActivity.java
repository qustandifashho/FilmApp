package com.example.filmapptest;

import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
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
        setupProfile();
    }

    public void setupProfile(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);

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
    }
}


