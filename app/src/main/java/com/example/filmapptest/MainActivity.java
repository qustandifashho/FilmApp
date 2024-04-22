package com.example.filmapptest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends ComponentActivity {
    private Button button;
    private AssetManager assets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // we called it login. R links to res folder
        assets = getAssets();
        Toast.makeText(this, "App Opened", Toast.LENGTH_SHORT).show(); //LAB 5 for debugging
        //setupButtons();
    }

    /*private void setupButtons() {
        button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText uText = findViewById(R.id.inputName);
                EditText pText = findViewById(R.id.passwordInput);
                int id = authenticate(uText.getText().toString(), pText.getText().toString()); // Lab 5
                if (id > 0) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class); // moves from mainActivity to Profile activity using intent
                    intent.putExtra("id", id); // lab 5, something about a bundle
                    startActivity(intent);
                } else {
                    uText.setText("");
                    pText.setText("");
                    uText.setError("Incorrect Username and Password combination");
                    pText.setError("Incorrect Username and Password combination");

                }
            }
        });
    }

     */

    // second android studio lab
    private int authenticate(String username, String password) {
        Scanner scan;
        String str = "";
        String[] arr = null;
        boolean authenticated = false;
        int id = -1; // lab 5

        try {
            scan = new Scanner(assets.open("login.txt"));
            while(scan.hasNext()) {  // while there is another line in our .txt file...
                str = scan.nextLine();
                arr = str.split(",");
                if(username.equalsIgnoreCase(arr[1]) && password.equals(arr[2])){
                    authenticated = true;
                    id = Integer.parseInt(arr[0]); // lab 5
                    break;
                }
            }
            scan.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return id;

        // make sure when writing to file you do .append not .write because .write gets rid of what is already in there

    }
}




