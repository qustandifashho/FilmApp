package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.activity.ComponentActivity;

import java.io.File;
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

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
                if (validateAddFriendInfo()) {

                    finish();
                }
                else {
                    userUsername.setText("");
                    userUsername.setError("The field must be filled out.");
                }
            }
        });
    }

    private boolean validateAddFriendInfo() {//is input empty
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);

        return !userUsername.getText().toString().equals("");
    }

    private int validateAccountInfo() {//does the account username exist in the accounts internal storage
        EditText userUsername = (EditText) findViewById(R.id.usernameSearch);
        String friendName = userUsername.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/account.txt"); //is it account or accounts?
        Scanner scan;
        String str = null;
        String arr[];

        try {
            scan = new Scanner(openFileInput("account.txt")); //is it account or accounts?
            while (scan.hasNextLine()) {
                str = scan.nextLine();
            }
            if(str != null) {
                arr = str.split(",");
                if(arr.length == ) {

                }
        }
    }

}
