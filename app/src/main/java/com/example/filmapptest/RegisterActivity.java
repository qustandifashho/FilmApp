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


    // is this for the internal storage of the account
    // int is the account number
    private int createLogin() { // make the file
        // check that the necessary info for creating the account
        EditText usernameInput = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordInput = (EditText) findViewById(R.id.passwordEditText);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt"); // not the one in the assets forlder
        OutputStreamWriter w = null;
        Scanner scan;
        int id = -1;
        String str = null;
        String[] arr;
        // login.txt: id,username,password
        if (!f.exists()) {
            id = 1;
            try {
                w = new OutputStreamWriter(openFileOutput("login.txt", MODE_PRIVATE));
                w.write(id + "," + username + "," + password);
                w.close();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        } else {
            try {
                scan = new Scanner(openFileInput("login.txt"));
                while (scan.hasNextLine()) {  // while there is another line in our .txt file...
                    str = scan.nextLine();
                }
                if (str != null) {
                    arr = str.split(",");
                    if (arr.length == 3) {
                        id = Integer.parseInt(arr[0]) + 1;
                    }
                }
                scan.close();

                w = new OutputStreamWriter(openFileOutput("login.txt", MODE_APPEND));
                w.append("\n" + id + "," + username + "," + password);
                w.close();

            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "IOException " + e.getMessage(), Toast.LENGTH_SHORT).show();


            }

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
    }
}
