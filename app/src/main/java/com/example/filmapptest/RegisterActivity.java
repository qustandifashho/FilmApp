package com.example.filmapptest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

// all done in lab 6
public class RegisterActivity extends ComponentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register); // we called it login. R links to res folder XML file. Different from the R.id
        //Toast.makeText(this, "App Opened", Toast.LENGTH_SHORT).show();
        setupButtons();
    }

    private void setupButtons() {
        // button id is CreateAccount
        Button button1 = (Button) findViewById(R.id.CreateAccountButton);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = -1;
                EditText nameInput = (EditText) findViewById(R.id.nameEditText);
                EditText usernameInput = (EditText) findViewById(R.id.usernameEditText);
                EditText emailInput = (EditText) findViewById(R.id.emailEditText);
                EditText passwordInput = (EditText) findViewById(R.id.passwordEditText);

                if (validateAccountInfo()) {
                    id = createLogin();
                    Toast.makeText(getBaseContext(), "All EditText has Values", Toast.LENGTH_SHORT).show();
                    if (id > 0) {
                        createAccount(id);
                    }
                    finish();
                } else {
                    nameInput.setText("");
                    usernameInput.setText("");
                    emailInput.setText("");
                    passwordInput.setText("");

                    nameInput.setError("All fields must be filled out");
                    usernameInput.setError("All fields must be filled out");
                    emailInput.setError("All fields must be filled out");
                    passwordInput.setError("All fields must be filled out");

                }

            }
        });
    }

    private boolean validateAccountInfo() {
        EditText nameInput = (EditText) findViewById(R.id.nameEditText);
        EditText usernameInput = (EditText) findViewById(R.id.usernameEditText);
        EditText emailInput = (EditText) findViewById(R.id.emailEditText);
        EditText passwordInput = (EditText) findViewById(R.id.passwordEditText);

        if(!usernameInput.getText().toString().equals("") && !nameInput.getText().toString().equals("") && !emailInput.getText().toString().equals("") && !passwordInput.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }

    }





    private int createLogin() {
        // Get the entered username and password
        EditText usernameInput = findViewById(R.id.usernameEditText);
        EditText passwordInput = findViewById(R.id.passwordEditText);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Initialize variables
        File loginFile = new File(getFilesDir(), "login.txt");
        Scanner scanner;
        int id = -1;
        boolean usernameExists = false;

        try {
            if (!loginFile.exists()) {
                // If the login file doesn't exist, create it and write the first entry
                id = 1;
                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("login.txt", MODE_PRIVATE));
                writer.write(id + "," + username + "," + password);
                writer.close();
            } else {
                // If the login file exists, check for existing usernames
                scanner = new Scanner(loginFile);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[1].equals(username)) {
                        // If the username already exists, set flag to true
                        usernameExists = true;
                        break;
                    }
                }
                scanner.close();

                if (!usernameExists) {
                    // If the username is unique, append it to the login file
                    scanner = new Scanner(loginFile);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        if (parts.length >= 1) {
                            id = Integer.parseInt(parts[0]);
                        }
                    }
                    scanner.close();

                    // Increment id for the new account
                    id++;

                    OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("login.txt", MODE_APPEND));
                    writer.append("\n" + id + "," + username + "," + password);
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // If the username already exists, show an error message
        if (usernameExists) {
            Toast.makeText(getBaseContext(), "Please select a different username. This username is taken.", Toast.LENGTH_SHORT).show();
        }

        return id;
    }












    private void createAccount(int id) { // makign accounts.txt
        EditText nameInput = (EditText) findViewById(R.id.nameEditText);
        EditText emailInput = (EditText) findViewById(R.id.emailEditText);
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt"); // not the one in the assets forlder
        OutputStreamWriter w = null;

        // accounts.txt
        if (!f.exists()) {
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt", MODE_PRIVATE)); ////////////////////////
                w.write(id + "," + name + "," + email);
                w.close();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        } else {
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt", MODE_APPEND));
                w.append("\n" + id + "," + name + "," + email);
                w.close();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }





        // Create empty watchlist file
        File watchlistFile = new File(getFilesDir(), "watchlist_" + id + ".txt");
        try {
            if (!watchlistFile.exists()) {
                watchlistFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Create empty friends list file
        File friendsFile = new File(getFilesDir(), "friends_" + id + ".txt");
        try {
            if (!friendsFile.exists()) {
                friendsFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //  Create empty ranked list file
        File rankedList = new File(getFilesDir(), "rankedList_" + id + ".txt");
        try {
            if (!rankedList.exists()) {
                rankedList.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    }

