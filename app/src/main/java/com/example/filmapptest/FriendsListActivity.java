package com.example.filmapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.ComponentActivity;

public class FriendsListActivity extends ComponentActivity {
    private ListView friendsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);
        setupButtons();
    }

    private void setupButtons() {
        Button button1 = (Button) findViewById(R.id.addFriend);
        friendsList = findViewById(R.id.friendsList);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(FriendsListActivity.this, AddFriendActivity.class));
            }
        });
    }

}
