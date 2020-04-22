package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Christina Senger
 * <p>
 * Startaktivität, die zum Spielen einlädt
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /**
     * Wird auf den Button mit dem Text "Entdecke die Welt von Catan geklickt,
     * wird die LoginActivity gestartet, in der der User seinen Namen auswählt.
     *
     *  @param view View, um den Button anzusprechen
     */
    public void discover(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}