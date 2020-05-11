package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Aktivität, welche die Baukosten anzeigt.
 */
public class ShowCostsActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(ShowCostsActivity.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_costs);

    }

    /**
     * @param view View um auf Buttonklick zu reagieren
     *             Lädt wieder die Activity zur Auswahl der Aktionen.
     */
    public void ahead(View view) {
        Intent intent = new Intent(this, SelectActionActivity.class);
        startActivity(intent);
    }
}
