package com.example.diesiedler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.Presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Aktivität in welcher der User seinen Username eingeben kann
 */
public class LoginActivity extends AppCompatActivity {

    private EditText displayName; // Eingabetextfeld für den Username
    private static final Logger log = Logger.getLogger(LoginActivity.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayName = findViewById(R.id.displayName);
    }

    /**
     * Klickt der User den Button, wird der eingegebene Text
     * als username gespeichert und mit der aktuellen Aktivität an
     * den Presenter übergeben, der die Daten an den Server weitergibt.
     *
     * @param view View, um aus Textfeld zu lesen
     */
    public void setName(View view) {

        String username = displayName.getText().toString();
        log.log(Level.INFO, username);
        Presenter.addUserAndGetUserList(this, username);
    }
}
