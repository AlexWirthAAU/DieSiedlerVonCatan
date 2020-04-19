package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenters.Presenter;

public class LoginActivity extends AppCompatActivity {

    private EditText displayName;
    private Presenter presenter = new Presenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayName = findViewById(R.id.displayName);
    }

    public void setName(View view) {

        String username = displayName.getText().toString();
        System.out.println(username + " name");
        presenter.addUserAndGetUserList(this, username);
    }
}
