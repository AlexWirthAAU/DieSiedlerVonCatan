package com.example.diesiedler;

import android.os.Bundle;

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
}
