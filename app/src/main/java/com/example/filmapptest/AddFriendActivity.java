package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class AddFriendActivity extends ComponentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);
        setupButtons();

    }

    private void setupButtons() {
        Button button1 = (Button) findViewById(R.id.saveFriend);
        ImageButton button2 = (ImageButton) findViewById(R.id.buttonGoBack2);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
                if (validateAddFriendInfo()) {
                    if(validateAccountInfo()) {
                        if(validateNotSameUser(getId())) {
                            if(validateNotAlreadyInFriendsList(getId())) {
                                addFriend(getId());
                                finish();
                            }
                            else {
                                userUsername.setText("");
                                userUsername.setError("You have already added this person as a friend.");
                            }
                        }
                        else {
                            userUsername.setText("");
                            userUsername.setError("Cannot add yourself as a friend.");
                        }
                    }
                    else {
                        userUsername.setText("");
                        userUsername.setError("This username does not exist.");
                    }

                } else {
                    userUsername.setText("");
                    userUsername.setError("The field must be filled out.");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
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

    private boolean validateAddFriendInfo() {//is input empty
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);

        return !userUsername.getText().toString().equals("");
    }

    private boolean validateAccountInfo() {//does the account username exist in the login internal storage
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
        String friendName = userUsername.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt");
        Scanner scan;
        String str = null;
        String arr[];


        try {
            scan = new Scanner(openFileInput("login.txt"));
            while (scan.hasNextLine()) {
                str = scan.nextLine();

                if (str != null) {
                    arr = str.split(",");
                    if (arr.length == 3) {
                        if (arr[1].equals(friendName)) {
                            scan.close();
                            return true;
                        }
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean validateNotSameUser(int id) {//checks to see if the name found is not the user who is searching
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
        String friendName = userUsername.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt");
        Scanner scan;
        String str = null;
        String arr[];


        try {
            scan = new Scanner(openFileInput("login.txt"));
            while (scan.hasNextLine()) {
                str = scan.nextLine();

                if (str != null) {
                    arr = str.split(",");
                    if (arr.length == 3) {
                        if(arr[1].equals(friendName) && Integer.parseInt(arr[0]) != id) {
                            scan.close();
                            return true;
                        }
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean validateNotAlreadyInFriendsList(int id) {//has the friend already been added to the users friends list?
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
        String friendName = userUsername.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "friends_" + id + ".txt");
        Scanner scan;
        String str = null;
        String arr[];


        try {
            scan = new Scanner(openFileInput("friends_" + id + ".txt"));
            while (scan.hasNextLine()) {
                str = scan.nextLine();

                if (str != null) {
                    arr = str.split(",");
                    if (arr.length == 1) {
                        if(arr[0].equals(friendName)) {
                            scan.close();
                            return false;
                        }
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;

    }

    private void addFriend(int id) {
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
        String friendName = userUsername.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "friends_" + id + ".txt");
        OutputStreamWriter w = null;
        Scanner scan;
        String str = null;
        String arr[];

        try {
            scan = new Scanner(openFileInput("friends_" + id + ".txt"));

            if(!scan.hasNextLine()) {
                w = new OutputStreamWriter(openFileOutput("friends_" + id + ".txt", MODE_PRIVATE));
                w.write(friendName);
                w.close();

            }
            else {
                w = new OutputStreamWriter(openFileOutput("friends_" + id + ".txt", MODE_APPEND));
                w.append("\n" + friendName);
                w.close();
            }
            scan.close();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}