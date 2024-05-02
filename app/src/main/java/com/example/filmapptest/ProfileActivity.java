package com.example.filmapptest;

import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ProfileActivity extends ComponentActivity {
    private Account profileinfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setupProfile(); //hi ;p
        setupButtons();
    }

    public void setupProfile(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);

        Scanner scnr;
        String str ="";
        String[] arr = null;
        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");

        try{
            if(f.exists()) {
                scnr = new Scanner(openFileInput("accounts.txt"));
                while (scnr.hasNext()) {
                    str = scnr.nextLine();
                    arr = str.split(",");
                    if (Integer.parseInt(arr[0]) == id) {
                        profileinfo = new Account(id, arr[1], arr[2]);
                        break;
                    }
                }
                scnr.close();
            }
        }
        catch(IOException e){
            System.out.println("Error: "+ e.getMessage());
        }
        if (profileinfo != null) {
            TextView name = (TextView) findViewById(R.id.name);
            TextView email = (TextView) findViewById(R.id.email);
            name.setText(profileinfo.getName());
            email.setText(profileinfo.getEmail());
        }
    }
    private void setupButtons(){
        Button button1 = (Button) findViewById(R.id.friends);
        Button button2 = (Button) findViewById(R.id.rate);
        Button button3 = (Button) findViewById(R.id.watch);
        Button button4 = (Button) findViewById(R.id.logout);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WatchlistActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

    }
}


