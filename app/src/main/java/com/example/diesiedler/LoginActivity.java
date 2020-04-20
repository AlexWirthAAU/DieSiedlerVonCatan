package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenters.Presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private EditText displayName;
    private static final Logger log = Logger.getLogger(LoginActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayName = findViewById(R.id.displayName);
    }

    /**
     * @param view - current View to access TextView
     */
    public void setName(View view) {

        String username = displayName.getText().toString();
        log.log(Level.INFO, "username", username);
        new Presenter().addUserAndGetUserList(this, username);
    }
}
