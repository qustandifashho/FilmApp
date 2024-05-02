package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.ComponentActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FriendsListActivity extends ComponentActivity {

    ListView friendsList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);
        friendsList = findViewById(R.id.friendsList);
        setupButtons();
    }

    public void setupButtons() {
        Button button1 = (Button) findViewById(R.id.addFriend);
        Button buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        ImageButton buttonGoBack = (ImageButton) findViewById(R.id.buttonGoBack);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = (new Intent(FriendsListActivity.this, AddFriendActivity.class));
                intent.putExtra("id", getId());
                startActivity(intent);
            }
        });


        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFriendsListView();
            }
        });

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
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

    private void updateFriendsListView() {
        ArrayList<String> usernames = loadNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        friendsList.setAdapter(adapter);
    }

    private ArrayList<String> loadNames() {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            File file = new File(getFilesDir(), "friends_" + getId() + ".txt");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    usernames.add(line);
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }


}
